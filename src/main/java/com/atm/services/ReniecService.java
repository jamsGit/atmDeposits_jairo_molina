package com.atm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.atm.configs.ClienteConfig;
import com.atm.models.Reniec;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@Service
public class ReniecService {

  @Autowired
  private EurekaClient eurekaCliente;
  @Autowired
  private RestTemplate clienteServicio;
  @Value("${spring.application.name}")
  private String service;

  public Reniec getReniec(String servicioPost) {

    // puertos dinamicos
    Application app = eurekaCliente.getApplication("RENIEC");
    List<InstanceInfo> info = app.getInstances();
    ;
    String ip = null;
    int port = 0;
    for (InstanceInfo elemento : info) {
      ip = elemento.getHostName();
      port = elemento.getPort();
    }

    String url = "http://" + ip + ":" + port + "/external/reniec/validate";

    return clienteServicio.postForObject(url, servicioPost, Reniec.class);

  }
}
