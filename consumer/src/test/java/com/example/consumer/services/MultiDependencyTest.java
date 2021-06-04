package com.example.consumer.services;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.consumer.config.BaseIntegrationTest;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;

import io.restassured.RestAssured;

public class MultiDependencyTest extends BaseIntegrationTest {

  @Rule
  public PactProviderRule mockGreetingProvider = new PactProviderRule("pactGreetingWorks", "127.0.0.1", 1234, this);

  @Rule
  public PactProviderRule mockFullGreetingProvider =
      new PactProviderRule("pactFullGreetingWorks", "127.0.0.1", 1235, this);

  @Pact(provider = "pactGreetingWorks", consumer = "consumer")
  public RequestResponsePact pactGreetingWorks(PactDslWithProvider builder) {
    return builder.given("Greeting Consumer exists")
        .uponReceiving("A request to greeting/Consumer")
        .path("/greeting/Full Consumer")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body("Hello, Full Consumer!")
        .toPact();
  }

  @Pact(provider = "pactFullGreetingWorks", consumer = "consumer")
  public RequestResponsePact pactFullGreetingWorks(PactDslWithProvider builder) {
    return builder.given("Greeting Full Consumer exists")
        .uponReceiving("A request to greeting/fullname/Consumer")
        .path("/fullname/Consumer")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body("Full Consumer")
        .toPact();
  }

  @Test
  @PactVerification({"pactGreetingWorks", "pactFullGreetingWorks"})
  public void greetingsTest() {
    RestAssured.given()
        .when()
        .get("/fullGreeting")
        .then()
        .statusCode(200)
        .body(CoreMatchers.is("Hello, Full Consumer!"));
  }
}
