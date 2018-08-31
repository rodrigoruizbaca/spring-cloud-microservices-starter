package com.easyrun.commons.transformer;

import java.util.List;
import java.util.stream.Collectors;

public interface Transformer<DTO, DOMAIN> {
	public DTO toDto(DOMAIN d);
	public DOMAIN toDomain(DTO d);
	
	public default List<DTO> toDtoLst(List<DOMAIN> lst) {
		return lst.stream().map(d -> toDto(d)).collect(Collectors.toList());
	}
	
	public default List<DOMAIN> toDomainLst(List<DTO> lst) {
		return lst.stream().map(d -> toDomain(d)).collect(Collectors.toList());
	}
}
