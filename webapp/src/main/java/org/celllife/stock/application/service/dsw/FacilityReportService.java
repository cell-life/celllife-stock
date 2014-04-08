package org.celllife.stock.application.service.dsw;

import org.celllife.stock.integration.dsw.DrugStockWarehouseService;

public interface FacilityReportService {

	void runStockTakeReport();
	void runStockArrivalReport();
	
	void setDrugStockWarehouseService(DrugStockWarehouseService dswService);
}
