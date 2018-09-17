package com.easyrun.auth.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.easyrun.auth.model.User;
import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.transformer.Transformer;
@Component
public class UserTransformer implements Transformer<UserDto, User> {
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public UserDto toDto(User d) {
		UserDto dto = new UserDto();
		dto.setId(d.getId());
		BeanUtils.copyProperties(d, dto);
		return dto;
	}

	@Override
	public User toDomain(UserDto d) {
		User domain = new User();				
		BeanUtils.copyProperties(d, domain, "password");
		domain.setPassword(encoder.encode(d.getPassword()));
		domain.setId(d.getId());
		return domain;
	}
}
