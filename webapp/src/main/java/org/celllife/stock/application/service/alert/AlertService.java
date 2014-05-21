package org.celllife.stock.application.service.alert;

import java.util.Set;

import org.celllife.stock.domain.alert.AlertDto;
import org.celllife.stock.domain.alert.AlertSummaryDto;

public interface AlertService {

	/**
	 * Creates an Alert in the system. If an older alert exists for the specified Drug and User, then the older
	 * alert will be "expired". 
	 * @param alert Alert to create
	 * @return saved Alert (will have id set)
	 */
	AlertDto createAlert(AlertDto alert);
	
	/**
	 * Receives a list of open Alerts for the specified User.
	 * Open Alerts are those in "new" or "sent" status.
	 * @param msisdn String msisdn to identify the user
	 * @return List of Alerts
	 */
	Set<AlertDto> getOpenAlerts(String msisdn);
	
	
	/**
	 * Returns a list of "new" Alerts that were created in the last day.
	 * Note all the alerts in the list will be marked as 'SENT'. This means that a repeat call
	 * to the same method will not result in the same Alerts being returned
	 *
	 * @param msisdn String msisdn to identify the user
	 * @return List of Alerts
	 */
	Set<AlertDto> getNewAlerts(String msisdn);

	/**
	 * Retrieve the alert by specifying the identifier
	 * @param id Long identifier
	 * @return Alert, or null if none found
	 */
	AlertDto getAlert(Long id);

	
	/**
	 * Retrieves an alert summary report showing the number of open alerts per clinic
	 */
	Set<AlertSummaryDto> getAlertSummary();
}
