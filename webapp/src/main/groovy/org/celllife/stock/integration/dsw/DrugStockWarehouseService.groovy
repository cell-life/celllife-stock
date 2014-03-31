package org.celllife.stock.integration.dsw

import java.util.List;

import org.celllife.stock.domain.stock.Stock;
import org.celllife.stock.domain.user.User;

/**
 * Application service the interfaces with the Drug Stock Warehouse
 */
public interface DrugStockWarehouseService {

	/**
	 * Send the list of Drug Stocks to the DSW.
	 * @param user User belonging to the clinic doing the stock take
	 * @param stock List of Stock takes
	 * @return true if the sending of data was successful
	 */
	boolean sendStockTakes(User user, List<Stock> stock);

	/**
	 * Sends the list of Stock arrivals to the DSW
	 * @param user User belonging to the clinic receiving stock
	 * @param stock List of Stock received
	 * @return true if the sending of data was successful
	 */
	boolean sendStockReceived(User user, List<Stock> stock);

	/**
	 * Sends the list of initial Stock levels to the DSW
	 * @param user User belonging to the clinic being activated
	 * @param stock List of initial Stock levels
	 * @return true if the sending of data was successful
	 */
	boolean sendActivation(User user, List<Stock> stock);
}
