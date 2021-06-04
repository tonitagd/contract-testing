package com.example.consumer.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("${secondUrl}")
public interface SecondAppClient {

  @GetMapping("fullname/{name}")
  String getFullName(@PathVariable("name") String name);
}
