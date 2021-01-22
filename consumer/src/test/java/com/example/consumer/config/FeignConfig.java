package com.example.consumer.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClient(name = "restapp", configuration = RibbonConfig.class)
public class FeignConfig {
}
