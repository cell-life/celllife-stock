package org.celllife.stock.domain.stock;

import java.util.Comparator;
import java.util.Date;

/**
 * Compares two Stocks by their date.
 */
public class StockComparator implements Comparator<Stock> {

	@Override
	public int compare(Stock lhs, Stock rhs) {
		// no check for nulls because you can't compare a null - therefore this method cannot accept them.
		Date date1 = lhs.getDate();
		Date date2 = rhs.getDate();

		return date2.compareTo(date1); // we want the latest stock first
	}
}
