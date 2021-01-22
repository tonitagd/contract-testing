package com.example.consumer.services;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.consumer.config.BaseIntegrationTest;
import com.example.consumer.resources.AuthsResource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pactfoundation.consumer.dsl.LambdaDsl;

public class RestAppClientTest extends BaseIntegrationTest {

  @Autowired
  private RestAppClient restAppClient;

  @Rule
  public PactProviderRule mockGreetingProvider = new PactProviderRule("pactGreetingWorks", "127.0.0.1", 1234, this);

  @Rule
  public PactProviderRule mockAuthProvider = new PactProviderRule("pactAuthWorks", "127.0.0.1", 1234, this);

  @Pact(provider = "pactGreetingWorks", consumer = "consumer")
  public RequestResponsePact pactGreetingWorks(PactDslWithProvider builder) {
    return builder.given("Greeting Ivan exists")
        .uponReceiving("A request to greeting/Ivan")
        .path("/greeting/Ivan")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body("Hello, Ivan!")
        .toPact();
  }

  /**
   * { "availableToSettle":12345, "card":{ "cardNum":"4916556499001713", "type":"VI", "lastDigits":"1713",
   * "cardExpiry":{ "month":12, "year":2030 } }, "authCode":"749908", "currencyCode":"USD",
   * "avsResponse":"NOT_PROCESSED", "cvvVerification":"NOT_PROCESSED", "id":"44948e6a-6488-470a-a514-fd4f2717e5ee",
   * "merchantRefNum":"80ce44a9-c258-4a5c-9e3d-2000197bcf4a", "amount":12345, "txnTime":"2021-01-20T12:59:07.000+0000",
   * "status":"COMPLETED", "links":[ { "rel":"self", "href":"https://api.dev.paysafe.com/cardpayments/v1/accounts/1002113480/auths/44948e6a-6488-470a-a514-fd4f2717e5ee"
   * } ] }
   */
  @Pact(provider = "pactAuthWorks", consumer = "consumer")
  public RequestResponsePact pactAuthWorks(PactDslWithProvider builder) {
    return builder.given("Auth works")
        .uponReceiving("A request to auth")
        .path("/auth")
        .method("POST")
        .headers("Content-Type", "application/json")
        .body(getRequestBody())
        .willRespondWith()
        .status(200)
        .body(getResponseBody())
        .toPact();
  }

  @Test
  @PactVerification("pactGreetingWorks")
  public void greetingsTest() {
    String greeting = restAppClient.getGreeting("Ivan");
    Assert.assertEquals("Hello, Ivan!", greeting);

  }

  @Test
  @PactVerification("pactAuthWorks")
  public void authTest() {
    AuthsResource request = new AuthsResource();
    AuthsResource response = restAppClient.authorize(request);
    Assert.assertEquals(request.amount, response.amount);
    Assert.assertEquals(request.card.cardNum, response.card.cardNum);
    Assert.assertEquals(request.card.cardExpiry.month, response.card.cardExpiry.month);
    Assert.assertEquals(request.card.cardExpiry.year, response.card.cardExpiry.year);
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


