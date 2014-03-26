package org.celllife.stock.domain;

import junit.framework.Assert;

import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:/META-INF/spring/spring-cache.xml",
        "classpath:/META-INF/spring/spring-config.xml",
        "classpath:/META-INF/spring/spring-domain.xml",
        "classpath:/META-INF/spring/spring-jdbc.xml",
        "classpath:/META-INF/spring/spring-orm.xml",
        "classpath:/META-INF/spring/spring-tx.xml",
        "classpath:/META-INF/spring-data/spring-data-jpa.xml"
})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateOne() throws Exception {
    	String msisdn = "0762837491";
    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
    	userRepository.save(user);
    	
    	User savedUser = userRepository.findOneByMsisdn(msisdn);
    	Assert.assertNotNull(savedUser);
    	
    	userRepository.delete(user);
    }
}
