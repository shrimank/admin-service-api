package com.platform.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Document
@JsonIgnoreProperties("userPassword")
@CompoundIndexes({
		@CompoundIndex(name = "USR_TENANT_USER_IDX", unique = true, def = "{'userName' : 1, 'tenantId' : 1}") })
public class User extends AbstractProperties {

	@Id
	private String _id;

	private Application application;

	@Indexed(unique = true)
	private String userName;

	private String userPassword;

	private Entity userEntity;

	private Contact contact;

	private Character forcePassword = '0';

	private Character passwodChanged = '0';

	private Date pwdExpiryDate;

}
