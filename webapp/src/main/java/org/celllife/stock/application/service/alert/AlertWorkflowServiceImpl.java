package org.celllife.stock.application.service.alert;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.celllife.mobilisr.api.rest.ContactDto;
import org.celllife.mobilisr.client.MobilisrClient;
import org.celllife.mobilisr.client.exception.RestCommandException;
import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertRepository;
import org.celllife.stock.domain.notification.Notification;
import org.celllife.stock.domain.notification.NotificationRepository;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.celllife.utilities.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AlertWorkflowServiceImpl implements AlertWorkflowService {
	
	private static Logger log = LoggerFactory.getLogger(AlertWorkflowServiceImpl.class);
	
	@Autowired
	AlertRepository alertRepository;

	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	MobilisrClient communicateClient;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	MailService mailService;
	
	@Value("${mailSender.from}")
	String mailFromAddress;
	
	@Value("${tmp.contact.pharmacist.msisdn}")
	String pharmacistMsisdn;

	@Value("${tmp.contact.pharmacist.name}")
	String pharmacistName;
	
	@Value("${tmp.contact.clinic.phoneNumber}")
	String clinicPhoneNumber;

	@Value("${tmp.contact.districtManager.email}")
	String districtManagerEmail;

	@Override
	public Alert updateAlert(Alert alert) {
		return alertRepository.save(alert);
	}

	@Override
	public Alert createAlert(Alert alert) {
		return alertRepository.save(alert);
	}

	@Override
	public void sendCommunication(Alert alert) {
		switch (alert.getLevel()) {
		case 1:
			// SMS to app phone (this is in the case they haven't receive the alert via the app somehow)
			String smsText1 = getAppSmsText(alert);
			sendSms(alert.getUser().getMsisdn(), smsText1, alert);
			break;
		case 2:
			// SMS to pharmacy manager
			String smsText2 = getPharmacistSmsText(alert);
			sendSms(pharmacistMsisdn, smsText2, alert);
			break;
		case 3:
			// email to district manager
			sendEmail(districtManagerEmail, alert);
			break;
		}
	}
	
	void sendSms(String msisdn, String smsText, Alert alert) {
		ContactDto contact = new ContactDto();
		contact.setMsisdn(msisdn);
		List<ContactDto> contacts = new ArrayList<ContactDto>();
		contacts.add(contact);
		String campaignName = "StockApp notification "+new Date()+"-"+msisdn;
		String campaignDescription = "StockApp notification JustSendSMS to "+msisdn;
		try {
			communicateClient.getCampaignService().createNewCampaign(campaignName, campaignDescription, smsText, contacts);
		} catch (RestCommandException e) {
			log.error("Could not send a notification SMS '"+smsText+"' to '"+msisdn+"'.",e);
		}
		log.debug("Should have sent notification SMS '"+smsText+"' to msisdn '"+msisdn+"'. "+alert);
		saveNotification(smsText, msisdn, alert);
	}

	String getAppSmsText(Alert alert) {
		String[] args = new String[] { alert.getDrug().getName() };
		String smsText = messageSource.getMessage("app.smsText", args, null, null);
		log.debug("app.SmsText="+smsText);
		return smsText;
	}

	String getPharmacistSmsText(Alert alert) {
		String[] args = new String[] { alert.getDrug().getName(), alert.getUser().getClinicName() };
		String smsText = messageSource.getMessage("pharmacist.smsText", args, null, null);
		log.debug("pharmacist.SmsText="+smsText);
		return smsText;
	}
	
	private void sendEmail(String emailAddress, Alert alert) {
		String subject = getEmailSubject(alert);
		String text = getEmailText(alert);
		mailService.sendEmail(emailAddress, mailFromAddress, subject, text);
		log.debug("Should have sent email '"+text+"' to '"+emailAddress+"'. "+alert);
		saveNotification(text, emailAddress, alert);
	}

	String getEmailText(Alert alert) {
		String[] args = new String[] { alert.getUser().getClinicName(), alert.getDrug().getName(), 
				pharmacistName, pharmacistMsisdn, clinicPhoneNumber };
		String emailText = messageSource.getMessage("districtManager.emailContent", args, null, null);
		log.debug("EmailText="+emailText);
		return emailText;
	}

	String getEmailSubject(Alert alert) {
		String[] args = new String[] { alert.getDrug().getName(), alert.getUser().getClinicName() };
		String subjectText = messageSource.getMessage("districtManager.emailSubject", args, null, null);
		log.debug("EmailSubject="+subjectText);
		return subjectText;
	}
	
	void saveNotification(String message, String recipient, Alert alert) {
		Notification notification = new Notification(new Date(), message, recipient, alert);
		Notification n = notificationRepository.save(notification);
		log.debug("saving notification="+n);
	}

	@Override
	@Scheduled(cron = "${alertworkflow.cron}")
	public void runWorkflow() {
		Iterator<User> userIt = userRepository.findAll().iterator();
		while (userIt.hasNext()) {
			User user = userIt.next();
			log.debug("Looking at user "+user);
			List<Alert> alerts = alertRepository.findOpenByUser(user);
			for (Alert alert : alerts) {
				log.debug("Looking at alert "+alert);
				AlertWorkflow workflow = AlertWorkflow.valueOf(alert.getStatus());
				workflow.process(alert, this);
			}
		}
	}
}