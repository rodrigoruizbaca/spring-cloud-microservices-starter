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
public class ClientDto implements EasyDto<String, String> {
	@Null(groups = NewValidator.class)
	private String id;
	
	@NotNull(
	        message = "Client name is required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)
	private String name;
	
	
	private String clientId;		
	private String secret;
	
	@NotNull(
	        message = "Client role list is required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)
	private List<String> roles;
	
	@NotNull(
	        message = "Client type is required",
	        groups = {NewValidator.class, ExistingValidator.class}
	)
	private String type;
	
	@Override
	public String geUniqueKey() {
		return clientId;
	}
	
	private String token;

}
