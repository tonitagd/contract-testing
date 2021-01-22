package com.example.restapp;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;

@Provider("pactAuthWorks") // name of tested provider
@PactFolder("src/test/resources/pacts") // Point where to find pacts (See also section Pacts source in documentation)
public class AuthPactTest extends PactTest {

  @State("Auth works")
  public void auth() {
    System.out.println("Calling service Auth works");
  }
}
