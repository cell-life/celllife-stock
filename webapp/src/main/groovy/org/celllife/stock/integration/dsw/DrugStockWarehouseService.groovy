package org.celllife.stock.integration.dsw

import org.celllife.stock.domain.drug.Drug
import org.celllife.stock.domain.stock.Stock
import org.celllife.stock.domain.user.User

/**
 * Application service the interfaces with the Drug Stock Warehouse
 */
public interface DrugStockWarehouseService {

	/**
	 * Send the list of Drug Stocks to the DSW.
	 * @param user User belonging to the clinic doing the stock take
	 * @param stock List of Stock takes - must all be on the same date
	 * @param update Boolean TRUE if there was already stock submitted on the date, FALSE otherwise
	 * @return true if the sending of data was successful
	 */
	boolean sendStockTakes(User user, List<Stock> stock, Boolean update);

	/**
	 * Sends the list of Stock arrivals to the DSW
	 * @param user User belonging to the clinic receiving stock
	 * @param stock List of Stock received  - must all be on the same date
	 * @param update Boolean TRUE if there was already stock received on the date, FALSE otherwise
	 * @return true if the sending of data was successful
	 */
	boolean sendStockReceived(User user, List<Stock> stock, Boolean update);

	/**
	 * Sends the list of initial Stock levels to the DSW
	 * @param user User belonging to the clinic being activated
	 * @param stock List of initial Stock levels
	 * @return true if the sending of data was successful
	 */
	boolean sendActivation(User user, List<Stock> stock);
    
    /**
     * Creates a pharmacy entity on the DSW
     * @param user User with details of the pharmacy
     * @return true if the creation was a success
     */
    boolean createPharmacy(User user);

	/**
	 * Creates the drug in the DSW
	 * @param drug Drug to create
	 * @return true if the drug was created successfully
	 */
	boolean createDrug(Drug drug);
}
