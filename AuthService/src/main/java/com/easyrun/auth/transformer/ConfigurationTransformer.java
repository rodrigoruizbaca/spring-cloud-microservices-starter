package com.easyrun.auth.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.easyrun.auth.model.Configuration;
import com.easyrun.commons.dto.ConfigurationDto;
import com.easyrun.commons.transformer.Transformer;
@Component
public class ConfigurationTransformer implements Transformer<ConfigurationDto, Configuration> {

	@Override
	public ConfigurationDto toDto(Configuration d) {
		ConfigurationDto domain = new ConfigurationDto();		
		BeanUtils.copyProperties(d, domain);
		return domain;
	}

	@Override
	public Configuration toDomain(ConfigurationDto d) {
		Configuration domain = new Configuration();		
		BeanUtils.copyProperties(d, domain);
		return domain;
	}

}
