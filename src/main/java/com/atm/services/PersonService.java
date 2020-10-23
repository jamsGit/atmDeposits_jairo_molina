package com.atm.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.atm.models.Person;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@Service
public class PersonService {
  @Value("${spring.application.name}")
  private String service;
  @Autowired
  private EurekaClient eurekaClient;

  @Autowired
  private RestTemplate clienteServicio;

  public Person getPerson(String servicioPost) {

    // puertos dinamicos
    Application app = eurekaClient.getApplication("PEOPLE-SERVICES");
    List<InstanceInfo> info = app.getInstances();
    String ip = null;
    int port = 0;
    for (InstanceInfo elemento : info) {
      ip = elemento.getHostName();

      port = elemento.getPort();
    }
    String url = "http://" + ip + ":" + port + "/core/persons?documentNumber=" + servicioPost;

    return clienteServicio.getForObject(url, Person.class);

  }

  public void updatePerson(Person person, String document) {
    Application app = eurekaClient.getApplication("PEOPLE-SERVICES");
    List<InstanceInfo> info = app.getInstances();
    String ip = null;
    int port = 0;
    for (InstanceInfo elemento : info) { 
      ip = elemento.getHostName();

      port = elemento.getPort();
    }
    String url = "http://" + ip + ":" + port + "/core/update/" + document;

    Map<String, String> params = new HashMap<String, String>();
    params.put("document", "document");
    clienteServicio.put(url, person, "");

  }

}
