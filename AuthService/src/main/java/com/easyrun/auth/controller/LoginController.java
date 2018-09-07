package com.easyrun.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.easyrun.auth.service.AuthService;
import com.easyrun.commons.dto.ConfigurationDto;
import com.easyrun.commons.dto.TokenDto;
import com.easyrun.commons.dto.UserDto;

@RestController()
@CrossOrigin
@RequestMapping(value="auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("login")
	public @ResponseBody TokenDto generateToken() {
		SecurityContext context = SecurityContextHolder.getContext();
		UserDto user = (UserDto)context.getAuthentication().getPrincipal();
		TokenDto dto = new TokenDto();
		dto.setAccessToken(user.getToken());
		dto.setType("bearer");
		return dto;
	}
	
	@GetMapping("encode")
	public @ResponseBody UserDto encode(@RequestParam("plain") String plain) {
		UserDto u = new UserDto();
		u.setPassword(new BCryptPasswordEncoder().encode(plain));
		return u;
	}
	
	@GetMapping("JWK")
	public @ResponseBody List<ConfigurationDto> getKeys() {
		return authService.getPublicKeys();
	}
}
