package com.easyrun.discovery.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@CrossOrigin
@RequestMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiscoveryController {
	
	@Value("${rodrigo.test}")
	private String test;
	
	@GetMapping("test")	
	public String test() {
		return test;
	}
}
