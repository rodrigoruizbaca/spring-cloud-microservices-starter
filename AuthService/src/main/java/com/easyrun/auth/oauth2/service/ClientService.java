package com.easyrun.auth.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyrun.auth.oauth2.model.Client;
import com.easyrun.auth.oauth2.model.QClient;
import com.easyrun.auth.oauth2.repository.ClientRepository;
import com.easyrun.auth.oauth2.transformer.ClientTransformer;
import com.easyrun.commons.dto.ClientDto;
import com.easyrun.commons.service.CrudSupportServiceImpl;

@Service("clientService")
public class ClientService extends CrudSupportServiceImpl <
Client, ClientDto, QClient, String, String, ClientTransformer, ClientRepository> { 
	
	@Autowired
	private ClientTransformer clientTransformer;
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private OAuth2Service oauth2Service;

	@Override
	public ClientRepository getRepository() {
		return clientRepository;
	}

	@Override
	public ClientTransformer getTransformer() {
		return clientTransformer;
	}

	@Override
	protected Client beforeAdd(Client client) {
		client.setClientId(oauth2Service.generateClientId());
		client.setSecret(oauth2Service.generateClientSecret());
		return client;
	}
	
	@Override
	protected Client beforeUpdate(Client client, ClientDto dto) {
		client.setName(dto.getName());
		client.setRoles(dto.getRoles());
		client.setType(dto.getType());
		return client;
	}
	
}
