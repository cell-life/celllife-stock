package org.celllife.stockout.application.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.celllife.stockout.application.service.user.SecurityServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class EncryptedPasswordEncoder implements PasswordEncoder {
	
	private static Logger log = LoggerFactory.getLogger(EncryptedPasswordEncoder.class);

	@Override
	public String encodePassword(String password, Object salt) {
		try {
			String encodedPassword = SecurityServiceUtils.encodeString(password+salt);
			return encodedPassword;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			log.warn("Could  not encode the password. Error:"+e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean isPasswordValid(String encryptedPassword, String password, Object salt) {
		try {
			String hashedPassword = SecurityServiceUtils.encodeString(password + salt);
			if (hashedPassword.equals(encryptedPassword)) {
				return true;
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			log.warn("Could not encode the password. It will be marked as invalid. Error:"+e.getMessage(), e);
		}
		return false;
	}

}
