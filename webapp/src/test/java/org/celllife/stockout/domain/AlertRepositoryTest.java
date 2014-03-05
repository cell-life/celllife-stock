package org.celllife.stockout.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    	Alert savedAlert = null;
    	User savedUser = null;
    	Drug savedDrug = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	savedUser = userRepository.save(user);
	    	
	    	String barcode = "0762837491";
	    	Drug drug = new Drug(barcode, "Disprin");
	    	savedDrug = drugRepository.save(drug);
	    	
	    	Alert alert = new Alert(new Date(), 1, "Testing Alerts", AlertStatus.NEW, savedUser, savedDrug);
	    	alertRepository.save(alert);
	    	savedAlert = alertRepository.findOneLatestByUserAndDrug(savedUser, savedDrug);
	    	Assert.assertNotNull(savedAlert);

    	} finally {
    		if (savedAlert != null) alertRepository.delete(savedAlert);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug != null) drugRepository.delete(savedDrug);
    	}    	
    }

    @Test
    public void testFindOnlyNewAlertForDrug() throws Exception {   	
    	Alert savedAlert1 = null;
    	Alert savedAlert2 = null;
    	User savedUser = null;
    	Drug savedDrug = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	savedUser = userRepository.save(user);
	    	
	    	String barcode = "0762837491";
	    	Drug drug = new Drug(barcode, "Disprin");
	    	savedDrug = drugRepository.save(drug);
	    	
	    	Calendar cal = Calendar.getInstance();
	    	cal.add(Calendar.MONTH, -2);
	    	Alert alert1 = new Alert(cal.getTime(), 1, "Testing Alerts", AlertStatus.EXPIRED, savedUser, savedDrug);
	    	savedAlert1 = alertRepository.save(alert1);
	    	cal.add(Calendar.MONTH, +2);
	    	Alert alert2 = new Alert(cal.getTime(), 2, "Testing Alerts", AlertStatus.NEW, savedUser, savedDrug);
	    	savedAlert2 = alertRepository.save(alert2);
	    	
	    	Alert latestAlert = alertRepository.findOneLatestByUserAndDrug(savedUser, savedDrug);
	    	Assert.assertNotNull(latestAlert);
	    	Assert.assertEquals(latestAlert, savedAlert2);
    	} finally {
    		if (savedAlert1 != null) alertRepository.delete(savedAlert1);
    		if (savedAlert2 != null) alertRepository.delete(savedAlert2);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug != null) drugRepository.delete(savedDrug);    		
    	}
    }

    @Test
    public void testFindOnlyNewAlerts() throws Exception {   	
    	Alert savedAlert1 = null;
    	Alert savedAlert2 = null;
    	Alert savedAlert3 = null;
    	User savedUser = null;
    	Drug savedDrug1 = null;
    	Drug savedDrug2 = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	savedUser = userRepository.save(user);
	    	
	    	String barcode1 = "0762837491";
	    	Drug drug1 = new Drug(barcode1, "Disprin");
	    	savedDrug1 = drugRepository.save(drug1);
	    	String barcode2 = "0762837492";
	    	Drug drug2 = new Drug(barcode2, "Pando");
	    	savedDrug2 = drugRepository.save(drug2);
	    	
	    	Alert alert1 = new Alert(new Date(), 1, "Testing Alerts", AlertStatus.NEW, savedUser, savedDrug1);
	    	savedAlert1 = alertRepository.save(alert1);
	    	Alert alert2 = new Alert(new Date(), 2, "Testing Alerts", AlertStatus.EXPIRED, savedUser, savedDrug2);
	    	savedAlert2 = alertRepository.save(alert2);
	    	Alert alert3 = new Alert(new Date(), 3, "Testing Alerts", AlertStatus.NEW, savedUser, savedDrug2);
	    	savedAlert3 = alertRepository.save(alert3);
	    	
	    	List<Alert> latestAlerts = alertRepository.findNewByUser(user);
	    	Assert.assertNotNull(latestAlerts);
	    	Assert.assertEquals(2, latestAlerts.size());
    	} finally {
    		if (savedAlert1 != null) alertRepository.delete(savedAlert1);
    		if (savedAlert2 != null) alertRepository.delete(savedAlert2);
    		if (savedAlert3 != null) alertRepository.delete(savedAlert3);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug1 != null) drugRepository.delete(savedDrug1);
        	if (savedDrug2 != null) drugRepository.delete(savedDrug2);
    	}
    }

    @Test
    public void testFindOpenAlerts() throws Exception {   	
    	Alert savedAlert1 = null;
    	Alert savedAlert2 = null;
    	Alert savedAlert3 = null;
    	User savedUser = null;
    	Drug savedDrug1 = null;
    	Drug savedDrug2 = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	savedUser = userRepository.save(user);
	    	
	    	String barcode1 = "0762837491";
	    	Drug drug1 = new Drug(barcode1, "Disprin");
	    	savedDrug1 = drugRepository.save(drug1);
	    	String barcode2 = "0762837492";
	    	Drug drug2 = new Drug(barcode2, "Pando");
	    	savedDrug2 = drugRepository.save(drug2);
	    	
	    	Alert alert1 = new Alert(new Date(), 1, "Testing Alerts", AlertStatus.SENT, savedUser, savedDrug1);
	    	savedAlert1 = alertRepository.save(alert1);
	    	Alert alert2 = new Alert(new Date(), 2, "Testing Alerts", AlertStatus.EXPIRED, savedUser, savedDrug2);
	    	savedAlert2 = alertRepository.save(alert2);
	    	Alert alert3 = new Alert(new Date(), 3, "Testing Alerts", AlertStatus.NEW, savedUser, savedDrug2);
	    	savedAlert3 = alertRepository.save(alert3);
	    	
	    	List<Alert> latestAlerts = alertRepository.findOpenByUser(user);
	    	Assert.assertNotNull(latestAlerts);
	    	Assert.assertEquals(2, latestAlerts.size());
    	} finally {
    		if (savedAlert1 != null) alertRepository.delete(savedAlert1);
    		if (savedAlert2 != null) alertRepository.delete(savedAlert2);
    		if (savedAlert3 != null) alertRepository.delete(savedAlert3);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug1 != null) drugRepository.delete(savedDrug1);
        	if (savedDrug2 != null) drugRepository.delete(savedDrug2);
    	}
    }
}

