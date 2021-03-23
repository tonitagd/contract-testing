package com.example.restapp;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.RestPactRunner;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@RunWith(RestPactRunner.class) // Say JUnit to run tests with custom Runner
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PactTest {
  @TestTarget
  public final Target target = new HttpTarget( "127.0.0.1", 8083);

  private static ConfigurableWebApplicationContext application;

  @BeforeClass
  public static void start() {
    application = (ConfigurableWebApplicationContext) SpringApplication.run(RestappApplication.class);
  }
}
