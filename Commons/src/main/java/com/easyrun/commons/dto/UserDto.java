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
public class UserDto {
	@Null(groups = NewValidator.class)
	private String id;
	@NotNull(
	        message = "Username is required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)
	private String username;
	@NotNull(
	        message = "Password is required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)	
	private String password;
	
	@NotNull(
	        message = "Roles are required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)
	private List<String> roles;
	
	private String token;
	private String audience;
}
