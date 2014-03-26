package org.celllife.stockout.application.service.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.celllife.stockout.domain.exception.StockOutException;
import org.celllife.stockout.domain.user.User;
import org.celllife.stockout.domain.user.UserDto;
import org.celllife.stockout.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto user) {
		User msisdnUser = userRepository.findOneByMsisdn(user.getMsisdn());
		if (msisdnUser != null) {
			throw new StockOutException("User with msisdn '"+user.getMsisdn()+"' already exists.");
		}
		
		User newUser = convertUser(user);

		// set the user's encrypted password
		try {
			newUser.setSalt(SecurityServiceUtils.getRandomToken());
			newUser.setEncryptedPassword(SecurityServiceUtils.encodeString(user.getPassword()+newUser.getSalt()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// this should not happen during runtime
			throw new StockOutException("Could not encrypt the user's password.", e);
		}

		User savedUser = userRepository.save(newUser);
		log.info("created user "+savedUser);

		return new UserDto(savedUser);
	}

	@Override
	public UserDto getUser(String msisdn) {
		User user = userRepository.findOneByMsisdn(msisdn);
		if (user != null) {
			return new UserDto(user);
		} else {
			return null;
		}
	}

	private User convertUser(UserDto user) {
		User newUser = new User(user.getMsisdn(), user.getEncryptedPassword(), user.getSalt(), 
				user.getClinicCode(), user.getClinicName());
		return newUser;
	}
}
