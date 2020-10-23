package com.atm.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deposit {
  
  private String dataFinger;
  private List<ValidAccount> ValidAccounts;
  private Double montoFinal;

}
