package com.easyrun.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize(value = "hasAnyAuthority=(add-role)")
	public @ResponseBody RoleDto addrole(@RequestBody RoleDto role) {
		return authService.addRole(role);
	}
	
	@PostMapping("user")	
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize(value = "hasAnyAuthority=(add-user)")
	public @ResponseBody UserDto addUser(@RequestBody UserDto user) {
		return authService.addUser(user);
	}
	
	@PatchMapping("role/{id}")	
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(value = "hasAnyAuthority=(update-role)")
	public @ResponseBody RoleDto updaterole(@RequestBody RoleDto role, @PathVariable String id) {
		return authService.updateRole(role, id);
	}
	
	@DeleteMapping("role/{id}")	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize(value = "hasAnyAuthority=(delete-role)")
	public void deleteRole(@PathVariable String id) {
		authService.deleteRole(id);
	}
}

