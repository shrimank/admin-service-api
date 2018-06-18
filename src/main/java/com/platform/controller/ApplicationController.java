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
import com.platform.domain.Application;
import com.platform.repository.ApplicationRepository;
import com.platform.response.ResponseObj;

@RestController
@RequestMapping("/api/v0.1")
@CrossOrigin(allowCredentials = "true")
public class ApplicationController {

	private static final String APPLICATION_JSON = "application/json";

	private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private ApplicationRepository applicationRepository;

	Gson gson = new Gson();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	/**
	 * 
	 * @param jsonApplication
	 * @return
	 */
	@RequestMapping(value = "application", method = RequestMethod.POST, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> create(@RequestBody String jsonApplication) {
		ResponseObj response = new ResponseObj(HttpStatus.CREATED);
		try {
			Application application = gson.fromJson(jsonApplication, Application.class);
			if (application != null) {
				Set<ConstraintViolation<Application>> violations = validator.validate(application);
				if (!violations.isEmpty()) {
					StringBuffer errorMsgs = new StringBuffer();
					for (ConstraintViolation<Application> violation : violations) {
						log.error(violation.getMessage());
						errorMsgs.append(violation.getMessage());
						response.setData(errorMsgs.toString());
						response.setStatus(HttpStatus.BAD_REQUEST);
					}
				} else {
					application = applicationRepository.save(application);
					response.setStatus(HttpStatus.CREATED);
					response.setData(application);
					response.setDescription("Application created successfully.");
				}
			} else {
				response.setStatus(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while creating application:{})", excep);
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
	 * @param jsonapplication
	 * @return
	 */
	@RequestMapping(value = "application/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> update(@PathParam("id") String _id, @RequestBody String jsonApplication) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<Application> application = applicationRepository.findById(_id);

			if (application.isPresent()) {
				Application updateAppObj = gson.fromJson(jsonApplication, Application.class);
				updateAppObj.set_id(application.get().get_id());
				updateAppObj = applicationRepository.save(updateAppObj);
				response.setStatus(HttpStatus.OK);
				response.setData(updateAppObj);
				response.setDescription("Application updated successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating application:{})", excep);
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
	@RequestMapping(value = "application/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getById(@PathParam("id") String _id) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<Application> application = applicationRepository.findById(_id);

			if (application.isPresent()) {
				response.setStatus(HttpStatus.OK);
				response.setData(application.get());
				response.setDescription("Application fetched successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating application:{})", excep);
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
	@RequestMapping(value = "application", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getAll(@RequestParam("limit") String limit) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Integer upperLimit = Integer.parseInt(limit);
			if (upperLimit > 0) {
				Integer lowerLimit = Integer.valueOf(String.valueOf(applicationRepository.count())) - upperLimit;
				List<Application> applications = applicationRepository
						.findAllWithLimit(new PageRequest(lowerLimit, upperLimit));
				if (applications != null && !applications.isEmpty()) {
					response.setStatus(HttpStatus.OK);
					response.setData(applications);
					response.setDescription("Application fetched successfully.");
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
				log.error("Exception while getAll Application with limit :{} :{})", limit, excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseObj>(response, response.getStatus());
	}

}
