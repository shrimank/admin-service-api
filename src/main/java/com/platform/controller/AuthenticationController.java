package com.platform.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.constants.IConstants;
import com.platform.domain.User;
import com.platform.enums.ActivationStatus;
import com.platform.enums.ProcessingStatus;
import com.platform.repository.UserRepository;
import com.platform.response.ResponseObj;

@RequestMapping("/api/v0.1")
@RestController
@CrossOrigin(allowCredentials = "true")
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param userName
	 * @param userPassword
	 * @param app
	 * @param tenantId
	 * @return
	 */
	@RequestMapping(value = "authenticate", method = RequestMethod.GET, produces = IConstants.APPLICATION_JSON)
	public ResponseEntity<ResponseObj> autheticate(@RequestParam("userName") String userName,
			@RequestParam("userPassword") String userPassword, @RequestParam("app") String app,
			@RequestParam("tenantId") String tenantId) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<User> user = userRepository
					.findByUserNameAndUserPasswordAndApplicationNameAndTenantIdAndEnabledFlagAndActivationStatusAndProcessingStatus(
							userName, userPassword, app, tenantId, '1', ActivationStatus.ACTIVE,
							ProcessingStatus.AUTHORIZED);

			if (user.isPresent()) {
				response.setStatus(HttpStatus.OK);
				response.setData(user.get());
				response.setDescription("User autheticated successfully.");
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

}
