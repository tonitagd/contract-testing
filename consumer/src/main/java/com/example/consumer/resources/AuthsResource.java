package com.example.consumer.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthsResource {
  public AuthsResource() {
    this.card = new CreditCardResource();
    this.amount = 12345;
    this.merchantRefNum = UUID.randomUUID().toString();
  }

  public Integer availableToSettle;
  public CreditCardResource card;
  public String authCode;
  public String currencyCode;
  public String avsResponse;
  public String cvvVerification;
  public String id;
  public String merchantRefNum;
  public Integer amount;
  public Date txnTime;
  public String status;
  public List<Link> links;
}
