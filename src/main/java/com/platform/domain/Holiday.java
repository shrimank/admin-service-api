package com.platform.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Holiday extends AbstractProperties {

	@Id
	private String _id;

	private Date holidayDate;
}
