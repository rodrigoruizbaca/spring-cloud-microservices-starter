package com.easyrun.auth.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.easyrun.commons.model.Auditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class User extends Auditable<String> {	
	private String username;
	private String password;
	private String audience;
	private List<String> roles;
}
