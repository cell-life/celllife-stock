package org.celllife.stockout.interfaces.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stockout.application.service.user.UserService;
import org.celllife.stockout.domain.exception.StockOutException;
import org.celllife.stockout.domain.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service/users")
public class UserController {

	@Autowired
	UserService userService;

	@Value("${external.base.url}") 
	String baseUrl;

	@ResponseBody
	@RequestMapping(
		method = RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUser(@RequestParam("msisdn") String msisdn, HttpServletResponse response) throws IOException {
		try {
			UserDto user = userService.getUser(msisdn);
	        if (user == null) {
	        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        	return null;
	        }
			return user;
		} catch (StockOutException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public void saveUser(@RequestBody UserDto user, HttpServletResponse response) throws IOException {
		try {
			// FIXME: lookup the clinic name if clinicName is null (using the clinic service)
			UserDto newUser = userService.createUser(user);
			response.setHeader("Location", baseUrl+"/service/users?msisdn="+newUser.getMsisdn());
	        response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (StockOutException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
