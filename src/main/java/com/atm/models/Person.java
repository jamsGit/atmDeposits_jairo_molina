package com.atm.models;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Person implements Serializable {

  private static final long serialVersionUID = -161706415689290466L;

  private Long id;
  private String document;
  private Boolean fingerprint;
  private Boolean blacklist;

}
