package org.celllife.stock.application.service.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AuthenticationServiceImpl implements AuthenticationService {
	
	private static Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean authenticate(String msisdn, String encryptedPassword) {
		User user = null;
		if (msisdn != null && !msisdn.trim().equals("")) {
			user = userRepository.findOneByMsisdn(msisdn);
			if (user != null) {
				if (isValidUserPassword(user, encryptedPassword)) {
					return true;
					//String sessionIdBefeoreNewContext = getCurrentSession();
					//setSecurityContext(userDetails, sessionIdBefeoreNewContext);
				}
			}
		}

		return false;
	}

	private Boolean isValidUserPassword(User user, String password) {
		String encryptedPassword = user.getEncryptedPassword();
		String salt = user.getSalt();

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