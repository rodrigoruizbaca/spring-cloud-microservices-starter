package com.easyrun.auth;

import java.util.UUID;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.easyrun.commons.model.SpringSecurityAuditorAware;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;

@Order(1)
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableMongoAuditing
@EnableCircuitBreaker
@EnableMongoRepositories(basePackages = { "com.easyrun.auth.repository", "com.easyrun.commons.repository",
		"com.easyrun.auth.oauth2.repository" })
@ComponentScan({ "com.easyrun.commons", "com.easyrun.auth" })
public class AuthApplication extends AbstractMongoConfiguration {

	@Value("${spring.data.mongo.uri}")
	private String mongoUri;

	@Value("${spring.data.mongo.databaseName}")
	private String databaseName;

	@Override
	public MongoClient mongoClient() {
		MongoClientURI uri = new MongoClientURI(mongoUri);
		MongoClient mongoClient = new MongoClient(uri);
		return mongoClient;
	}

	@Bean
	public HystrixCommandAspect hystrixAspect() {
		return new HystrixCommandAspect();
	}

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Bean
	public MongoDbFactory mongo() {
		SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongoClient(), databaseName);
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RsaJsonWebKey getRsaJsonWebKey() throws Exception {
		RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		rsaJsonWebKey.setKeyId(getKeyId());
		return rsaJsonWebKey;
	}

	@Bean
	public String getKeyId() {
		return UUID.randomUUID().toString();
	}

	@Bean
	public AuditorAware<String> myAuditorProvider() {
		return new SpringSecurityAuditorAware();
	}
}
