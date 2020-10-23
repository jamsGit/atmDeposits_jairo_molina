package com.atm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.atm.models.Person;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
  
  @Mock
  private EurekaClient eurekaClient;
  
  @InjectMocks
  private PersonService personService;

  @Mock
  private RestTemplate clienteServicio;
  
  @Mock
  private InstanceInfo elemento;

  @Test
  public void testGetPerson() throws Exception {
    
    when(eurekaClient.getApplication("PEOPLE-SERVICES"))
    .thenReturn(new Application("PEOPLE-SERVICES"));
    
    when(elemento.getHostName()).thenReturn("http://localhost");
    
    when(elemento.getPort()).thenReturn(8081);
    
    when(clienteServicio.getForObject("http://localhost:8081/core/persons?documentNumber=12345", Person.class))
              .thenReturn(new Person(1L, "12345", true, true));
    
    /* Test */
    Person person = clienteServicio.getForObject("http://localhost:8081/core/persons?documentNumber=12345", Person.class);
    
    /* Asserts */
    assertEquals(1L,person.getId());
    assertEquals("12345",person.getDocument());
    assertEquals(true, person.getFingerprint());
    assertEquals(true,person.getBlacklist());
    
    when(personService.getPerson("12345"))
    .thenReturn(new Person(1L, "12345", true, true));
    
  }

}


