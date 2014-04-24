package org.celllife.stock.application.service.alert;

import org.celllife.stock.domain.alert.Alert;

/**
 * Service used to implement the Alert process workflow
 */
public interface AlertWorkflowService {

	/**
	 * Updates the alert by saving the specified alert as is
	 */
	Alert updateAlert(Alert alert);
	/**
	 * Saves the specified alert
	 */
	Alert createAlert(Alert alert);
	/**
	 * Sends the necessary communication at the appropriate level.
	 * Level 2 communication - an SMS/email to the pharmacy manager
	 * Level 3 communication - an SMS/email to the district manager
	 * @param alert
	 */
	void sendCommunication(Alert alert);

	/**
	 * Executes the workflow - this involves going through all the open alerts and then
	 * using the AlertWorkflow to determine the next step
	 */
	void runWorkflow();
}
