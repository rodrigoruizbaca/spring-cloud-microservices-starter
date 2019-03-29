package com.easyrun.commons.service;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("S")
public class SecurityService {
	
	@SuppressWarnings("unchecked")
	protected Collection<SimpleGrantedAuthority> getSignedAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<SimpleGrantedAuthority> auths = (Collection<SimpleGrantedAuthority>)authentication.getAuthorities();
		return auths;
	}
		
	public boolean hasAuthorityAsPattern(String pattern) {	
		Collection<SimpleGrantedAuthority> auths = getSignedAuthorities();
		return auths.stream().anyMatch(auth -> {
			if (auth.getAuthority().equals("*")) {
				return true;
			} else if (auth.getAuthority().startsWith("*-")) {
				String authority = auth.getAuthority().replace("*-", "");
				String pat = pattern.replaceAll("(?i)[^-]*", "");
				return authority.equalsIgnoreCase(pat);
			} else if (auth.getAuthority().endsWith("-*")) {
				String authority = auth.getAuthority().replace("-*", "");
				String pat = pattern.replaceAll("(?i)-.*", "");
				return authority.equalsIgnoreCase(pat);
			} else {
				return auth.getAuthority().equalsIgnoreCase(pattern);
			}				
		});		
	}
	
}
