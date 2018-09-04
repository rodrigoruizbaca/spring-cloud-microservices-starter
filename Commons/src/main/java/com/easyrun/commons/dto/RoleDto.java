package com.easyrun.commons.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class RoleDto {
	private String id;
	private String roleCd;
	private List<String> permissions;
}
