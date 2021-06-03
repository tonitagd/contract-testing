package com.example.restapp;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFolder;

@Provider("pactGreetingWorks") // name of tested provider
@PactBroker(host = "localhost", port = "9080", scheme = "http")
public class GreetingPactTest extends PactTest {

  @State("Greeting Consumer exists")
  public void greetIvan() {
    System.out.println("Calling service Greeting Consumer exists");
  }
}
