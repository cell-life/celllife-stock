package org.celllife.stock.domain.stock;

/**
 * Status of the Stock
 * NEW = stock has been received
 * FAILED = stock was sent to the DSW, but the sending failed
 * SENT = stock information has been sent to the DSW
 */
public enum StockStatus {
	NEW,
	FAILED,
	SENT
}
