package com.easyrun.commons.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(exclude={"publicKey", "headerKeyId"})
@Getter
@Setter
public class ConfigurationDto {
	private String id;
	private String publicKey;
	private String headerKeyId;
	private String name;
}
