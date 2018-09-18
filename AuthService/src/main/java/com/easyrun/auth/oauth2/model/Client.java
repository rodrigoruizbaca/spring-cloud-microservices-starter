package com.easyrun.auth.oauth2.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.easyrun.commons.model.Auditable;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
@QueryEntity
public class Client extends Auditable<String> {
	private String name;
	private String clientId;
	private String secret;
	private List<String> roles;
	private String type;
}
