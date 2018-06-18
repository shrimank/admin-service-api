package com.platform.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.platform.enums.MenuItemType;

import lombok.Data;

@Data
@Document
public class MenuItem implements Comparable<MenuItem> {

	@Id
	private String _id;

	private String title;

	private String code;

	private String icon;

	private MenuItemType type;

	private Integer order;

	@Override
	public int compareTo(MenuItem menuItem) {
		return this.getOrder().compareTo(menuItem.getOrder());
	}

}
