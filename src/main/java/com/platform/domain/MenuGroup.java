package com.platform.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class MenuGroup implements Comparable<MenuGroup> {

	@Id
	private String _id;

	private String title;

	@Indexed(unique = true)
	private String code;

	private String icon;

	private List<MenuItem> menuItems;

	private Integer order;

	@Override
	public int compareTo(MenuGroup mg) {
		return this.getOrder().compareTo(mg.getOrder());
	}

}
