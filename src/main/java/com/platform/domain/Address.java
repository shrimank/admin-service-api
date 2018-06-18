package com.platform.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Address {
	
	@Id
	private String _id;

	private String address_1;

	private String address_2;

	private String city;

	private String state;

	private String zipCode;

}
