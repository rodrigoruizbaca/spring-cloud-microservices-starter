package com.easyrun.auth.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.easyrun.auth.model.Role;
import com.easyrun.commons.dto.RoleDto;
import com.easyrun.commons.transformer.Transformer;
@Component
public class RoleTransformer  implements Transformer<RoleDto, Role>{

	@Override
	public Role toDomain(RoleDto d) {
		Role domain = new Role();
		domain.setId(d.getId());
		BeanUtils.copyProperties(d, domain);
		return domain;
	}

	@Override
	public RoleDto toDto(Role d) {
		RoleDto dto = new RoleDto();
		dto.setId(d.getId());
		BeanUtils.copyProperties(d, dto);
		return dto;
	}
	

}
