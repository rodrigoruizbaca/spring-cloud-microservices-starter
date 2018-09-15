package com.easyrun.auth.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easyrun.auth.model.QRole;
import com.easyrun.commons.dto.ExistingValidator;
import com.easyrun.commons.dto.NewValidator;
import com.easyrun.commons.dto.RoleDto;
import com.easyrun.commons.exception.EntityNotFoundException;
import com.easyrun.commons.service.CrudSupportService;

@RestController()
@CrossOrigin
@RequestMapping(value="role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
	
	
	@Autowired
	@Qualifier("roleService")
	private CrudSupportService<RoleDto, String, QRole> service; 
	
	@PostMapping()	
	@PreAuthorize("@S.hasAuthorityAsPattern('add-role')")
	public  ResponseEntity<?> addrole(@Validated(NewValidator.class) @RequestBody RoleDto role) {
		return ResponseEntity.created(URI.create("/role")).body(service.add(role));
	}	
	
	@PatchMapping("/{id}")	
	@PreAuthorize("@S.hasAuthorityAsPattern('update-role')")
	public ResponseEntity<?> updateRole(@Validated(ExistingValidator.class) @RequestBody RoleDto role, @PathVariable String id) {
		try {
			return ResponseEntity.ok(service.update(role, id));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("")	
	@PreAuthorize("@S.hasAuthorityAsPattern('get-role')")
	public ResponseEntity<?> getRole(Pageable p, @RequestParam(value = "search", required=false) String search) {
		
		if (search != null && !search.isEmpty()) {
			return ResponseEntity.ok(service.get(p, QRole.role, search));
		} else {
			return ResponseEntity.ok(service.get(p, QRole.role));
		}
	}
	
	@DeleteMapping("/{id}")	
	@PreAuthorize("@S.hasAuthorityAsPattern('delete-role')")
	public ResponseEntity<?> deleteRole(@PathVariable String id) {
		try {
			service.delete(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}

