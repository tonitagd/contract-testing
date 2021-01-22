package com.example.consumer.config;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {
  @Bean
  public ServerList<Server> serverList() {
    return new StaticServerList<>(new Server("127.0.0.1", 1234), new Server("127.0.0.1", 1234));
  }
}
