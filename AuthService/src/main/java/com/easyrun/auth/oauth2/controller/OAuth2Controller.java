package com.easyrun.auth.oauth2.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.easyrun.commons.dto.ClientDto;
import com.easyrun.commons.dto.TokenDto;

@RestController()
@CrossOrigin
@RequestMapping(value="oauth2", produces = MediaType.APPLICATION_JSON_VALUE)
public class OAuth2Controller {
	
	@PostMapping("token")
	public @ResponseBody TokenDto generateToken() {
		SecurityContext context = SecurityContextHolder.getContext();
		ClientDto client = (ClientDto)context.getAuthentication().getPrincipal();
		TokenDto dto = new TokenDto();
		dto.setAccessToken(client.getToken());
		dto.setType("bearer");
		return dto;
	}
}
