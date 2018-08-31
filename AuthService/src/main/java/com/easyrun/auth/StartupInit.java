package com.easyrun.auth;

import javax.annotation.PostConstruct;

import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easyrun.commons.model.Configuration;
import com.easyrun.commons.repository.ConfigurationRepository;

@Component
public class StartupInit {
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private RsaJsonWebKey rsaJsonWebKey;
	
	@Value("${isMaster}")
	private String isMaster;
		
	@PostConstruct
	public void init() throws Exception {
		boolean isMasterFlag = Boolean.valueOf(isMaster);
		if (isMasterFlag) {
			configurationRepository.deleteAll();
			Configuration c = new Configuration();			
			c.setPublicKey(rsaJsonWebKey.toJson());
			c.setHeaderKeyId(rsaJsonWebKey.getKeyId());
			c.setName("PUBLIC_KEY");
			configurationRepository.insert(c);
		}
	}
}
