package com.atm.swagger;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfigAppSwagger {
  
  /**
   * 
   * comnetario.
   */
  
  public Docket api() {
    
    return new Docket(DocumentationType.SWAGGER_12).select()
        .apis(RequestHandlerSelectors.basePackage("com.atm.services"))
        .paths(PathSelectors.any()).build();
  }

}
