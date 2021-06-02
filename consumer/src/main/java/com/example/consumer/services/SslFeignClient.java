package com.example.consumer.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "https://127.0.0.1:1234", configuration = SslFeignConfig.class)
public interface SslFeignClient {

  @GetMapping("secureGreeting/{name}")
  String getGreeting(@PathVariable("name") String name);
}
