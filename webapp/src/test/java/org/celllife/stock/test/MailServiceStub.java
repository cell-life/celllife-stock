package org.celllife.stock.test;

import org.celllife.utilities.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MailServiceStub implements MailService {
	
	private static Logger log = LoggerFactory.getLogger(MailServiceStub.class);

	@Override
	public void sendEmail(String to, String from, String subject, String text) {
		log.debug("STUB: sendEmail to="+to+" from="+from+" subject="+subject+" text="+text);
	}

}
