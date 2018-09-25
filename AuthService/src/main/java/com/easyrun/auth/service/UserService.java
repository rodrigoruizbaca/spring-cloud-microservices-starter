package com.easyrun.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easyrun.auth.model.QUser;
import com.easyrun.auth.model.User;
import com.easyrun.auth.repository.UserRepository;
import com.easyrun.auth.transformer.UserTransformer;
import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.service.CrudSupportServiceImpl;

@Service("userService")
public class UserService extends CrudSupportServiceImpl <
User, UserDto, QUser, String, String, UserTransformer, UserRepository> {
	@Autowired
	private UserTransformer userTransfomer;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

	@Override
	public UserTransformer getTransformer() {
		return userTransfomer;
	}
	
	@Override
	protected User beforeAdd(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return user;
	}
	
	@Override
	protected User beforeUpdate(User user, UserDto dto) {
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(dto.getRoles());
		return user;
	}
}
