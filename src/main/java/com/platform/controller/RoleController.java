package com.platform.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.platform.domain.Role;
import com.platform.repository.RoleRepository;
import com.platform.response.ResponseObj;

@RestController
@RequestMapping("/api/v0.1")
@CrossOrigin(allowCredentials = "true")
public class RoleController {

	private static final String APPLICATION_JSON = "application/json";

	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleRepository roleRepository;

	Gson gson = new Gson();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	/**
	 * 
	 * @param jsonRole
	 * @return
	 */
	@RequestMapping(value = "role", method = RequestMethod.POST, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> create(@RequestBody String jsonRole) {
		ResponseObj response = new ResponseObj(HttpStatus.CREATED);
		try {
			Role role = gson.fromJson(jsonRole, Role.class);
			if (role != null) {
				Set<ConstraintViolation<Role>> violations = validator.validate(role);
				if (!violations.isEmpty()) {
					StringBuffer errorMsgs = new StringBuffer();
					for (ConstraintViolation<Role> violation : violations) {
						log.error(violation.getMessage());
						errorMsgs.append(violation.getMessage());
						response.setData(errorMsgs.toString());
						response.setStatus(HttpStatus.BAD_REQUEST);
					}
				} else {
					role = roleRepository.save(role);
					response.setStatus(HttpStatus.CREATED);
					response.setData(role);
					response.setDescription("Role created successfully.");
				}
			} else {
				response.setStatus(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while creating role:{})", excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseObj>(response, response.getStatus());
	}

	/**
	 * 
	 * @param _id
	 * @param jsonRole
	 * @return
	 */
	@RequestMapping(value = "role/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> update(@PathParam("id") String _id, @RequestBody String jsonRole) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<Role> role = roleRepository.findById(_id);

			if (role.isPresent()) {
				Role updateRoleObj = gson.fromJson(jsonRole, Role.class);
				updateRoleObj.set_id(role.get().get_id());
				updateRoleObj = roleRepository.save(updateRoleObj);
				response.setStatus(HttpStatus.OK);
				response.setData(updateRoleObj);
				response.setDescription("Role updated successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating role:{})", excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseObj>(response, response.getStatus());
	}

	/**
	 * 
	 * @param _id
	 * @return
	 */
	@RequestMapping(value = "role/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getById(@PathParam("id") String _id) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<Role> role = roleRepository.findById(_id);

			if (role.isPresent()) {
				response.setStatus(HttpStatus.OK);
				response.setData(role.get());
				response.setDescription("Role fetched successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating role:{})", excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseObj>(response, response.getStatus());
	}

	/**
	 * 
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "role", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getAll(@RequestParam("limit") String limit) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Integer upperLimit = Integer.parseInt(limit);
			if (upperLimit > 0) {
				Integer lowerLimit = Integer.valueOf(String.valueOf(roleRepository.count())) - upperLimit;
				List<Role> roles = roleRepository.findAllWithLimit(new PageRequest(lowerLimit, upperLimit));
				if (roles != null && !roles.isEmpty()) {
					response.setStatus(HttpStatus.OK);
					response.setData(roles);
					response.setDescription("Role fetched successfully.");
				} else {
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (NumberFormatException excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while parsing limit:{},{})", limit, excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while getAll Role with limit :{} :{})", limit, excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseObj>(response, response.getStatus());
	}

}
