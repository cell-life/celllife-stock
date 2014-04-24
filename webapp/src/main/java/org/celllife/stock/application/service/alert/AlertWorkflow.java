package org.celllife.stock.application.service.alert;

import java.util.Date;

import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertStatus;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public enum AlertWorkflow {
			
	NEW {
		@Override
		Alert process(Alert alert, AlertWorkflowService alertWorkflowService) {
			Alert newAlert = null;
			// At level 1 they are given an extra day grace (the alert has not been received on the phone)
			if ((isGreaterThanTwoDays(alert) && alert.getLevel() == 1)
					// At level 2, they are back on one day per alert level
					|| (isGreaterThanOneDay(alert) && alert.getLevel() == 2)) {
				// expire the old alert, create a new one at the next highest level
				newAlert = createNewAndExpireOldAlert(alert, alertWorkflowService);
				// send out alert notification
				alertWorkflowService.sendCommunication(newAlert);
			} else if (isGreaterThanOneDay(alert) && alert.getLevel() == 1) {
				System.out.println("here");
				// send out extra communication
				alertWorkflowService.sendCommunication(alert);
			}
			return newAlert;
		}
	},
	SENT {
		@Override
		Alert process(Alert alert, AlertWorkflowService alertWorkflowService) {
			Alert newAlert = null;
			// If the alert is older than one day, escalate it
			if (isGreaterThanOneDay(alert) && alert.getLevel() < 3) {
				// expire the old alert, create a new one at the next highest level
				newAlert = createNewAndExpireOldAlert(alert, alertWorkflowService);
				// send out alert notification
				alertWorkflowService.sendCommunication(newAlert);
			}
			return newAlert;
		}
	},
	RESOLVED {
		@Override
		Alert process(Alert alert, AlertWorkflowService alertWorkflowService) {
			// do nothing
			return null;
		}
	},
	CLOSED {
		@Override
		Alert process(Alert alert, AlertWorkflowService alertWorkflowService) {
			// do nothing
			return null;
		}
	},
	EXPIRED {
		@Override
		Alert process(Alert alert, AlertWorkflowService alertWorkflowService) {
			// do nothing
			return null;
		}
	};
	
	public static AlertWorkflow valueOf(AlertStatus status) {
		return AlertWorkflow.valueOf(status.name());
	}

	abstract Alert process(Alert alert, AlertWorkflowService alertWorkflowService);
	
	boolean isGreaterThanOneDay(Alert alert) {
		// FIXME: needs to use service
		long oneDay = 1 * 24 * 60 * 60 * 1000;
		Date now = new Date();
		if (now.getTime() > alert.getDate().getTime() + oneDay) {
			return true;
		}
		return false;
	}

	boolean isGreaterThanTwoDays(Alert alert) {
		// FIXME: needs to use public holidays service
		long twoDays = 2 * 24 * 60 * 60 * 1000;
		Date now = new Date();
		if (now.getTime() > alert.getDate().getTime() + twoDays) {
			return true;
		}
		return false;
	}
	
	Alert createNewAndExpireOldAlert(Alert alert, AlertWorkflowService alertWorkflowService) {
		// create new alert
		Alert newAlert = new Alert(new Date(), alert.getLevel()+1, alert.getMessage(), 
				AlertStatus.NEW, alert.getUser(), alert.getDrug());
		newAlert = alertWorkflowService.createAlert(newAlert);
		// expire the old alert
		alert.setStatus(AlertStatus.EXPIRED);
		alertWorkflowService.updateAlert(alert);

		return newAlert;
	}
}
