package com.easyrun.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyrun.auth.model.Configuration;
import com.easyrun.auth.repository.ConfigurationRepository;
import com.easyrun.auth.transformer.ConfigurationTransformer;
import com.easyrun.commons.dto.ConfigurationDto;

@Service
public class AuthService {

	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private ConfigurationTransformer configurationTransformer;
		
	
	public List<ConfigurationDto> getPublicKeys() {
		List<Configuration> configs = configurationRepository.getConfigurationByName("PUBLIC_KEY");
		return configurationTransformer.toDtoLst(configs);
	}
}
