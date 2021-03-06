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
import com.platform.domain.Menu;
import com.platform.repository.MenuRepository;
import com.platform.response.ResponseObj;

@RestController
@RequestMapping("/api/v0.1")
@CrossOrigin(allowCredentials = "true")
public class MenuController {

	private static final String APPLICATION_JSON = "application/json";

	private static final Logger log = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuRepository menuRepository;

	Gson gson = new Gson();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	/**
	 * 
	 * @param jsonMenu
	 * @return
	 */
	@RequestMapping(value = "menu", method = RequestMethod.POST, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> create(@RequestBody String jsonMenu) {
		ResponseObj response = new ResponseObj(HttpStatus.CREATED);
		try {
			Menu menu = gson.fromJson(jsonMenu, Menu.class);
			if (menu != null) {
				Set<ConstraintViolation<Menu>> violations = validator.validate(menu);
				if (!violations.isEmpty()) {
					StringBuffer errorMsgs = new StringBuffer();
					for (ConstraintViolation<Menu> violation : violations) {
						log.error(violation.getMessage());
						errorMsgs.append(violation.getMessage());
						response.setData(errorMsgs.toString());
						response.setStatus(HttpStatus.BAD_REQUEST);
					}
				} else {
					menu = menuRepository.save(menu);
					response.setStatus(HttpStatus.CREATED);
					response.setData(menu);
					response.setDescription("Menu created successfully.");
				}
			} else {
				response.setStatus(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while creating menu:{})", excep);
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
	 * @param jsonMenu
	 * @return
	 */
	@RequestMapping(value = "menu/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> update(@PathParam("id") String _id, @RequestBody String jsonMenu) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<Menu> menu = menuRepository.findById(_id);

			if (menu.isPresent()) {
				Menu updateMenuObj = gson.fromJson(jsonMenu, Menu.class);
				updateMenuObj.set_id(menu.get().get_id());
				updateMenuObj = menuRepository.save(updateMenuObj);
				response.setStatus(HttpStatus.OK);
				response.setData(updateMenuObj);
				response.setDescription("Menu updated successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating menu:{})", excep);
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
	@RequestMapping(value = "menu/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getById(@PathParam("id") String _id) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Optional<Menu> menu = menuRepository.findById(_id);

			if (menu.isPresent()) {
				response.setStatus(HttpStatus.OK);
				response.setData(menu.get());
				response.setDescription("Menu fetched successfully.");
			} else {
				response.setStatus(HttpStatus.NOT_FOUND);
			}

		} catch (Exception excep) {
			if (log.isErrorEnabled()) {
				log.error("Exception while updating menu:{})", excep);
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
	@RequestMapping(value = "menu", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public ResponseEntity<ResponseObj> getAll(@RequestParam("limit") String limit) {
		ResponseObj response = new ResponseObj(HttpStatus.OK);
		try {
			Integer upperLimit = Integer.parseInt(limit);
			if (upperLimit > 0) {
				Integer lowerLimit = Integer.valueOf(String.valueOf(menuRepository.count())) - upperLimit;
				List<Menu> menus = menuRepository.findAllWithLimit(new PageRequest(lowerLimit, upperLimit));
				if (menus != null && !menus.isEmpty()) {
					response.setStatus(HttpStatus.OK);
					response.setData(menus);
					response.setDescription("Menu fetched successfully.");
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
				log.error("Exception while getAll Menu with limit :{} :{})", limit, excep);
			}
			response.setData(excep.getMessage());
			response.setDescription(excep.getLocalizedMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseObj>(response, response.getStatus());
	}

}
