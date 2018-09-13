package com.easyrun.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilterDto {
	private String name;
	private String oper;
	private String value;
}
