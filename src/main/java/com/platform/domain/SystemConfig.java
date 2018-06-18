package com.platform.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class SystemConfig extends AbstractProperties {

	@Id
	private String _id;

	@Indexed(unique = true)
	private String code;

	private String value;

}
