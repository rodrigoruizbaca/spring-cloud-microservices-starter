package com.easyrun.commons.service;

import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.easyrun.commons.Repository.EasyRepository;
import com.easyrun.commons.dto.EasyDto;
import com.easyrun.commons.exception.EntityNotFoundException;
import com.easyrun.commons.rest.filter.EasyCustomRqslVisitor;
import com.easyrun.commons.transformer.Transformer;
import com.querydsl.core.types.Predicate;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

public abstract class CrudSupportServiceImpl<	
	ENTITY, 
	DTO extends EasyDto<KEY>, 
	Q,
	ID,
	KEY,
	TRANSFORMER extends Transformer<DTO, ENTITY>, 
	REPOSITORY extends MongoRepository<ENTITY, ID> & QuerydslPredicateExecutor<ENTITY> & EasyRepository<ENTITY, KEY>>
	implements CrudSupportService<DTO, ID, Q> {
	
	
	public DTO add(DTO dto) {
		ENTITY entity = getRepository().getByUniqueKey(dto.geUniqueKey());
		if (entity == null) {
			entity = getTransformer().toDomain(dto);
			return getTransformer().toDto(getRepository().insert(entity));
		}
		throw new DuplicateKeyException("The key for entity" + dto.geUniqueKey() + " already exists");
	}
	
	public Page<DTO> get(Pageable pageable, Q q) {
		return get(pageable, q, null);
	}
	
	public Page<DTO> get(Pageable pageable, Q q, String search) {
		Page<ENTITY> entityPage = null;
		if (search != null) {
			Node rootNode = new RSQLParser().parse(search);
			EasyCustomRqslVisitor<Q> visitor = new EasyCustomRqslVisitor<Q>(q);
			Predicate p = rootNode.accept(visitor);
			entityPage = getRepository().findAll(p, pageable); 
		} else {
			entityPage = getRepository().findAll(pageable); 
		}
		
		Page<DTO> page = new PageImpl<>(getTransformer().toDtoLst(entityPage.getContent()), pageable, entityPage.getTotalElements());
		return page;				
	}
	
	public DTO update(DTO dto, ID id) throws EntityNotFoundException {
		Optional<ENTITY> optional = getRepository().findById(id);		
		if (optional.isPresent()) {
			ENTITY entity = optional.get();			
			return getTransformer().toDto(getRepository().save(entity));
		} else {
			throw new EntityNotFoundException("The entity with id " + id + " does not exists");
		}		
	}
	
	public void delete(ID id) throws EntityNotFoundException {
		Optional<ENTITY> optional = getRepository().findById(id);
		if (optional.isPresent()) {			
			getRepository().deleteById(id);
		} else {
			throw new EntityNotFoundException("The entity with id " + id + " does not exists");
		}		
	}
	
	public abstract REPOSITORY getRepository();
	public abstract TRANSFORMER getTransformer();
	
}
