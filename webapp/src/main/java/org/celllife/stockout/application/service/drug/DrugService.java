package org.celllife.stockout.application.service.drug;

import org.celllife.stockout.domain.drug.DrugDto;

/**
 * Drug related services
 */
public interface DrugService {

	/**
	 * Creates the specified drug
	 * @param drug Drug
	 * @return created Drug, with id set
	 */
	DrugDto createDrug(DrugDto drug);
	
	/**
	 * Retrieves the Drug for the specified scanned barcode
	 * @param barcode String barcode
	 * @return
	 */
	DrugDto getDrug(String barcode);
}
