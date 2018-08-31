package com.easyrun.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.easyrun.auth.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> { 
	public User getByUsername(String username);

}
