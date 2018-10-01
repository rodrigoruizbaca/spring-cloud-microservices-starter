package com.easyrun.commons.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import com.easyrun.commons.TestUtilities;
import com.easyrun.commons.dto.ConfigurationDto;
import com.easyrun.commons.rest.CloudRestTemplate;

public class AuthenticationServiceTest {
	@Before
	public void initTest() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	private AuthenticationService service;

	@Mock
	private LoadBalancerClient loadBalancer;

	@Mock
	private CloudRestTemplate restTemplate;


	@Test
	public void testGetPublicKeys() {
		MockServiceInstance externalService = new MockServiceInstance();
		
		when(restTemplate.getForObject(externalService.getUri() + "/JWK", ConfigurationDto[].class))
				.thenReturn(TestUtilities.getKeys());
		when(loadBalancer.choose(anyString())).thenReturn(externalService);
		
		List<ConfigurationDto> keys = service.getPublicKeys();
		verify(loadBalancer, times(1)).choose(anyString());
		verify(restTemplate, times(1)).getForObject(externalService.getUri() + "/JWK", ConfigurationDto[].class);
		assertEquals(1, keys.size());
	}

}
