package org.celllife.stock.application.service.user;


import junit.framework.Assert;

import org.celllife.stock.domain.exception.StockException;
import org.celllife.stock.domain.user.ClinicDto;
import org.celllife.stock.domain.user.User;
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
			Assert.assertEquals(Boolean.FALSE, savedUser.getActivated());
		} finally {
			if (userId != null) userRepository.delete(userId);
		}
	}
	
    @Test(expected = StockException.class)
    public void testCreateUserEmptyPassword() throws Exception {
        Long userId = null;
        try {
            UserDto user = new UserDto("0118198075", " ", "0000", "Demo Clinic 1");
            UserDto savedUser = userService.createUser(user);
            userId = savedUser.getId();
        } finally {
            if (userId != null) userRepository.delete(userId);
        }
    }
	
	@Test
	public void testUpdateUser() throws Exception {
        Long userId = null;
        try {
            UserDto user = new UserDto("0118198075", "1234", "0000", "Demo Clinic 1");
            UserDto savedUser = userService.createUser(user);
            userId = savedUser.getId();

            savedUser.setClinicCode("0001");
            UserDto updatedUser = userService.updateUser(savedUser);
            Assert.assertEquals("0001", updatedUser.getClinicCode());

        } finally {
            if (userId != null) userRepository.delete(userId);
        }
	}
	
	@Test
    public void testUpdateUserWithPasswordReset() throws Exception {
        Long userId = null;
        try {
            UserDto user = new UserDto("0118198075", "1234", "0000", "Demo Clinic 1");
            UserDto savedUser = userService.createUser(user);
            userId = savedUser.getId();

            savedUser.setPassword("4321");
            savedUser.setOldPassword("1234");
            UserDto updatedUser = userService.updateUser(savedUser);

            Assert.assertEquals("0000", updatedUser.getClinicCode());

        } finally {
            if (userId != null) userRepository.delete(userId);
        }
    }

    @Test(expected = StockException.class)
    public void testUpdateUserWithPasswordResetNoOldPassword() throws Exception {
        Long userId = null;
        try {
            UserDto user = new UserDto("0118198075", "1234", "0000", "Demo Clinic 1");
            UserDto savedUser = userService.createUser(user);
            userId = savedUser.getId();

            savedUser.setPassword("4321");
            userService.updateUser(savedUser);

        } finally {
            if (userId != null) userRepository.delete(userId);
        }
    }	
	
	@Test(expected = StockException.class)
    public void testUpdateUserWithPasswordResetInvalidOldPassword() throws Exception {
        Long userId = null;
        try {
            UserDto user = new UserDto("0118198075", "1234", "0000", "Demo Clinic 1");
            UserDto savedUser = userService.createUser(user);
            userId = savedUser.getId();

            savedUser.setPassword("4321");
            savedUser.setOldPassword("1235");
            userService.updateUser(savedUser);

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

	@Test(expected = StockException.class)
	public void testActivateClinicWithNoUser() throws Exception {
	    ClinicDto clinic = new ClinicDto("7342878239473294", "0000", "Demo Clinic 1", "[123,123]");
	    userService.activateClinic(clinic);
	}

	@Test
    public void testActivateClinic() throws Exception {
	    String msisdn = "0118198075";
        Long userId = null;
        try {
            UserDto user = new UserDto(msisdn, "1234", "0000", "Demo Clinic 1");
            UserDto savedUser = userService.createUser(user);
            userId = savedUser.getId();

            ClinicDto clinic = new ClinicDto(msisdn);
            clinic.setLeadTime(14);
            clinic.setSafetyLevel(3);
            userService.activateClinic(clinic);

            UserDto user2 = userService.getUser(msisdn);
            Assert.assertEquals(new Integer(14), user2.getLeadTime());
            Assert.assertEquals(new Integer(3), user2.getSafetyLevel());
            Assert.assertFalse(user2.getActivated());
            
        } finally {
            if (userId != null) userRepository.delete(userId);
        }
    }

    @Test
    public void testToDTOAndBack() throws Exception {
        User user = new User("0118198075", "1234", "1234", "0000", "Demo Clinic 1");
        user.setCoordinates("[29.7962,-28.5868]");
        user.setClinicPhoneNumber("27214626481");
        user.setPharmacistName("Head guy");
        user.setPharmacistMsisdn("27768198075");
        user.setDistrictManagerEmail("dagmar@cell-life.org");
        
        UserDto userDto = new UserDto(user);
        User user2 = userDto.toUser();

        Assert.assertEquals(user, user2);
        Assert.assertEquals("[29.7962,-28.5868]", user2.getCoordinates());
        Assert.assertEquals("27214626481", user2.getClinicPhoneNumber());
        Assert.assertEquals("Head guy", user2.getPharmacistName());
        Assert.assertEquals("27768198075", user2.getPharmacistMsisdn());
        Assert.assertEquals("dagmar@cell-life.org", user2.getDistrictManagerEmail());
    }
}
