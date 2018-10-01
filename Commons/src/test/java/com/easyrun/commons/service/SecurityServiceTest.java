package com.easyrun.commons.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.easyrun.commons.TestUtilities;

public class SecurityServiceTest {
	
	@Before
	public void initTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Mock
	private SecurityService service;
	
	@Test
	public void testHasAuthorityAsPattern() {		
		when(service.hasAuthorityAsPattern(anyString())).thenCallRealMethod();
		
		when(service.getSignedAuthorities()).thenReturn(TestUtilities.getSignedAuthoritiesAsSuperAdmin());
		assertEquals(true, service.hasAuthorityAsPattern("add-user"));
		
		when(service.getSignedAuthorities()).thenReturn(TestUtilities.getSignedAuthoritiesAsReader());
		assertEquals(false, service.hasAuthorityAsPattern("add-user"));
		assertEquals(true, service.hasAuthorityAsPattern("get-user"));
	}
}
