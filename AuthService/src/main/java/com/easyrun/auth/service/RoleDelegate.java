package com.easyrun.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.easyrun.auth.model.QRole;
import com.easyrun.commons.dto.RoleDto;
import com.easyrun.commons.exception.EntityNotFoundException;
import com.easyrun.commons.service.CrudSupportService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RoleDelegate {
	@Autowired
	@Qualifier("roleService")
	private CrudSupportService<RoleDto, String, QRole> service; 
	
	@Autowired
	private RoleServiceCache cache; 
	
	@HystrixCommand(fallbackMethod = "addCache")
	public RoleDto add(RoleDto dto) {
		return service.add(dto);
	}
	
	@HystrixCommand(fallbackMethod = "updateCache")
	public RoleDto update(RoleDto dto, String id) throws EntityNotFoundException {
		return service.update(dto, id);
	}
	
	@HystrixCommand(fallbackMethod = "getCache")
	public Page<RoleDto> get(Pageable pageable, QRole q) {
		return service.get(pageable, q);
	}
	
	@HystrixCommand(fallbackMethod = "getCache")
	public Page<RoleDto> get(Pageable pageable, QRole q, String search) {
		return service.get(pageable, q, search);
	}
			
	@HystrixCommand(fallbackMethod = "deleteCache")
	public void delete(String id) throws EntityNotFoundException {
		service.delete(id);
	}
	
	@SuppressWarnings("unused")
	private RoleDto addCache(RoleDto dto) {
		return cache.add(dto);
	}
		
	@SuppressWarnings("unused")
	private RoleDto updateCache(RoleDto dto, String id) throws EntityNotFoundException {
		return cache.update(dto, id);
	}
	
	@SuppressWarnings("unused")
	private Page<RoleDto> getCache(Pageable pageable, QRole q) {
		return cache.get(pageable, q);
	}
	
	@SuppressWarnings("unused")
	public void deleteCache(String id) throws EntityNotFoundException {
		cache.delete(id);
	}
	
}
