package com.platform.domain;

import java.util.Date;

import lombok.Data;

@Data
public abstract class AbstractProperties {

	public Date createdDate;

	public Date lastUpdatedDate;

	public String createdBy;

	public String updatedBy;

	public String tenantId;

	public String entityCode;

	public Integer accessLevel;

	private Character enabledFlag = '1';

	private Character deletedFlag = '0';

}
