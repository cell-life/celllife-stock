package org.celllife.stock.application.service.user;

/**
 * Allows for alernative authentication methods (e.g. from the mobile phone)
 */
public interface AuthenticationService {

	boolean authenticate(String msisdn, String encryptedPassword);
}
