package com.example.consumer.config;

import au.com.dius.pact.core.model.annotations.PactFolder;
import com.example.consumer.ConsumerApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {ConsumerApplication.class},
    properties = {"eureka.client.enabled:false", "eureka.instance.prefer-ip-address:true"})
@PactFolder(value = "pacts")
public abstract class BaseIntegrationTest {

  @LocalServerPort
  private int port;

  @Before
  public void baseComponentSetUp() {
    RestAssured.port = port;
  }

}
