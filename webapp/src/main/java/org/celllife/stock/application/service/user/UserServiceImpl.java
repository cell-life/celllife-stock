package org.celllife.stock.application.service.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.celllife.stock.domain.exception.StockException;
import org.celllife.stock.domain.user.ClinicDto;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserDto;
import org.celllife.stock.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
	
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDto createUser(UserDto user) {
		User msisdnUser = userRepository.findOneByMsisdn(user.getMsisdn());
		if (msisdnUser != null) {
			throw new StockException("User with msisdn '"+user.getMsisdn()+"' already exists.");
		}
		
		User newUser = user.toUser();

		// set the user's encrypted password
	    if (user.getPassword() == null || user.getPassword().trim().equals("")) {
	        throw new StockException("User must specify a password.");
	    }
		try {
			newUser.setSalt(SecurityServiceUtils.getRandomToken());
			newUser.setEncryptedPassword(SecurityServiceUtils.encodeString(user.getPassword()+newUser.getSalt()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// this should not happen during runtime
			throw new StockException("Could not encrypt the user's password.", e);
		}

		User savedUser = userRepository.save(newUser);
		log.info("created user "+savedUser);

		return new UserDto(savedUser);
	}
	
    @Override
    public UserDto updateUser(UserDto user) {
        User msisdnUser = userRepository.findOneByMsisdn(user.getMsisdn());
        if (msisdnUser == null) {
            throw new StockException("User with msisdn '"+user.getMsisdn()+"' does not exist.");
        }
        // deal with password resets before merging
        if (!StringUtils.isEmpty(user.getPassword())) {
            if (StringUtils.isEmpty(user.getOldPassword())) {
                throw new StockException("In order to reset the password for the user with msisdn '" + user.getMsisdn()
                        + "' please set the oldPassword.");
            }
            // valid oldPassword
            try {
                String encryptedOldPassword = SecurityServiceUtils.encodeString(user.getOldPassword()+msisdnUser.getSalt());
                if (msisdnUser.getEncryptedPassword().equals(encryptedOldPassword)) {
                    // oldPassword has been validated
                    msisdnUser.setEncryptedPassword(SecurityServiceUtils.encodeString(user.getPassword()+msisdnUser.getSalt()));
                } else {
                    throw new StockException("Cannot update user or reset password for user with msisdn '"
                            + user.getMsisdn() + "' - oldPassword is not valid. Please try again.");
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                // this should not happen during runtime
                throw new StockException("Could not encrypt the user's password.", e);
            }
        }
        msisdnUser.merge(user.toUser());
        
        User savedUser = userRepository.save(msisdnUser);
        log.info("updated user "+savedUser);
        
        return new UserDto(savedUser);
    }

	@Override
	@Transactional(readOnly = true)
	public UserDto getUser(String msisdn) {
		User user = userRepository.findOneByMsisdn(msisdn);
		if (user != null) {
			return new UserDto(user);
		} else {
			return null;
		}
	}

    @Override
    public void activateClinic(ClinicDto clinic) {
        User user = userRepository.findOneByMsisdn(clinic.getMsisdn());
        if (user == null) {
            throw new StockException("User with msisdn '"+clinic.getMsisdn()+"' does not exist. Please create the user before trying to activate the clinic.");
        }
        user.setLeadTime(clinic.getLeadTime());
        user.setSafetyLevel(clinic.getSafetyLevel());
        user.setActivated(Boolean.FALSE);
        
        User savedUser = userRepository.save(user);
        log.info("activated clinic "+savedUser);
    }
}
