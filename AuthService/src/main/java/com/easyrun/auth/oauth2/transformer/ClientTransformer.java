package com.easyrun.auth.oauth2.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.easyrun.auth.oauth2.model.Client;
import com.easyrun.commons.dto.ClientDto;
import com.easyrun.commons.transformer.Transformer;
@Component
public class ClientTransformer implements Transformer<ClientDto, Client> {

	@Override
	public Client toDomain(ClientDto d) {
		Client domain = new Client();
		domain.setId(d.getId());
		BeanUtils.copyProperties(d, domain);
		return domain;
	}

	@Override
	public ClientDto toDto(Client d) {
		ClientDto dto = new ClientDto();
		dto.setId(d.getId());
		BeanUtils.copyProperties(d, dto);
		return dto;
	}

}
