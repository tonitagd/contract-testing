package com.example.consumer.config;

import com.example.consumer.ConsumerApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {ConsumerApplication.class, FeignConfig.class},
    properties = {"eureka.client.enabled:false", "eureka.instance.prefer-ip-address:true"})
public abstract class BaseIntegrationTest {
}
