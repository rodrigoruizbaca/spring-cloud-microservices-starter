package com.easyrun.auth.oauth2.controller;

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

import com.easyrun.auth.oauth2.model.QClient;
import com.easyrun.commons.dto.ClientDto;
import com.easyrun.commons.dto.ExistingValidator;
import com.easyrun.commons.dto.NewValidator;
import com.easyrun.commons.exception.EntityNotFoundException;
import com.easyrun.commons.service.CrudSupportService;

@RestController()
@CrossOrigin
@RequestMapping(value="oauth2/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {
	
	@Autowired
	@Qualifier("clientService")
	private CrudSupportService<ClientDto, String, QClient> service; 
	
	
	
	@PostMapping()	
	@PreAuthorize("@S.hasAuthorityAsPattern('oauth2-add-client')")
	public  ResponseEntity<?> add(@Validated(NewValidator.class) @RequestBody ClientDto client) {		
		return ResponseEntity.created(URI.create("/oauth2/client")).body(service.add(client));
	}	
	
	@PatchMapping("/{id}")	
	@PreAuthorize("@S.hasAuthorityAsPattern('oauth2-update-client')")
	public ResponseEntity<?> update(@Validated(ExistingValidator.class) @RequestBody ClientDto client, @PathVariable String id) {
		try {
			return ResponseEntity.ok(service.update(client, id));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("")	
	@PreAuthorize("@S.hasAuthorityAsPattern('oauth2-get-client')")
	public ResponseEntity<?> get(Pageable p, @RequestParam(value = "search", required=false) String search) {		
		if (search != null && !search.isEmpty()) {
			return ResponseEntity.ok(service.get(p, QClient.client, search));
		} else {
			return ResponseEntity.ok(service.get(p, QClient.client));
		}
	}
	
	@DeleteMapping("/{id}")	
	@PreAuthorize("@S.hasAuthorityAsPattern('oauth2-delete-client')")
	public ResponseEntity<?> delete(@PathVariable String id) {
		try {
			service.delete(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
