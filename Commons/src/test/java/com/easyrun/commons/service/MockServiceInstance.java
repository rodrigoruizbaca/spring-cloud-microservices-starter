package com.easyrun.commons.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;

public class MockServiceInstance implements ServiceInstance {

	@Override
	public String getServiceId() {
		return "test-service";
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return "localhost";
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 8787;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public URI getUri() {
		try {
			return new URI("http://" + getHost() + ":" + getPort());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, String> getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

}
