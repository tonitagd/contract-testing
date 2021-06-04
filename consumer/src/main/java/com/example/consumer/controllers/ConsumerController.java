package com.example.consumer.controllers;

import com.example.consumer.resources.AuthsResource;
import com.example.consumer.services.RestAppClient;
import com.example.consumer.services.SecondAppClient;
import com.example.consumer.services.SslFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

  @Autowired
  private RestAppClient restClient;

  @Autowired
  private SecondAppClient secondAppClient;

  @Autowired
  private SslFeignClient sslFeignClient;

  @GetMapping(path = "greeting")
  public String getGreeting() {
    return restClient.getGreeting("Consumer");
  }

  @GetMapping(path = "fullGreeting")
  public String fullGreeting() {
    String consumer = secondAppClient.getFullName("Consumer");
    return restClient.getGreeting(consumer);
  }

  @GetMapping(path = "secureGreeting")
  public String getSecureGreeting() {
    return sslFeignClient.getGreeting("SecureConsumer");
  }

  @GetMapping(path = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthsResource authorize() {
    return restClient.authorize(new AuthsResource());
  }
}
