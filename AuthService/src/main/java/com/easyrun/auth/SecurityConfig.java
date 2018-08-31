package com.easyrun.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.easyrun.auth.security.UsernamePasswordAuthentitationProvider;
import com.easyrun.auth.security.UsernamePasswordFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsernamePasswordAuthentitationProvider usernamePasswordAuthentitationProvider;

	public String[] getIgnoredUrls() {
		return new String[] { "/encode/**" };
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordAuthentitationProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/encode/**").permitAll().antMatchers("/token/**").hasAuthority("user.token");
		http.csrf().disable();
	}

	@Bean
	public UsernamePasswordFilter getUsernamePasswordFilter() throws Exception {
		return new UsernamePasswordFilter(this.getIgnoredUrls(), authenticationManager());
	}
}
