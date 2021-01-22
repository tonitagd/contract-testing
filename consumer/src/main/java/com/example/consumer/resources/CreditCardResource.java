package com.example.consumer.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditCardResource {
  public CreditCardResource() {
    cardNum = "4916556499001713";
    cardExpiry = new CardExpiry();
  }

  public String cardNum;
  public String type;
  public String lastDigits;
  public CardExpiry cardExpiry;
  public String cvv;

  public List<Link> links;
}
