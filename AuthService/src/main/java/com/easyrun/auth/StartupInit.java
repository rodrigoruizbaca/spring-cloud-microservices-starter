package com.easyrun.auth;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.easyrun.auth.model.Configuration;
import com.easyrun.auth.model.Role;
import com.easyrun.auth.model.User;
import com.easyrun.auth.repository.ConfigurationRepository;
import com.easyrun.auth.repository.RoleRepository;
import com.easyrun.auth.repository.UserRepository;

@Component
public class StartupInit {
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RsaJsonWebKey rsaJsonWebKey;
	
	@Autowired
	private PasswordEncoder encoder;
	
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
			createSuperAdmin();
		}		
	}
	
	private void createSuperAdmin() {
		Role superAdminRole = new Role();
		superAdminRole.setRoleCd("SUPER_ADMIN");
		Optional<Role> entity = roleRepository.findOne(Example.of(superAdminRole));
		if (!entity.isPresent()) {
			superAdminRole.setPermissions(Arrays.asList(new String[]{"add-role", "add-user"}));
			roleRepository.insert(superAdminRole);
		}
		User superUser = new User();
		superUser.setUsername("admin");		
		Optional<User> userEntity = userRepository.findOne(Example.of(superUser));
		if (!userEntity.isPresent()) {
			superUser.setPassword(encoder.encode("@dm1n"));
			superUser.setRoles(Arrays.asList(new String[]{superAdminRole.getId()}));
			userRepository.insert(superUser);
		}		
	}
}
