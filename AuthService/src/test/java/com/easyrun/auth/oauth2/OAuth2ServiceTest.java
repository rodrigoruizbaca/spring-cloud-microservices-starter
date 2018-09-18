package com.easyrun.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.easyrun.auth.oauth2.service.OAuth2Service;
import com.google.common.collect.Sets;

public class OAuth2ServiceTest {
	
	OAuth2Service service;
	
	@Before
	public void init() {
		service = mock(OAuth2Service.class);
	}
	
	@Test
	public void generateClientIdTest() {
		when(service.generateClientId()).thenCallRealMethod();
		String resp = service.generateClientId();
		int expectedLenght = 32;
		assertEquals(expectedLenght, resp.length());
		
		Set<String> generated = Sets.newHashSet();
		
		for (int x = 0; x < 100; x++) {
			generated.add(service.generateClientId());
		}
		assertEquals(100, generated.size());
	}
	
	@Test
	public void generateClientSecretTest() {
		when(service.generateClientSecret()).thenCallRealMethod();
		Set<String> generated = Sets.newHashSet();
		
		for (int x = 0; x < 100; x++) {
			generated.add(service.generateClientSecret());
		}
		assertEquals(100, generated.size());
	}
	
}
