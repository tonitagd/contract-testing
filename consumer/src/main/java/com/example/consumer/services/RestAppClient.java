package com.example.consumer.services;

import com.example.consumer.resources.AuthsResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("restapp")
public interface RestAppClient {

  @GetMapping("greeting/{name}")
  String getGreeting(@PathVariable("name") String name);

  @PostMapping(value = "auth",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  AuthsResource authorize(@RequestBody AuthsResource request);
}
