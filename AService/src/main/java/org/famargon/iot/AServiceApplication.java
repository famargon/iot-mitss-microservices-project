package org.famargon.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.context.request.RequestContextListener;

@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@SpringBootApplication
public class AServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AServiceApplication.class, args);
	}
	
	@Bean
	public Sampler alwaysSample() {
		return new AlwaysSampler();
	}
	
	@Bean
	public RequestContextListener requestContextListener() {
	    return new RequestContextListener();
	}
	
	/**
	 * Para que se reenvie el token oauth a las llamadas a sucesivos servicios
	 */
	
	@Autowired
	OAuth2ClientContext oAuth2ClientContext;
	@Autowired
	OAuth2ProtectedResourceDetails resource;
	
	@Bean
	public OAuth2FeignRequestInterceptor oauth2Feign() {
		return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource);
	}
}
