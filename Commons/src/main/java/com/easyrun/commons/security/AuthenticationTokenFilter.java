package com.easyrun.commons.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.security.exception.InvalidTokenException;

import lombok.Getter;
public class AuthenticationTokenFilter extends AbstractUsernamePasswordFilter {

	private AuthenticationManager authenticationManager;
	
	@Getter
	private String ignoredUrls[];
	
	@Getter
	private String matchUrls[];
	
	
	public AuthenticationTokenFilter(AuthenticationManager authenticationManager, String ignoreUrls[], String matchUrls[]) {
        this.authenticationManager = authenticationManager;
        this.ignoredUrls = ignoreUrls;
        this.matchUrls = matchUrls;
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		SecurityContext context = SecurityContextHolder.getContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String urlPath = httpServletRequest.getServletPath();
		
		//boolean skip = httpServletRequest.getAttribute("excludeFilerToken") != null ? (Boolean)httpServletRequest.getAttribute("excludeFilerToken") : false;
		
		//getMappedUrls(httpServletRequest.getSession().getServletContext());
				
		if (super.isMatchedUrl(urlPath) && !super.isIgnoredUrl(urlPath) && !httpServletRequest.getMethod().equalsIgnoreCase("options")) {							
			try {
				String header = httpServletRequest.getHeader("x-auth");
		        // Make sure the header has the JWT token in it
		        if (header != null && header.toLowerCase().startsWith("bearer ")) {
		        	String finalToken = header.substring(7);
		        	UserDto user = new UserDto();
		        	user.setToken(finalToken);
		        	AuthenticationToken authentication = new AuthenticationToken(user, new ArrayList<>());
		        	Authentication auth = authenticationManager.authenticate(authentication);
					if (auth != null && auth.isAuthenticated()) {
						context.setAuthentication(auth);
					}
		        } else {
		        	throw new InvalidTokenException("Token not found");
		        }	
			} catch (AuthenticationException e) {
				logger.error(e.getMessage(), e);
				httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
				return;
			}
			
		}
		
		// Continue on with the filtering within the chain
		chain.doFilter(request, response);
	}


}
