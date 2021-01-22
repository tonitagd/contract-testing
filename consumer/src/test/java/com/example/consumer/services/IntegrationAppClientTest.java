package com.example.consumer.services;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.consumer.config.BaseIntegrationTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class IntegrationAppClientTest extends BaseIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Rule
  public PactProviderRule mockGreetingProvider = new PactProviderRule("pactGreetingWorks", "127.0.0.1", 1234, this);

  @Pact(provider = "pactGreetingWorks", consumer = "consumer")
  public RequestResponsePact pactGreetingWorks(PactDslWithProvider builder) {
    return builder.given("Greeting Consumer exists")
        .uponReceiving("A request to greeting/Consumer")
        .path("/greeting/Consumer")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body("Hello, Consumer!")
        .toPact();
  }

  @Test
  @PactVerification("pactGreetingWorks")
  public void greetingsTest() {
    String greeting = restTemplate.getForObject("/greeting", String.class);
    Assert.assertEquals("Hello, Consumer!", greeting);
  }
}


