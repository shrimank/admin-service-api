package com.platform.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Menu extends AbstractProperties {

	@Id
	private String _id;

	private List<MenuGroup> menuGroups;
}
