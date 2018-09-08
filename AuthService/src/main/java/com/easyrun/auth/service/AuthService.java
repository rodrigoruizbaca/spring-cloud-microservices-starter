package com.easyrun.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.easyrun.auth.model.Configuration;
import com.easyrun.auth.model.Role;
import com.easyrun.auth.model.User;
import com.easyrun.auth.repository.ConfigurationRepository;
import com.easyrun.auth.repository.RoleRepository;
import com.easyrun.auth.repository.UserRepository;
import com.easyrun.auth.transformer.ConfigurationTransformer;
import com.easyrun.auth.transformer.RoleTransformer;
import com.easyrun.auth.transformer.UserTransformer;
import com.easyrun.commons.dto.ConfigurationDto;
import com.easyrun.commons.dto.RoleDto;
import com.easyrun.commons.dto.UserDto;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private ConfigurationTransformer configurationTransformer;
	
	@Autowired
	private RoleTransformer roleTransfomer;
	
	@Autowired
	private UserTransformer userTransfomer;
	
	public RoleDto addRole(RoleDto role) {
		Role entity = roleRepository.getByRoleCd(role.getRoleCd());
		if (entity == null) {
			entity = roleTransfomer.toDomain(role);
			return roleTransfomer.toDto(roleRepository.insert(entity));
		}
		throw new DuplicateKeyException("The key for role" + role.getRoleCd() + " already exists");
	}
	
	public UserDto addUser(UserDto user) {
		User entity = userRepository.getByUsername(user.getUsername());
		if (entity == null) {
			entity = userTransfomer.toDomain(user);
			return userTransfomer.toDto(userRepository.insert(entity));
		}
		throw new DuplicateKeyException("The username " + user.getUsername() + " already exists");
	}
	
	public List<ConfigurationDto> getPublicKeys() {
		List<Configuration> configs = configurationRepository.getConfigurationByName("PUBLIC_KEY");
		return configurationTransformer.toDtoLst(configs);
	}
}
