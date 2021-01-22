package com.example.restapp.controllers;

import com.example.restapp.feign.clients.CardClient;
import com.example.restapp.resources.AuthsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

  @Autowired
  private CardClient cardClient;
  @Value("${account}")
  private String account;

  @GetMapping("greeting/{name}")
  public String getVersion(@PathVariable String name) {
    System.out.println("Calling greeting controller...");
    return String.format("Hello, %s!", name);
  }

  @PostMapping("auth")
  public AuthsResource authorize(AuthsResource resource) {
    return cardClient.authorize(resource, account);
  }
}
