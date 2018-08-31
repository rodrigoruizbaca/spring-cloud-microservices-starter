package com.easyrun.commons.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(exclude={"publicKey", "headerKeyId"})
@Getter
@Setter
@Document
public class Configuration {
	@Id
	private String id;
	
	private String publicKey;
	
	private String headerKeyId;
	
	private String name;

}
