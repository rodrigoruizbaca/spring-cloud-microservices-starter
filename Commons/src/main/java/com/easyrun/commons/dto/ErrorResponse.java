package com.easyrun.commons.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	private String message;
}
