package org.celllife.stock.application.service.alert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertRepository;
import org.celllife.stock.domain.alert.AlertStatus;
import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.notification.Notification;
import org.celllife.stock.domain.notification.NotificationRepository;
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
public class AlertWorkflowServiceTest {

	// service being tested
	@Autowired
	AlertWorkflowService alertWorkflowService;

	// repositories used during the test to setup the DB
    @Autowired
    private DrugRepository drugRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private NotificationRepository notificationRepository;

	@Test
	public void testRunWorkflowAlertsFromToday() throws Exception {
		User user = null;
		Drug drug1 = null;
		Alert alert1 = null;
		
		alertRepository.deleteAll();
		drugRepository.deleteAll();
		userRepository.deleteAll();
		
		try {
			user = createAndSaveUser("27768198076");
			drug1 = createAndSaveDrug("1112223331", "Disprin");
			alert1 = createAndSaveAlert(new Date(), 1, AlertStatus.NEW, user, drug1);

			alertWorkflowService.runWorkflow();
			
			Thread.sleep(5000); // wait for threads to finish
			
			List<Notification> notifications = notificationRepository.findByAlert(alert1);
			Assert.assertNotNull(notifications);
			Assert.assertTrue(notifications.isEmpty());
			
    	} finally {
    		if (alert1 != null) alertRepository.delete(alert1);
        	if (user != null) userRepository.delete(user);
        	if (drug1 != null) drugRepository.delete(drug1);
    	}
	}
	
	@Test
	public void testRunWorkflow() throws Exception {
		User user = null;
		Drug drug1 = null;
		Drug drug2 = null;
		Drug drug3 = null;
		Drug drug4 = null;
		Alert alert1 = null;
		Alert alert2 = null;
		Alert alert3 = null;
		Alert alert4 = null;
		List<Notification> notifications = new ArrayList<Notification>();
		
		notificationRepository.deleteAll();
		alertRepository.deleteAll();
		drugRepository.deleteAll();
		userRepository.deleteAll();
		
		try {
			user = createAndSaveUser("27768198076");
			drug1 = createAndSaveDrug("1112223331", "Disprin");
			drug2 = createAndSaveDrug("1112223332", "Panado");
			drug3 = createAndSaveDrug("1112223333", "Grandpa");
			drug4 = createAndSaveDrug("1112223334", "Ibuprofin");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -3);
			alert1 = createAndSaveAlert(cal.getTime(), 1, AlertStatus.NEW, user, drug1);
			System.out.println("HELLO... alert1="+alert1);
			cal.add(Calendar.DATE, 2);
			alert2 = createAndSaveAlert(cal.getTime(), 1, AlertStatus.NEW, user, drug4);
			System.out.println("HELLO... alert2="+alert2);
			alert3 = createAndSaveAlert(cal.getTime(), 2, AlertStatus.SENT, user, drug2);
			System.out.println("HELLO... alert3="+alert3);
			alert4 = createAndSaveAlert(cal.getTime(), 3, AlertStatus.SENT, user, drug3);
			System.out.println("HELLO... alert4="+alert4);

			alertWorkflowService.runWorkflow();
			
			System.out.println("waiting for threads to finish");
			Thread.sleep(5000);
			
			Iterator<Notification> notificationIt = notificationRepository.findAll().iterator();
			Assert.assertTrue(notificationIt.hasNext());
			int counter = 0;
			List<Drug> drugs = new ArrayList<Drug>();
			while (notificationIt.hasNext()) {
				Notification notification = notificationIt.next();
				counter++;
				System.out.println("notification="+notification);
				System.out.println("notification.alert="+notification.getAlert());
				drugs.add(notification.getAlert().getDrug());
				notifications.add(notification);
			}
			Assert.assertEquals(3, counter);
			
			for (Notification n : notifications) {
			    if (n.getAlert().equals(alert3)) {
			        // sms
			        Assert.assertEquals("SMS is being sent to pharmacist", "27768198075", n.getRecipient());
			        String message = n.getMessage();
			        Assert.assertTrue("Message contains drug name", message.contains(drug2.getName()));
			        Assert.assertTrue("Message contains clinic name", message.contains("Demo Clinic 1"));
			    }
			    if (n.getAlert().equals(alert4)) {
			        // email
			        Assert.assertEquals("SMS is being sent to district manager", "dagmar@cell-life.org", n.getRecipient());
			        String message = n.getMessage();
			        Assert.assertTrue("Message contains clinic phone number", message.contains("021 462 6481"));
	                Assert.assertTrue("Message contains clinic name", message.contains("Demo Clinic 1"));
	                Assert.assertTrue("Message contains drug name", message.contains(drug3.getName()));
	                Assert.assertTrue("Message contains pharmacist name", message.contains("Dagmar Timler"));
	                Assert.assertTrue("Message contains pharmacist msisdn", message.contains("27768198075"));
			    }
			}
			
			Assert.assertTrue(drugs.contains(drug1));
			Assert.assertTrue(drugs.contains(drug4));
			Assert.assertTrue(drugs.contains(drug2));

    	} finally {
    		notificationRepository.deleteAll();
    		alertRepository.deleteAll();
    		drugRepository.deleteAll();
    		userRepository.deleteAll();
    		
    		/*
    		for (Notification n : notifications) {
    			notificationRepository.delete(n);
    		}
    		if (alert1 != null) alertRepository.delete(alert1);
    		if (alert2 != null) alertRepository.delete(alert2);
    		if (alert3 != null) alertRepository.delete(alert3);
    		if (alert4 != null) alertRepository.delete(alert4);
        	if (drug1 != null) drugRepository.delete(drug1);
        	if (drug2 != null) drugRepository.delete(drug2);
        	if (drug3 != null) drugRepository.delete(drug3);
        	if (drug4 != null) drugRepository.delete(drug4);
        	if (user != null) userRepository.delete(user);
        	*/
    	}
	}

	private Alert createAndSaveAlert(Date date, Integer level, AlertStatus status, User user, Drug drug) {
    	Alert alert = new Alert(date, level, "Test alert", status, user, drug);
    	return alertRepository.save(alert);
	}
	
	private User createAndSaveUser(String msisdn) {
		User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
		user.setClinicPhoneNumber("021 462 6481");
		user.setDistrictManagerEmail("dagmar@cell-life.org");
		user.setPharmacistName("Dagmar Timler");
		user.setPharmacistMsisdn("27768198075");
    	return userRepository.save(user);
	}
	
	private Drug createAndSaveDrug(String barcode, String name) {
		Drug drug = new Drug(barcode, name, name);
    	return drugRepository.save(drug);
	}
}

