package com.easyrun.commons.model;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyrun.commons.dto.UserDto;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		if (authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof UserDto)) {
			return Optional.empty();
		}
		
		return Optional.of( ((UserDto) authentication.getPrincipal()).getId() );
	}

}
