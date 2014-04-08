package org.celllife.stock.application.service.user;


import junit.framework.Assert;

import org.celllife.stock.application.service.user.UserService;
import org.celllife.stock.domain.exception.StockException;
import org.celllife.stock.domain.user.UserDto;
import org.celllife.stock.domain.user.UserRepository;
import org.celllife.stock.test.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testCreateUser() throws Exception {
		Long userId = null;
		try {
			UserDto user = new UserDto("0118198075", "1234", "0000", "Demo Clinic 1");
			UserDto savedUser = userService.createUser(user);
			userId = savedUser.getId();
			Assert.assertNotNull(savedUser);
			Assert.assertEquals(user.getMsisdn(), savedUser.getMsisdn());
			Assert.assertNotNull(savedUser.getId());
			Assert.assertNotNull(savedUser.getEncryptedPassword());
			Assert.assertNotNull(savedUser.getSalt());
		} finally {
			if (userId != null) userRepository.delete(userId);
		}
	}

	@Test(expected = StockException.class)
	public void testCreateDuplicateUser() throws Exception {
		Long userId = null;
		try {
			UserDto user = new UserDto("0118198075", "1234", "0000", "Demo Clinic 1");
			UserDto savedUser = userService.createUser(user);
			userId = savedUser.getId();
			
			// this should fail
			userService.createUser(user);
		} finally {
			if (userId != null) userRepository.delete(userId);
		}
	}
}
