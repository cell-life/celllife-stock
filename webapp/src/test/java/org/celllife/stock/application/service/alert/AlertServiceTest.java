package org.celllife.stock.application.service.alert;

import java.util.Date;
import java.util.Set;

import junit.framework.Assert;

import org.celllife.stock.application.service.alert.AlertService;
import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertDto;
import org.celllife.stock.domain.alert.AlertRepository;
import org.celllife.stock.domain.alert.AlertStatus;
import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
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
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class AlertServiceTest {

	// service being tested
	@Autowired
	AlertService alertService;

	// repositories used during the test to setup the DB
    @Autowired
    private DrugRepository drugRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertRepository alertRepository;
	
	@Test
	public void testCreateAlert() {
		User user = null;
		Drug drug = null;
		Alert savedAlert1 = null;
		Alert savedAlert2 = null;
		
		try {
			user = createAndSaveUser("0738847292");
			drug = createAndSaveDrug("1112223333");
			Alert alert1 = createAlert(1, user, drug);
			Alert alert2 = createAlert(2, user, drug);
			
			AlertDto newAlert1 = new AlertDto(alert1);
			AlertDto alertDTO1 = alertService.createAlert(newAlert1);
			
			savedAlert1 = alertRepository.findOne(alertDTO1.getId());
			AlertDto savedAlert1Dto = new AlertDto(savedAlert1);
			Assert.assertEquals(alertDTO1, savedAlert1Dto);

			AlertDto alertDTO2 = alertService.createAlert(new AlertDto(alert2)); 

			// now 1st alert should be "expired"
			savedAlert2 = alertRepository.findOne(alertDTO2.getId());
			Assert.assertEquals(alertDTO2, new AlertDto(savedAlert2));
			
    	} finally {
    		if (savedAlert1 != null) alertRepository.delete(savedAlert1);
    		if (savedAlert2 != null) alertRepository.delete(savedAlert2);
        	if (user != null) userRepository.delete(user);
        	if (drug != null) drugRepository.delete(drug);
    	}
	}
	
	@Test
	public void testGetNewAlerts() {
		User user = null;
		Drug drug = null;
		Alert alert = null;
		
		try {
			user = createAndSaveUser("0738847292");
			drug = createAndSaveDrug("1112223333");
			alert = createAndSaveAlert(1, user, drug);
			
			Set<AlertDto> alerts = alertService.getNewAlerts(user.getMsisdn());
			Assert.assertNotNull(alerts);
			Assert.assertEquals(1, alerts.size());
			Assert.assertEquals(new AlertDto(alert), alerts.toArray()[0]);
			
			alerts = alertService.getNewAlerts(user.getMsisdn());
			Assert.assertNotNull(alerts);
			Assert.assertEquals(0, alerts.size());
			
    	} finally {
    		if (alert != null) alertRepository.delete(alert);
        	if (user != null) userRepository.delete(user);
        	if (drug != null) drugRepository.delete(drug);
    	}
	}
	
	private Alert createAlert(Integer level, User user, Drug drug) {
    	Alert alert = new Alert(new Date(), level, "Testing Alerts", AlertStatus.NEW, user, drug);
    	return alert;
	}

	private Alert createAndSaveAlert(Integer level, User user, Drug drug) {
    	Alert alert = createAlert(level, user, drug);
    	return alertRepository.save(alert);
	}
	
	private User createAndSaveUser(String msisdn) {
		User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
    	return userRepository.save(user);
	}
	
	private Drug createAndSaveDrug(String barcode) {
		Drug drug = new Drug(barcode, "Disprin");
    	return drugRepository.save(drug);
	}
}
