package org.celllife.stock.application.service.alert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertDto;
import org.celllife.stock.domain.alert.AlertRepository;
import org.celllife.stock.domain.alert.AlertStatus;
import org.celllife.stock.domain.alert.AlertSummaryDto;
import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.exception.StockException;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public AlertDto createAlert(AlertDto alert) {
		User user = getUser(alert); // will use either msisdn or cliniccode to locate the user
		Drug drug = getDrug(alert);
		
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

		// save new alert
		Alert newAlert = convertAlert(alert, user, drug);
		newAlert.setStatus(AlertStatus.NEW);
		log.debug("saving alert "+newAlert+" for user="+user+" and drug="+drug);
		Alert savedAlert = alertRepository.save(newAlert);

		return new AlertDto(savedAlert);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<AlertDto> getOpenAlerts(String msisdn) {
		User user = userRepository.findOneByMsisdn(msisdn);
		List<Alert> alerts = alertRepository.findOpenByUser(user);
		return convertAlertCollection(alerts);
	}

	@Override
	@Transactional
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
	@Transactional(readOnly = true)
	public AlertDto getAlert(Long id) {
		Alert alert = alertRepository.findOne(id);
		if (alert != null) {
			return new AlertDto(alert);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Set<AlertSummaryDto> getAlertSummary() {
		List<AlertSummaryDto> summary = alertRepository.calculateAlertSummary();
		Set<AlertSummaryDto> alertSummary = new HashSet<AlertSummaryDto>();
		for (AlertSummaryDto dto : summary) {
			alertSummary.add(dto);
		}
		return alertSummary;
	}

	private User getUser(AlertDto alert) {
		if (alert.getUser() == null) {
			throw new StockException("No user specified for alert. "+alert);
		}
		User user = null;
		if (alert.getUser().getMsisdn() != null && !alert.getUser().getMsisdn().trim().equals("")) {
			user = userRepository.findOneByMsisdn(alert.getUser().getMsisdn());
		}
		if (alert.getUser().getClinicCode() != null && !alert.getUser().getClinicCode().trim().equals("")) {
			List<User> users = userRepository.findByClinicCode(alert.getUser().getClinicCode());
			if (users != null && users.size() > 0) {
				user = users.get(0);
			}
		}
		if (user == null) {
			throw new StockException("Could not find user with msisdn '"+alert.getUser().getMsisdn()+" or clinicCode '"+alert.getUser().getClinicCode()+"'.");
		}
		return user;
	}

	private Drug getDrug(AlertDto alert) {
		if (alert.getDrug() == null) {
			throw new StockException("No drug specified for alert. "+alert);
		}
		Drug drug = drugRepository.findOneByBarcode(alert.getDrug().getBarcode());
		if (drug == null) {
			throw new StockException("Could not find drug with barcode '"+alert.getDrug().getBarcode()+"'.");
		}
		return drug;
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
