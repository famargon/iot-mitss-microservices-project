package org.famargon.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class OAuthAuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthAuthorizationServerApplication.class, args);
	}
	
	@Bean
	public Sampler alwaysSample() {
		return new AlwaysSampler();
	}
}
