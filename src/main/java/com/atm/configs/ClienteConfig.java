package com.atm.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClienteConfig {
  @Bean("clienteServicio")
  public RestTemplate configTemplate() {
    return new RestTemplate();
  }

}

