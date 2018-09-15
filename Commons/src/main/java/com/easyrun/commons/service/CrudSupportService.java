package com.easyrun.commons.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.easyrun.commons.exception.EntityNotFoundException;

public interface CrudSupportService<DTO, ID, Q> {		
	DTO add(DTO dto);
	Page<DTO> get(Pageable pageable, Q q);
	Page<DTO> get(Pageable pageable, Q q, String search);
	DTO update(DTO dto, ID id) throws EntityNotFoundException;
	void delete(ID id) throws EntityNotFoundException;
}
