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
import com.platform.domain.User;
import com.platform.repository.UserRepository;
import com.platform.response.ResponseObj;

@RestController
@RequestMapping("/api/v0.1")
@CrossOrigin(allowCredentials = "true")
public class UserController {

	private static final String APPLICATION_JSON = "application/json";

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	Gson gson = new Gson();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	/**
	 * 
	 * @param jsonUser
	 * @return
	 */
	@RequestMapping(value = "user", method = RequestMethod.POST, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> create(@RequestBody String jsonUser) {
		ResponseObj response = new ResponseObj(HttpStatus.CREATED);
		try {
			User user = gson.fromJson(jsonUser, User.class);
			if (user != null) {
				Set<ConstraintViolation<User>> violations = validator.validate(user);
				if (!violations.isEmpty()) {
					StringBuffer errorMsgs = new StringBuffer();
					for (ConstraintViolation<User> violation : violations) {
						log.error(violation.getMessage());
						errorMsgs.append(violation.getMessage());
						response.setData(errorMsgs.toString());
						response.setStatus(HttpStatus.BAD_REQUEST);
					}
				} else {
					user = userRepository.save(user);
					response.setStatus(HttpStatus.CREATED);
					response.setData(user);
					response.setDescription("User created successfully.");
				}
			} else {
				response.setStatus(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while creating user:{})", excep);
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
	 * @param jsonUser
	 * @return
	 */
	@RequestMapping(value = "user/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> update(@PathParam("id") String _id, @RequestBody String jsonUser) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<User> user = userRepository.findById(_id);

			if (user.isPresent()) {
				User updateUserObj = gson.fromJson(jsonUser, User.class);
				updateUserObj.set_id(user.get().get_id());
				updateUserObj = userRepository.save(updateUserObj);
				response.setStatus(HttpStatus.OK);
				response.setData(updateUserObj);
				response.setDescription("User updated successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating user:{})", excep);
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
	@RequestMapping(value = "user/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getById(@PathParam("id") String _id) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<User> user = userRepository.findById(_id);

			if (user.isPresent()) {
				response.setStatus(HttpStatus.OK);
				response.setData(user.get());
				response.setDescription("User fetched successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating user:{})", excep);
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
	@RequestMapping(value = "user", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getAll(@RequestParam("limit") String limit) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Integer upperLimit = Integer.parseInt(limit);
			if (upperLimit > 0) {
				Integer lowerLimit = Integer.valueOf(String.valueOf(userRepository.count())) - upperLimit;
				List<User> users = userRepository.findAllWithLimit(new PageRequest(lowerLimit, upperLimit));
				if (users != null && !users.isEmpty()) {
					response.setStatus(HttpStatus.OK);
					response.setData(users);
					response.setDescription("User fetched successfully.");
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
				log.error("Exception while getAll User with limit :{} :{})", limit, excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseObj>(response, response.getStatus());
	}

}
