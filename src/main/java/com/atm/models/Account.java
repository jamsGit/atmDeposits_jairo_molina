package com.atm.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1702238447759804202L;
  private String accountNumber;
  private Double amount;

}
