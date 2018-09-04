package com.easyrun.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.easyrun.auth.service.AuthService;
import com.easyrun.commons.dto.RoleDto;
import com.easyrun.commons.dto.UserDto;

@RestController()
@CrossOrigin
@RequestMapping(value="auth/oper", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("role")	
	@PreAuthorize(value = "hasAnyAuthority=(add-role)")
	public @ResponseBody RoleDto addrole(@RequestBody RoleDto role) {
		return authService.addRole(role);
	}
	
	@PostMapping("user")	
	@PreAuthorize(value = "hasAnyAuthority=(add-user)")
	public @ResponseBody UserDto addUser(@RequestBody UserDto user) {
		return authService.addUser(user);
	}
		
}
