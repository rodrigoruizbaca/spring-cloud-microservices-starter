package com.easyrun.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@RefreshScope
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
