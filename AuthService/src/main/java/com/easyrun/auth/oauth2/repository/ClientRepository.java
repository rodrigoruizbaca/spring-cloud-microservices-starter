package com.easyrun.auth.oauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.easyrun.auth.oauth2.model.Client;
import com.easyrun.commons.Repository.EasyRepository;
@Repository
public interface ClientRepository extends MongoRepository<Client, String>, QuerydslPredicateExecutor<Client>, EasyRepository<Client, String> {
	@Query(value = "{clientId: ?0}")
	Client getByUniqueKey(String clientId);
}
