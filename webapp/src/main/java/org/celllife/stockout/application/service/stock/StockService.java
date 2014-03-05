package org.celllife.stockout.application.service.stock;

import org.celllife.stockout.domain.stock.StockDto;

public interface StockService {

	/**
	 * Creates the specified Stock Take.
	 * If there is an Alert for the specified Drug and User, then set the status of 
	 * that Alert to be RESOLVED
	 * @param stock
	 * @return
	 */
	StockDto createStockTake(StockDto stock);

	/**
	 * Creates the specified Stock (either a Stock Take/Order or a Stock Received).
	 * If there is an Alert for the specified Drug and User, then set the status of 
	 * that Alert to be CLOSED
	 * @param stock
	 * @return
	 */
	StockDto createStockArrival(StockDto stock);

	/**
	 * Retrieve the stock by specifying the identifier
	 * @param id Long identifier
	 * @return Stock, or null if none found
	 */
	StockDto getStock(Long id);
}