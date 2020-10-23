package com.atm.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;




@Configuration
public class UrlServer {
   
  @Bean
  public String getUrl() {
//    
//    Application app = eurekaClient.getApplication(serviceId);
//    List<InstanceInfo> info = app.getInstances();
//    String ip = null;
//    int port = 0;
//    
//    for (InstanceInfo elemento : info) {
//      System.out.println(elemento);
//      ip = elemento.getHostName();
//
//      port = elemento.getPort();
//    }
    
   // System.out.println("http://" + ip + ":" + port + this.getRoute());
    
   //return ("http://" + ip + ":" + port + this.getRoute());
    return null;
  
  }

}
