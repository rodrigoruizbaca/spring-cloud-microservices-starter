package com.easyrun.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easyrun.auth.model.Configuration;
import com.easyrun.auth.model.QRole;
import com.easyrun.auth.model.QUser;
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
import com.easyrun.commons.exception.EntityNotFoundException;
import com.easyrun.commons.rest.filter.EasyCustomRqslVisitor;
import com.querydsl.core.types.Predicate;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

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
	
	@Autowired
	private PasswordEncoder encoder;
	
	public RoleDto addRole(RoleDto role) {
		Role entity = roleRepository.getByRoleCd(role.getRoleCd());
		if (entity == null) {
			entity = roleTransfomer.toDomain(role);
			return roleTransfomer.toDto(roleRepository.insert(entity));
		}
		throw new DuplicateKeyException("The key for role" + role.getRoleCd() + " already exists");
	}
	
	public Page<RoleDto> getRoles(Pageable pageable) {
		return getRoles(pageable, null);
	}
	
	public Page<RoleDto> getRoles(Pageable pageable, String search) {
		Page<Role> entityPage = null;
		if (search != null) {
			Node rootNode = new RSQLParser().parse(search);
			EasyCustomRqslVisitor<QRole> visitor = new EasyCustomRqslVisitor<QRole>(QRole.role);
			Predicate p = rootNode.accept(visitor);
			entityPage = roleRepository.findAll(p, pageable); 
		} else {
			entityPage = roleRepository.findAll(pageable); 
		}
		
		Page<RoleDto> page = new PageImpl<>(roleTransfomer.toDtoLst(entityPage.getContent()), pageable, entityPage.getTotalElements());
		return page;				
	}
	
	public RoleDto updateRole(RoleDto role, String id) throws EntityNotFoundException {
		Optional<Role> optional = roleRepository.findById(id);		
		if (optional.isPresent()) {
			Role entity = optional.get();			
			entity.setPermissions(role.getPermissions());
			return roleTransfomer.toDto(roleRepository.save(entity));
		} else {
			throw new EntityNotFoundException("The role with id " + id + " does not exists");
		}		
	}
	
	public void deleteRole(String id) throws EntityNotFoundException {
		Optional<Role> optional = roleRepository.findById(id);
		if (optional.isPresent()) {			
			roleRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("The role with id " + id + " does not exists");
		}		
	}
	
	public UserDto addUser(UserDto user) {
		User entity = userRepository.getByUsername(user.getUsername());
		if (entity == null) {
			entity = userTransfomer.toDomain(user);
			entity.setPassword(encoder.encode(user.getPassword()));
			return userTransfomer.toDto(userRepository.insert(entity));
		}
		throw new DuplicateKeyException("The username " + user.getUsername() + " already exists");
	}
	
	public Page<UserDto> getUsers(Pageable pageable) {		
		return getUsers(pageable, null);
	}
	
	public Page<UserDto> getUsers(Pageable pageable, String search) {
		Page<User> entityPage = null;
		if (search != null) {
			Node rootNode = new RSQLParser().parse(search);
			EasyCustomRqslVisitor<QUser> visitor = new EasyCustomRqslVisitor<QUser>(QUser.user);
			Predicate p = rootNode.accept(visitor);
			entityPage = userRepository.findAll(p, pageable); 
		} else {
			entityPage = userRepository.findAll(pageable);		
		}
		Page<UserDto> page = new PageImpl<>(userTransfomer.toDtoLst(entityPage.getContent()), pageable, entityPage.getTotalElements());
		return page;			
	}
	
	public UserDto updateUser(UserDto user, String id) throws EntityNotFoundException {
		Optional<User> optional = userRepository.findById(id);		
		if (optional.isPresent()) {
			User entity = optional.get();			
			entity.setPassword(encoder.encode(user.getPassword()));
			entity.setRoles(user.getRoles());
			return userTransfomer.toDto(userRepository.save(entity));
		} else {
			throw new EntityNotFoundException("The user with id " + id + " does not exists");
		}		
	}
	
	public void deleteUser(String id) throws EntityNotFoundException {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isPresent()) {			
			userRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("The user with id " + id + " does not exists");
		}		
	}
	
	public List<ConfigurationDto> getPublicKeys() {
		List<Configuration> configs = configurationRepository.getConfigurationByName("PUBLIC_KEY");
		return configurationTransformer.toDtoLst(configs);
	}
}
