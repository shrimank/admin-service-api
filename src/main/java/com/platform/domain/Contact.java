package com.platform.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Contact extends AbstractProperties {
	
	@Id
	private String _id;

	private String firstName;

	private String lastName;

	private String middleName;

	private String email;

	private String altEmail;

	private String mobileNo;

	private String altMobileNo;

	private String faxNumber;
	
	private Address address;

}
