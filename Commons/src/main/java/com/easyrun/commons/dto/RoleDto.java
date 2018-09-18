package com.easyrun.commons.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class RoleDto implements EasyDto<String, String> {		
    @Null(groups = NewValidator.class)
	private String id;
	@NotNull(
	        message = "Role code is required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)
	private String roleCd;
	
	@NotNull(
	        message = "Permissions are required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)
	private List<String> permissions;

	@Override
	public String geUniqueKey() {
		// TODO Auto-generated method stub
		return roleCd;
	}
	
	
	
}
