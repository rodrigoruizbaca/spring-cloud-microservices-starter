package com.easyrun.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.easyrun.auth.model.QRole;
import com.easyrun.commons.dto.RoleDto;
import com.easyrun.commons.exception.EntityNotFoundException;
import com.easyrun.commons.service.CrudSupportService;

/**
 * Simple cache implemented as an example. A better implementation using redis or other kind of cache may be used.
 * @author rodrigo ruiz
 *
 */
@Service("roleServiceCache")
public class RoleServiceCache implements CrudSupportService<RoleDto, String, QRole> {
	
	//Uses a concurrent HashMap inside so seems fine for this.
	Map<String, RoleDto> cache = new ConcurrentHashMap<>(100);
	
	@Override
	public RoleDto add(RoleDto dto) {
		boolean exists = cache.values().stream().filter(role -> role.getRoleCd().equalsIgnoreCase(dto.getRoleCd())).count() > 1 ;
		if (exists) {
			throw new DuplicateKeyException("The key for entity " + dto.geUniqueKey() + " already exists");
		}		
		UUID uuid = UUID.randomUUID();
		dto.setId(uuid.toString());
		cache.put(dto.getId(), dto);
		return dto;
	}

	@Override
	public Page<RoleDto> get(Pageable pageable, QRole q) {
		List<RoleDto> list = new ArrayList<>(cache.values());
		PageImpl<RoleDto> page = new PageImpl<>(list, pageable, list.size());
		return page;
	}

	@Override
	public Page<RoleDto> get(Pageable pageable, QRole q, String search) {		
		return get(pageable, q);
	}

	@Override
	public RoleDto update(RoleDto dto, String id) throws EntityNotFoundException {		
		cache.put(id, dto);
		return dto;
	}

	@Override
	public void delete(String id) throws EntityNotFoundException {
		cache.remove(id);		
	}



}
