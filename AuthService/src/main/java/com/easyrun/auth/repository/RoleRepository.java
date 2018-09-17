package com.easyrun.auth.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.easyrun.auth.model.Role;
import com.easyrun.commons.Repository.EasyRepository;
@Repository
public interface RoleRepository extends MongoRepository<Role, String>, QuerydslPredicateExecutor<Role>, EasyRepository<Role, String> {
	
	List<Role> getRolesByIdIn(List<String> ids);
	
	@Query(value = "{roleCd: ?0}")
	Role getByUniqueKey(String roleCd);
}
	