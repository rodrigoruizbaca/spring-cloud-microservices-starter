package com.easyrun.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.easyrun.commons.dto.ConfigurationDto;

public class TestUtilities {
	public static ConfigurationDto[] getKeys() {
		ConfigurationDto config = new ConfigurationDto();
		config.setHeaderKeyId("9d6a5535-318d-4b0c-838f-4c2640916af9");
		config.setId("12345");
		config.setName("PUBLIC_KEY");
		config.setPublicKey("{\"kty\":\"RSA\",\"kid\":\"9d6a5535-318d-4b0c-838f-4c2640916af9\",\"n\":\"n2Yobl2DlxQOcwQBqH5Yw8Y253mes97MBY1IHcUH4p0ce9SpwQi47uPPzHsYG8jDL0dysRkLEzoIZTxqyn2zGPvdr6Sfpww27DGdwypxYhNbGvlTF7co481EpppQmVZcc-ycWUR5NzjBuKaWrik178tozRz071wwA-aC-GjPy6_KgjyYbHXY78jbxLTX3nzOP_YVYDC-szEF6aXwjq9m9WcQWK60XM0mcBZnPEaxudrPsvgLjRjRLEX4Ce3NYBHkW2BNXa04Owa8DV8S3ig_03VpNOrfxlStZPYKZlvqlZV0BzM8AAqgHeT_bm9xZwZAa-9bssydvqnV91AhQ4y7hQ\",\"e\":\"AQAB\"}");
		return new ConfigurationDto[] {config};
	}
	
	public static Collection<SimpleGrantedAuthority> getSignedAuthoritiesAsSuperAdmin() {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority("*");
		auths.add(auth);
		return auths;
	}
	
	public static Collection<SimpleGrantedAuthority> getSignedAuthoritiesAsReader() {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority("get-*");
		auths.add(auth);
		return auths;
	}
	
	public static Collection<SimpleGrantedAuthority> getSignedAuthoritiesAsWriter() {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority("add-*");
		SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("update-*");
		SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("delete-*");
		auths.add(auth);
		auths.add(auth1);
		auths.add(auth2);
		return auths;
	}
	
	public static Collection<SimpleGrantedAuthority> getSignedAuthoritiesAsUserAdmin() {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority("*-user");
		auths.add(auth);
		return auths;
	}
}
