package com.easyrun.auth.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyrun.auth.service.AuthService;
import com.easyrun.commons.dto.ExistingValidator;
import com.easyrun.commons.dto.NewValidator;
import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.exception.EntityNotFoundException;

@RestController()
@CrossOrigin
@RequestMapping(value="auth/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private AuthService authService;
	
	
	@PostMapping()	
	@PreAuthorize("@S.hasAuthorityAsPattern('add-user')")
	public  ResponseEntity<?> addUser(@Validated(NewValidator.class) @RequestBody UserDto user) {
		return ResponseEntity.created(URI.create("/user")).body(authService.addUser(user));
	}	
	
	@PatchMapping("/{id}")	
	@PreAuthorize("@S.hasAuthorityAsPattern('update-user')")
	public ResponseEntity<?> updateUser(@Validated(ExistingValidator.class) @RequestBody UserDto user, @PathVariable String id) {
		try {
			return ResponseEntity.ok(authService.updateUser(user, id));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("")	
	@PreAuthorize("@S.hasAuthorityAsPattern('get-user')")
	public ResponseEntity<?> getUsers() {		
		return ResponseEntity.ok(authService.getUsers());		
	}
	
	@DeleteMapping("/{id}")	
	@PreAuthorize("@S.hasAuthorityAsPattern('delete-user')")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		try {
			authService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
