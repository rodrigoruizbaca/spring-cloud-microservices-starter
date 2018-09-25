package com.easyrun.auth.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.rest.filter.EasyGenericFilter;
@WebFilter(urlPatterns = "/login")
public class UsernamePasswordFilter extends EasyGenericFilter {

	private AuthenticationManager authenticationManager;

	public UsernamePasswordFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.getContext();

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String urlPath = httpServletRequest.getServletPath();
		
		if (!super.isIgnoredUrl(urlPath) && super.isMatchedUrl(urlPath)) {
			String username = httpServletRequest.getParameter("username");
			String password = httpServletRequest.getParameter("password");
			if (username == null || password == null) {
				httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
				return;
			}
			//request.setAttribute("excludeFilerToken", true);			
			UserDto u = new UserDto();
			u.setUsername(username);
			u.setPassword(password);
			UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(u, new ArrayList<>());
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

		/*if (httpServletRequest.getHeader("x-auth") != null) {
			chain.doFilter(request, response);
		}
		else if (httpServletRequest.getParameter("grant_type") != null && httpServletRequest.getParameter("grant_type").equals("client_credentials")) {
			chain.doFilter(request, response);
		} else if (!super.isIgnoredUrl(urlPath)) {			
			String username = httpServletRequest.getParameter("username");
			String password = httpServletRequest.getParameter("password");
			if (username == null || password == null) {
				httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
				return;
			}
			request.setAttribute("excludeFilerToken", true);
			request.setAttribute("Oauth2ClientCredentialsFilter", true);
			UserDto u = new UserDto();
			u.setUsername(username);
			u.setPassword(password);
			UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(u, new ArrayList<>());
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
		}*/

	}
	
	public String[] getIgnoredUrls() {
		return new String[] { "/encode/**", "/JWK/**" };
	}

	@Override
	public String[] getMatchUrls() {		
		return new String[] {"/login/**", "/auth/login/**"};
	}

}
