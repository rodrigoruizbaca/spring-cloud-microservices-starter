package com.easyrun.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyrun.auth.model.QRole;
import com.easyrun.auth.model.Role;
import com.easyrun.auth.repository.RoleRepository;
import com.easyrun.auth.transformer.RoleTransformer;
import com.easyrun.commons.dto.RoleDto;
import com.easyrun.commons.service.CrudSupportServiceImpl;
@Service("roleService")
public class RoleService extends CrudSupportServiceImpl <
	Role, RoleDto, QRole, String, String, RoleTransformer, RoleRepository> {
	
	@Autowired
	private RoleTransformer roleTransfomer;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleRepository getRepository() {
		return roleRepository;
	}

	@Override
	public RoleTransformer getTransformer() {
		return roleTransfomer;
	}

}
