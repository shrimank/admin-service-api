package com.platform.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * 
 * @author shriman-dev
 *
 */
@Data
public class ResponseObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4305295492382862947L;

	private HttpStatus status;

	private Object data;

	private String description;

	public ResponseObj(HttpStatus status) {
		this.status = status;
	}

}
