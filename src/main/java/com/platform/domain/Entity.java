package com.platform.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
@CompoundIndexes({
		@CompoundIndex(name = "TENANT_ENTITY_IDX", unique = true, def = "{'entityCode' : 1, 'tenantId' : 1}") })
public class Entity extends AbstractProperties {

	@Id
	private String _id;

	private String entityCode;

	private String entityName;

	private Entity parentEntity;

	private Address address;

}
