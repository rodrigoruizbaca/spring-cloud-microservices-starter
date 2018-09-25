package com.easyrun.auth.oauth2.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;


@Service("oAuth2Service")
public class OAuth2Service  { 
	
	public String generateClientId() {
		Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < 32){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 32);
	}
	
	public String generateClientSecret() {
		byte[] b = new byte[256];
		new Random().nextBytes(b);
		String sha256hex = Hashing.sha256().hashBytes(b)
				  .toString();
		return sha256hex;
	}

	
	
}
