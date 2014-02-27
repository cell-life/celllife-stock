package org.celllife.stockout.domain.alert;

/**
 * Status of the Alert
 * NEW = alert has been received
 * RECEIVED = alert has been sent to the mobile client
 * EXPIRED = alert has been replaced by a newer alert
 */
public enum AlertStatus {
	NEW,
	RECEIVED,
	EXPIRED
}
