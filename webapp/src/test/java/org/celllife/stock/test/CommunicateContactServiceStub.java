package org.celllife.stock.test;

import org.celllife.mobilisr.api.rest.ContactDto;
import org.celllife.mobilisr.client.ContactService;
import org.celllife.mobilisr.client.exception.RestCommandException;
import org.springframework.stereotype.Service;

@Service
public class CommunicateContactServiceStub implements ContactService {

	@Override
	public void updateContactDetails(String originalMsisdn, ContactDto contact) throws RestCommandException {
		// TODO Auto-generated method stub

	}

}
