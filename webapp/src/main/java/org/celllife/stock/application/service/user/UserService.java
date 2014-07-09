package org.celllife.stock.application.service.user;

import org.celllife.stock.domain.user.ClinicDto;
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
	 * Updates the user details on the system. If the clearText password is set, then 
	 * the users password will be updated.
	 * @param user User to update
	 * @return updated User
	 */
	UserDto updateUser(UserDto user);
	
	/**
	 * Retrieves the user given the msisdn (cellphone number).
	 * @param msisdn String
	 * @return User, null if none found
	 */
	UserDto getUser(String msisdn);

	/**
	 * Activates the clinic and sets the leadTime and the safetyLevel
	 * @param clinic ClinicDto containing the values entered during the phone setup
	 */
	void activateClinic(ClinicDto clinic);
}
