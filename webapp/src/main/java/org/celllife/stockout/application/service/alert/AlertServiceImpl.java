package org.celllife.stockout.application.service.alert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.celllife.stockout.domain.alert.Alert;
import org.celllife.stockout.domain.alert.AlertDto;
import org.celllife.stockout.domain.alert.AlertRepository;
import org.celllife.stockout.domain.alert.AlertStatus;
import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.drug.DrugRepository;
import org.celllife.stockout.domain.user.User;
import org.celllife.stockout.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertServiceImpl implements AlertService {
	
	private static Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);
	
	@Autowired
	AlertRepository alertRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DrugRepository drugRepository;

	@Override
	public AlertDto createAlert(AlertDto alert) {
		User user = userRepository.findOneByMsisdn(alert.getUser().getMsisdn());
		Drug drug = drugRepository.findOneByBarcode(alert.getDrug().getBarcode());
		
		// FIXME: always assumes that user + drug are found. Should throw Exception if not
		log.warn("saving alert1 ... "+alert+" user="+user+" drug="+drug);
		
		// save new alert
		Alert newAlert = convertAlert(alert, user, drug);
		log.warn("saving alert2 ... "+newAlert+" user="+user+" drug="+drug);
		newAlert.setStatus(AlertStatus.NEW);
		Alert savedAlert = alertRepository.save(newAlert);
		
		// update latest alert to be expired (latest alert is only status NEW or SENT)
		Alert oldAlert = alertRepository.findOneLatestByUserAndDrug(user, drug);
		if (oldAlert != null) {
			if (log.isDebugEnabled()) {
				log.warn("Expiring related alert '"+alert.getId()+"' (status='"+alert.getStatus()+"') for " +
						"user '"+user.getMsisdn()+"' in clinic '"+user.getClinicName()+"' for " +
								"drug '"+drug.getDescription()+"'");
			}
			oldAlert.setStatus(AlertStatus.EXPIRED);
			alertRepository.save(oldAlert);
		}

		return new AlertDto(savedAlert);
	}

	@Override
	public Set<AlertDto> getOpenAlerts(String msisdn) {
		User user = userRepository.findOneByMsisdn(msisdn);
		List<Alert> alerts = alertRepository.findNewByUser(user);
		return convertAlertCollection(alerts);
	}

	@Override
	// FIXME: desperately need a transaction here
	public Set<AlertDto> getNewAlerts(String msisdn) {
		User user = userRepository.findOneByMsisdn(msisdn);
		List<Alert> alerts = alertRepository.findNewByUser(user);
		Set<AlertDto> alertDTOs = convertAlertCollection(alerts);
		
		// mark alerts as sent
		for (Alert alert : alerts) {
			alert.setStatus(AlertStatus.SENT);
			alertRepository.save(alert);
		}
		
		return alertDTOs;
	}

	@Override
	public AlertDto getAlert(Long id) {
		Alert alert = alertRepository.findOne(id);
		if (alert != null) {
			return new AlertDto(alert);
		} else {
			return null;
		}
	}

	private Set<AlertDto> convertAlertCollection(List<Alert> alerts) {
		Set<AlertDto> alertDTOs = new HashSet<AlertDto>();
		for (Alert alert : alerts) {
			alertDTOs.add(new AlertDto(alert));
		}
		return alertDTOs;
	}

	private Alert convertAlert(AlertDto alert, User user, Drug drug) {
		Alert newAlert = new Alert(alert.getDate(), alert.getLevel(), alert.getMessage(), alert.getStatus(), 
				user, drug);
		return newAlert;
	}
}
