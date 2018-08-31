package com.easyrun.commons.dto;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(exclude={"publicKey", "headerKeyId"})
@Getter
@Setter
public class ConfigurationDto {
	private int id;
	private Map<String, Object> publicKey;
	private String headerKeyId;
	private String name;
}
