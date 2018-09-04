package com.easyrun.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = {"com.easyrun.auth.repository", "com.easyrun.commons.repository"})
public class MongoConfig {

	@Value("${spring.data.mongo.uri}")
	private String mongoUri;

	@Value("${spring.data.mongo.databaseName}")
	private String databaseName;


	/*@Override
	public MongoClient mongoClient() {
		MongoClientURI uri = new MongoClientURI(mongoUri);
		MongoClient mongoClient = new MongoClient(uri);
		return mongoClient;
	}


	@Override
	protected String getDatabaseName() {
		return databaseName;
	}*/
	
	@Bean
	public MongoDbFactory mongo() {
		MongoClientURI uri = new MongoClientURI(mongoUri);
		MongoClient mongoClient = new MongoClient(uri);
		SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongoClient, databaseName);	    
	    return factory;
	}

}
