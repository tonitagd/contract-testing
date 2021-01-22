package com.example.restapp;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;

@PactFolder("src/test/resources/pacts") // Point where to find pacts (See also section Pacts source in documentation)
@Provider("pactGreetingWorks") // name of tested provider
public class GreetingPactTest extends PactTest {

  @State("Greeting Consumer exists")
  public void greetIvan() {
    System.out.println("Calling service Greeting Consumer exists");
  }
}
