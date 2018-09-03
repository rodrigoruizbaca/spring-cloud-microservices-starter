package com.easyrun.auth.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.easyrun.auth.model.User;
import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.transformer.Transformer;
@Component
public class UserTransformer implements Transformer<UserDto, User> {
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
		domain.setId(d.getId());
		BeanUtils.copyProperties(d, domain);
		return domain;
	}
}
