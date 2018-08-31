package com.easyrun.commons.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private String id;
	private String username;
	private String password;
	private String token;
}
