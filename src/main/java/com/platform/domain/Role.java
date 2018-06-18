package com.platform.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
@CompoundIndexes({ @CompoundIndex(name = "ROLE_TENANT_ROLE_IDX", unique = true, def = "{'name' : 1, 'tenantId' : 1}") })
public class Role extends AbstractProperties {

	@Id
	private String _id;

	@Indexed(unique = true)
	private String name;

	private List<Menu> menus;

}
