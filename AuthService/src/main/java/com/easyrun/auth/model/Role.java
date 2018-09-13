package com.easyrun.auth.model;

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
public class Role extends Auditable<String> {
	private List<String> permissions;
	private String roleCd;
}
