package org.celllife.stockout.domain.exception;

/**
 * A general purpose Stock Out App Server exception
 */
public class StockOutException extends RuntimeException {

	private static final long serialVersionUID = -8646842078307129109L;

	public StockOutException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockOutException(String message) {
		super(message);
	}
}
