package com.platform.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Lookup extends AbstractProperties {

	@Id
	private String _id;

	private String code;

	private String value;

	private String attribute_1;
	private String attribute_2;
	private String attribute_3;
	private String attribute_4;
	private String attribute_5;
	private String attribute_6;
	private String attribute_7;
	private String attribute_8;
	private String attribute_9;
	private String attribute_10;

}
