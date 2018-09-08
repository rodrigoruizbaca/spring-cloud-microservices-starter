package com.easyrun.auth.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.easyrun.auth.model.Configuration;

@Repository
public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
	public List<Configuration> getConfigurationByName(String name); 

}
