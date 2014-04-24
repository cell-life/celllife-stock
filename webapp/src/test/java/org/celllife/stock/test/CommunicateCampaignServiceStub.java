package org.celllife.stock.test;

import java.util.List;
import java.util.Map;

import org.celllife.mobilisr.api.rest.CampaignDto;
import org.celllife.mobilisr.api.rest.ContactDto;
import org.celllife.mobilisr.api.rest.PagedListDto;
import org.celllife.mobilisr.client.CampaignService;
import org.celllife.mobilisr.client.exception.RestCommandException;
import org.celllife.mobilisr.constants.CampaignStatus;
import org.celllife.mobilisr.constants.CampaignType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommunicateCampaignServiceStub implements CampaignService {
	
	private static Logger log = LoggerFactory.getLogger(CommunicateCampaignServiceStub.class);

	@Override
	public PagedListDto<CampaignDto> getCampaigns() throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(Integer offset, Integer limit) throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(CampaignType type) throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(CampaignType type, Integer offset, Integer limit)
			throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(CampaignStatus status) throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(CampaignStatus status, Integer offset, Integer limit)
			throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(CampaignType type, CampaignStatus status) throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(CampaignType type, CampaignStatus status, Integer offset,
			Integer limit) throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedListDto<CampaignDto> getCampaigns(Map<String, Object> parameters) throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CampaignDto getCampaignDetails(Long campaignId) throws RestCommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addContactToCampaign(Long campaignId, ContactDto contact) throws RestCommandException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addContactsToCampaign(Long campaignId, List<ContactDto> contacts) throws RestCommandException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeContactFromCampaign(Long campaignId, String msisdn) throws RestCommandException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeContactFromCampaign(Long campaignId, ContactDto contact) throws RestCommandException {
		// TODO Auto-generated method stub

	}

	@Override
	public long createNewCampaign(CampaignDto campaign) throws RestCommandException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void createNewCampaign(String name, String description, String message, List<ContactDto> contacts)
			throws RestCommandException {
		log.debug("STUB createNewCampaign name="+name+" description="+description+" message="+message+" contacts="+contacts);
	}

}
