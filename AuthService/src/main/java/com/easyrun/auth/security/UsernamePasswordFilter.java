package com.easyrun.auth.security;

import java.io.IOException;

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
import com.easyrun.commons.security.AbstractUsernamePasswordFilter;
import com.google.common.collect.Lists;

import lombok.Getter;

public class UsernamePasswordFilter extends AbstractUsernamePasswordFilter {

	private AuthenticationManager authenticationManager;
	@Getter
	private String[] ignoredUrls;

	public UsernamePasswordFilter(String[] ignoredUrls, AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.ignoredUrls = ignoredUrls;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.getContext();

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String urlPath = httpServletRequest.getServletPath();

		if (!super.isIgnored(urlPath)) {
			String username = httpServletRequest.getParameter("username");
			String password = httpServletRequest.getParameter("password");
			UserDto u = new UserDto();
			u.setUsername(username);
			u.setPassword(password);
			UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(u, Lists.newArrayList());
			try {
				Authentication auth = authenticationManager.authenticate(authentication);
				if (auth != null && auth.isAuthenticated()) {
					context.setAuthentication(auth);
				}
			} catch (AuthenticationException e) {
				httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
				return;
			}
		}

		chain.doFilter(request, response);

	}

}
