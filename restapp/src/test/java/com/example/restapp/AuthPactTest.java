package com.example.restapp;

import au.com.dius.pact.consumer.BaseMockServer;
import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.MockHttpServerKt;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.model.MockProviderConfig;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.MediaType;

import io.pactfoundation.consumer.dsl.LambdaDsl;

@Provider("pactAuthWorks") // name of tested provider
@PactBroker(host = "localhost", port = "9080", scheme = "http")
public class AuthPactTest extends PactTest {

  private BaseMockServer server;

  @Before
  public void setup() {
    MockProviderConfig config = new MockProviderConfig("127.0.0.1", 1234, PactSpecVersion.V2, "http");

    RequestResponsePact pact = ConsumerPactBuilder
        .consumer("consumer")
        .hasPactWith("pactHttpsGreetingWorks")
        .uponReceiving("A request to auth")
        .path("/cardpayments/v1/accounts/1111111111/auths")
        .headers("Content-Type", MediaType.APPLICATION_JSON_VALUE,
            "Accept", MediaType.APPLICATION_JSON_VALUE,
            "Authorization", "Basic c29tZXVzZXI6cGFzc3dvcmQ=")
        .method("POST")
        .body(getRequestBody())
        .willRespondWith()
        .status(200)
        .body(getResponseBody())
        .toPact();

    server = (BaseMockServer) MockHttpServerKt.mockServer(pact, config);
    server.start();
    server.waitForServer();
  }

  @After
  public void teardown() {
    if (server != null) {
      server.stop();
      server = null;
    }
  }

  @State("Auth works")
  public void auth() {
    System.out.println("Calling service Auth works");
  }

  private DslPart getResponseBody() {
    return LambdaDsl.newJsonBody((o) -> o
        .numberValue("availableToSettle", 12345)
        .object("card", card -> {
          card.stringValue("cardNum", "4916556499001713");
          card.stringValue("type", "VI");
          card.stringValue("lastDigits", "1713");
          card.object("cardExpiry", cardExpiry -> {
            cardExpiry.numberValue("month", 12);
            cardExpiry.numberValue("year", 2030);
          });
        })
        .stringType("authCode", "749908 - dynamic value")
        .stringValue("currencyCode", "USD")
        .stringType("avsResponse", "NOT_PROCESSED - dynamic value")
        .stringType("cvvVerification", "NOT_PROCESSED - dynamic value")
        .uuid("id")
        .uuid("merchantRefNum")
        .numberValue("amount", 12345)
        .stringValue("status", "COMPLETED")
        .array("links", links -> {
          links.object(linkItem -> {
            linkItem.stringValue("rel", "self");
            linkItem.stringType("href", "https://api.dev.paysafe.com... - dynamic value");
          });
        })).build();
  }

  private DslPart getRequestBody() {
    return LambdaDsl.newJsonBody((o) -> o
        .object("card", card -> {
          card.stringValue("cardNum", "4916556499001713");
          card.object("cardExpiry", cardExpiry -> {
            cardExpiry.numberValue("month", 12);
            cardExpiry.numberValue("year", 2030);
          });
        })
        .stringType("merchantRefNum", "80ce44a9-c258-4a5c-9e3d-2000197bcf4a - dynamic value")
        .numberValue("amount", 12345)).build();
  }
}
