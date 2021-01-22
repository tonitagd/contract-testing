package com.example.consumer.controllers;

import com.example.consumer.resources.AuthsResource;
import com.example.consumer.services.RestAppClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

  @Autowired
  private RestAppClient restClient;

  @GetMapping(path = "greeting")
  public String getGreeting() {
    return restClient.getGreeting("Consumer");
  }

  @GetMapping(path = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthsResource authorize() {
    return restClient.authorize(new AuthsResource());
  }
}
