package com.easyrun.commons.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public abstract class Auditable<ID> implements Persistable<ID> {
	
	@CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
    @Id
    private ID id;
    
    @Override
	public boolean isNew() {
		return id == null;
	}
	
    @CreatedBy    
    private String createdBy;
    
    @LastModifiedBy
    private String lastModifiedBy;
}
