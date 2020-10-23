package com.atm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.atm.models.CardResponse;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@Service
public class CardService {
  @Autowired
  private EurekaClient eurekaClient;
  
  @Value("${spring.application.name}")
  private String service;
 
  @Autowired
  private RestTemplate clienteServicio;

  public CardResponse getCards(String servicioPost) {
    //puertos dinamicos
   
    Application app = eurekaClient.getApplication("CARD");
    List<InstanceInfo> info = app.getInstances();  
    String ip = null;
    int port=0;
    for(InstanceInfo elemento: info) {
      ip = elemento.getHostName();
      port =elemento.getPort();
    }
    
    String url = "http://"+ip+":"+ port +"/core/cards?documentNumber="+servicioPost;    
   
    return  clienteServicio.getForObject(url,CardResponse.class);
       
  }
}
