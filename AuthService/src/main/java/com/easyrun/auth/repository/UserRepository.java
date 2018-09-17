package com.easyrun.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.easyrun.auth.model.User;
import com.easyrun.commons.Repository.EasyRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User>, EasyRepository<User, String> { 
	
	@Query(value = "{username: ?0}")
	public User getByUniqueKey(String username);

}
