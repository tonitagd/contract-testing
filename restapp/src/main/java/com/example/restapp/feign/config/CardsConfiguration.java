package com.example.restapp.feign.config;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardsConfiguration {

  @Value("${user}")
  private String user;

  @Value("${password}")
  private String password;

  @Bean
  public Decoder decoder() {
    return new JacksonDecoder();
  }

  @Bean
  public Encoder encoder() {
    return new JacksonEncoder();
  }

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor(user, password);
  }
}
