package org.celllife.stockout.domain;

import java.util.Date;

import junit.framework.Assert;

import org.celllife.stockout.domain.alert.Alert;
import org.celllife.stockout.domain.alert.AlertRepository;
import org.celllife.stockout.domain.alert.AlertStatus;
import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.drug.DrugRepository;
import org.celllife.stockout.domain.user.User;
import org.celllife.stockout.domain.user.UserRepository;
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
public class AlertRepositoryTest {
	
    @Autowired
    private DrugRepository drugRepository;

	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private AlertRepository alertRepository;

    @Test
    public void testCreateOne() throws Exception {
    	String msisdn = "0762837491";
    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
    	User savedUser = userRepository.save(user);
    	
    	String barcode = "0762837491";
    	Drug drug = new Drug(barcode, "Disprin");
    	Drug savedDrug = drugRepository.save(drug);
    	
    	Alert alert = new Alert(new Date(), 1, "Testing Alerts", AlertStatus.NEW, savedUser, savedDrug);
    	alertRepository.save(alert);
    	Alert savedAlert = alertRepository.findLatestByUserAndDrug(savedUser, savedDrug);
    	Assert.assertNotNull(savedAlert);
    	
    	alertRepository.delete(savedAlert);
    	userRepository.delete(savedUser);
    	drugRepository.delete(savedDrug);
    }
}
