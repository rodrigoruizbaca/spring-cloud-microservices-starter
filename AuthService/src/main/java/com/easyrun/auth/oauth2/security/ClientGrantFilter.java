package com.easyrun.auth.oauth2.security;

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

import com.easyrun.commons.dto.ClientDto;
import com.easyrun.commons.security.EasyGenericFilter;

public class ClientGrantFilter extends EasyGenericFilter {

	private AuthenticationManager authenticationManager;
	
	public ClientGrantFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager; 
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.getContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String urlPath = httpServletRequest.getServletPath();
						
		if (!super.isIgnoredUrl(urlPath) && super.isMatchedUrl(urlPath) && httpServletRequest.getParameter("grant_type") != null && httpServletRequest.getParameter("grant_type").equals("client_credentials")) {
			String clientId = httpServletRequest.getParameter("client_id");
			String clientSecret = httpServletRequest.getParameter("client_secret");
			if (clientId == null || clientSecret == null) {
				httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
				return;
			}
			ClientDto client = new ClientDto();
			client.setClientId(clientId);
			client.setSecret(clientSecret);
			ClientAuthentication authentication = new ClientAuthentication(client, new ArrayList<>());
			try {
				Authentication auth = authenticationManager.authenticate(authentication);
				if (auth != null && auth.isAuthenticated()) {
					context.setAuthentication(auth);
				}
			} catch (AuthenticationException e) {
				httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
				return;
			}
			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
		}				
	}

	@Override
	public String[] getIgnoredUrls() {		
		return new String[0];
	}

	@Override
	public String[] getMatchUrls() {		
		return new String[] {"oauth2/token/**"};
	}

}
