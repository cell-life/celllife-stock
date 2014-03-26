package org.celllife.stock.domain.exception;

/**
 * A general purpose Stock Out App Server exception
 */
public class StockException extends RuntimeException {

	private static final long serialVersionUID = -8646842078307129109L;

	public StockException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockException(String message) {
		super(message);
	}
}
