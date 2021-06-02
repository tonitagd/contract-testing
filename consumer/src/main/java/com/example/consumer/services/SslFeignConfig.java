package com.example.consumer.services;

import feign.Client;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class SslFeignConfig {

  @Bean
  public Client feignClient() {
    Client trustSSLSockets = new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
    return trustSSLSockets;
  }

  private SSLSocketFactory getSSLSocketFactory() {
    try {
      TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          //Do your validations
          return true;
        }
      };
      String allPassword = "brentwashere";
      SSLContext sslContext = SSLContextBuilder
          .create()
          .loadKeyMaterial(ResourceUtils.getFile("classpath:keystore/pact-jvm-512.jks"), allPassword.toCharArray(),
              allPassword.toCharArray())
          .loadTrustMaterial(ResourceUtils.getFile("classpath:keystore/pact-jvm-512.jks"), allPassword.toCharArray())
          .build();
      return sslContext.getSocketFactory();
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
