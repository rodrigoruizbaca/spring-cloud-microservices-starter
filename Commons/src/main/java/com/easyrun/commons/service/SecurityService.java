package com.easyrun.commons.service;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("S")
public class SecurityService /*implements PermissionEvaluator*/ {
	
	
	
	@SuppressWarnings("unchecked")
	public boolean hasAuthorityAsPattern(String pattern) {	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<SimpleGrantedAuthority> auths = (Collection<SimpleGrantedAuthority>)authentication.getAuthorities();
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

	/*@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
		//String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
		return hasAuthorityAsPattern(authentication, permission.toString());		
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if ((authentication == null) || (targetType == null) || !(permission instanceof String)){
            return false;
        }
		return hasAuthorityAsPattern(authentication, permission.toString());
	}*/
}
