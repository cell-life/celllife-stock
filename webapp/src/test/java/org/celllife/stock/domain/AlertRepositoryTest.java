package org.celllife.stock.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertRepository;
import org.celllife.stock.domain.alert.AlertStatus;
import org.celllife.stock.domain.alert.AlertSummaryDto;
import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.celllife.stock.test.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
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

    @Test
    public void testAlertSummary() throws Exception {   	
    	Alert savedAlert1 = null;
    	Alert savedAlert2 = null;
    	Alert savedAlert3 = null;
    	Alert savedAlert4 = null;
    	User savedUser = null;
    	Drug savedDrug1 = null;
    	Drug savedDrug2 = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	String coordinates = "[18.4789,-33.9900]";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	user.setCoordinates(coordinates);
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
	    	Alert alert4 = new Alert(new Date(), 1, "Testing Alerts", AlertStatus.NEW, savedUser, savedDrug2);
	    	savedAlert4 = alertRepository.save(alert4);
	    	
	    	List<AlertSummaryDto> alertSummary = alertRepository.calculateAlertSummary();
	    	Assert.assertNotNull(alertSummary);
	    	Assert.assertEquals(1, alertSummary.size());
	    	AlertSummaryDto dto = alertSummary.get(0);
	    	Assert.assertNotNull(dto.getClinic());
	    	Assert.assertEquals("0000", dto.getClinic().getClinicCode());
	    	Assert.assertEquals("Demo Clinic 1", dto.getClinic().getClinicName());
	    	Assert.assertEquals(msisdn, dto.getClinic().getMsisdn());
	    	Assert.assertEquals(coordinates, dto.getClinic().getCoordinates());
	    	Assert.assertEquals(new Long(2), dto.getLevel1AlertCount());
	    	Assert.assertEquals(new Long(0), dto.getLevel2AlertCount());
	    	Assert.assertEquals(new Long(1), dto.getLevel3AlertCount());
	    	
    	} finally {
    		if (savedAlert1 != null) alertRepository.delete(savedAlert1);
    		if (savedAlert2 != null) alertRepository.delete(savedAlert2);
    		if (savedAlert3 != null) alertRepository.delete(savedAlert3);
    		if (savedAlert4 != null) alertRepository.delete(savedAlert4);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug1 != null) drugRepository.delete(savedDrug1);
        	if (savedDrug2 != null) drugRepository.delete(savedDrug2);
    	}
    }
}

