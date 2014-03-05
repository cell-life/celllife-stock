package org.celllife.stockout.application.service.user;


import junit.framework.Assert;

import org.celllife.stockout.domain.exception.StockOutException;
import org.celllife.stockout.domain.user.UserDto;
import org.celllife.stockout.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:/META-INF/spring/spring-application.xml",
        "classpath:/META-INF/spring/spring-cache.xml",
        "classpath:/META-INF/spring/spring-config.xml",
        "classpath:/META-INF/spring/spring-domain.xml",
        "classpath:/META-INF/spring/spring-jdbc.xml",
        "classpath:/META-INF/spring/spring-orm.xml",
        "classpath:/META-INF/spring/spring-tx.xml",
        "classpath:/META-INF/spring-data/spring-data-jpa.xml"
})
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

	@Test(expected = StockOutException.class)
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
