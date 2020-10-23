package com.atm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.atm.configs.ClienteConfig;
import com.atm.models.Account;
import com.atm.models.Card;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@Service
public class AccountService {
  @Autowired
  private EurekaClient eurekaClient;
  @Autowired
  private RestTemplate clienteServicio;
  @Value("${spring.application.name}")
  private String service;
  
  public Account getAccount(String servicioPost) {
    Application app = eurekaClient.getApplication("ACCOUNT");
    List<InstanceInfo> info = app.getInstances();
    String ip = null;
    int port=0;
    for(InstanceInfo elemento: info) {
      ip = elemento.getHostName();
      port =elemento.getPort();
    }
    String url = "http://"+ip+":"+ port +"/core/accounts?cardNumber=";
    System.out.println("url:" +url);
   
    return clienteServicio.getForObject(url+servicioPost,Account.class);
        
  }
}
