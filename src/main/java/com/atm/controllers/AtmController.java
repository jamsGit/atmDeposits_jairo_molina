package com.atm.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.atm.models.Account;
import com.atm.models.Card;
import com.atm.models.CardResponse;
import com.atm.models.Deposit;
import com.atm.models.Fingerprint;
import com.atm.models.Person;
import com.atm.models.Reniec;
import com.atm.models.ValidAccount;
import com.atm.services.AccountService;
import com.atm.services.CardService;
import com.atm.services.FingerprintService;
import com.atm.services.PersonService;
import com.atm.services.ReniecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@RestController
@RequestMapping("/atm/")

public class AtmController {
   
  @Autowired
    PersonService personService;
  @Autowired
    FingerprintService fingerprintService;
  @Autowired
  ReniecService reniecService;
  @Autowired
  AccountService accountService;
  @Autowired
  CardService cardService;
  
  double  monto;
  
  @Value("${spring.application.name}")
  private String service;
  @PostMapping("deposits/")  
  
  public ResponseEntity<Single<?>>responseReniec(@RequestBody String numberDocument) throws Exception{
    //final Logger logger = LoggerFactory.getLogger(getClass().getName());
    JSONObject respuesta = new JSONObject(numberDocument);
    monto = 0.00;
   
    Deposit deposito = new Deposit();
    
    Person person = personService.getPerson(respuesta.getString("numberDocument"));
    try {
      if(person.getBlacklist()) {
          throw new Exception("Se encuentra en lista Negra");
      }else {
        if(person.getFingerprint()) {
          deposito.setDataFinger(fingerprintService.getFingerprint(respuesta.getString("numberDocument")).getEntityName());
        }else {
         person.setFingerprint(true);
         personService.updatePerson(person, person.getDocument());
          deposito.setDataFinger(reniecService.getReniec(respuesta.getString("numberDocument")).getEntityName()); 
          
        }
        
        List<ValidAccount> validAccounts = new ArrayList<>();
        CardResponse lista = cardService.getCards(respuesta.getString("numberDocument"));
        
        Observable.fromArray(lista)
                  .map(x->x.getCards())
                  .flatMapIterable(y->y)
                  .filter(z->z.getActive())
                  .subscribe(x->{
                   Account account = accountService.getAccount(x.getCardNumber());
                  validAccounts.add(new ValidAccount(account.getAccountNumber()));
                  monto+=account.getAmount();
                  });
                  
           deposito.setValidAccounts(validAccounts);

          deposito.setMontoFinal(monto); 
        return ResponseEntity.ok(Single.just(deposito));
        
      }
    } catch (Exception e) {
      return ResponseEntity.ok(Single.just(new Deposit(e.getMessage(),null,0.0 )));
    }
   

  }

}
