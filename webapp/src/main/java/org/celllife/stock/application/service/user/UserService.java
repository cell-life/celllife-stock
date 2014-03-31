package org.celllife.stock.application.service.user;

import org.celllife.stock.domain.user.UserDto;

public interface UserService {

	/**
	 * Creates a user on the system. A password salt will be generated for the user, and 
	 * their password encrypted for storage. 
	 * @param user User to create
	 * @return saved User (with Id specified)
	 */
	UserDto createUser(UserDto user);
	
	/**
	 * Retrieves the user given the msisdn (cellphone number).
	 * @param msisdn String
	 * @return User, null if none found
	 */
	UserDto getUser(String msisdn);
}