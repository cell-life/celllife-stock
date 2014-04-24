package org.celllife.stock.test;

import org.celllife.mobilisr.client.CampaignService;
import org.celllife.mobilisr.client.ContactService;
import org.celllife.mobilisr.client.MobilisrClient;
import org.springframework.stereotype.Service;

@Service
public class CommunicateStub implements MobilisrClient {

	@Override
	public CampaignService getCampaignService() {
		return new CommunicateCampaignServiceStub();
	}

	@Override
	public ContactService getContactService() {
		return new CommunicateContactServiceStub();
	}

}
