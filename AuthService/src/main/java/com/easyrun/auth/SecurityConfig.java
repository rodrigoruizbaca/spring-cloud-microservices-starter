package com.easyrun.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.easyrun.auth.security.UsernamePasswordAuthentitationProvider;
import com.easyrun.auth.security.UsernamePasswordFilter;
import com.easyrun.commons.security.AuthenticationTokenFilter;
import com.easyrun.commons.security.AuthenticationTokenProvider;
@Order(2)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsernamePasswordAuthentitationProvider usernamePasswordAuthentitationProvider;
	
	@Autowired
	private AuthenticationTokenProvider authenticationTokenProvider;

	public String[] getIgnoredUrls() {
		return new String[] { "/encode/**", "/JWK/**" };
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordAuthentitationProvider);
		auth.authenticationProvider(authenticationTokenProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		/*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/encode/**").permitAll().antMatchers("/token/**").hasAuthority("user.token");
		http.csrf().disable();*/
		http
	    .authorizeRequests()
	        // 1
	        .requestMatchers(EndpointRequest.to("status", "info")).permitAll();
	}

	@Bean
	public UsernamePasswordFilter getUsernamePasswordFilter() throws Exception {
		return new UsernamePasswordFilter(this.getIgnoredUrls(), authenticationManager());
	}
	
	@Bean
	public AuthenticationTokenFilter getAuthenticationTokenFilter() throws Exception {
		return new AuthenticationTokenFilter(authenticationManager());
	}
}
