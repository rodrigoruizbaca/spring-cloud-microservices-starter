package com.easyrun.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "com.easyrun.auth.repository")
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${mongo.uri}")
	private String mongoUri;

	@Value("${mongo.databaseName}")
	private String databaseName;

	// mongodb+srv://starter:<PASSWORD>@cluster0-wzmuc.mongodb.net/test?retryWrites=true

	@Override
	public MongoClient mongoClient() {
		MongoClientURI uri = new MongoClientURI(mongoUri);
		MongoClient mongoClient = new MongoClient(uri);
		return mongoClient;
	}

	public @Bean MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), getDatabaseName());
	}

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

}
