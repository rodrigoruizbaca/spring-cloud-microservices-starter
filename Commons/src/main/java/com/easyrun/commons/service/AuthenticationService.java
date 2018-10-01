package com.easyrun.commons.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;

import com.easyrun.commons.dto.ConfigurationDto;
import com.easyrun.commons.rest.CloudRestTemplate;

@Service
public class AuthenticationService {
	
	@Value("${services.auth.name}")
	private String authServiceName = "";
	
	@Autowired 
	private LoadBalancerClient loadBalancer;
	
	@Autowired
	private CloudRestTemplate restTemplate;
	
	public List<ConfigurationDto> getPublicKeys() {
		ServiceInstance instance = loadBalancer.choose(authServiceName);
		ConfigurationDto[] response = restTemplate.getForObject(instance.getUri() + "/JWK", ConfigurationDto[].class);
		return Arrays.asList(response);
	}
}
