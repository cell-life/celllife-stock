package org.celllife.stock.integration.dsw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.stock.Stock;
import org.celllife.stock.domain.stock.StockType;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.integration.dsw.DrugStockWarehouseService;
import org.celllife.stock.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class DrugStockWarehouseServiceTest {
	
	@Autowired
	DrugStockWarehouseService dswService;

	@Test
	@Ignore("integration test")
	public void testAddDrugs() throws Exception {
		Drug grandpa = new Drug("60015204","Grandpa", "Grandpa 24 tablets");
		boolean success = dswService.createDrug(grandpa);
		Assert.assertTrue(success);
		Drug panado = new Drug("60011053", "Panado", "Panado 500mg 24 tablets");
		success = dswService.createDrug(panado);
		Assert.assertTrue(success);
	}

	@Test
	@Ignore("integration test")
	public void testActivation() throws Exception {
		User user = new User();
		user.setLeadTime(14);
		user.setSafetyLevel(3);
		user.setClinicCode("0000");
		List<Stock> stock = new ArrayList<Stock>();
		Drug grandpa = new Drug("60015204", "Grandpa 24 tablets");
		stock.add(new Stock(new Date(), 25, StockType.ORDER, user, grandpa));
		Drug panado = new Drug("60011053", "Panado 500mg 24 tablets");
		stock.add(new Stock(new Date(), 10, StockType.ORDER, user, panado));
		boolean success = dswService.sendActivation(user, stock);
		Assert.assertTrue(success);
	}

	@Test
	@Ignore("integration test")
	public void testStockTake() throws Exception {
		User user = new User();
		user.setClinicCode("0000");
		List<Stock> stock = new ArrayList<Stock>();
		Drug grandpa = new Drug("60015204", "Grandpa 24 tablets");
		stock.add(new Stock(new Date(), 25, StockType.ORDER, user, grandpa));
		Drug panado = new Drug("60011053", "Panado 500mg 24 tablets");
		stock.add(new Stock(new Date(), 10, StockType.ORDER, user, panado));
		boolean success = dswService.sendStockTakes(user, stock);
		Assert.assertTrue(success);
	}

	@Test
	@Ignore("integration test")
	public void testStockArrival() throws Exception {
		User user = new User();
		user.setClinicCode("0000");
		List<Stock> stock = new ArrayList<Stock>();
		Drug grandpa = new Drug("60015204", "Grandpa 24 tablets");
		stock.add(new Stock(new Date(), 25, StockType.RECEIVED, user, grandpa));
		Drug panado = new Drug("60011053", "Panado 500mg 24 tablets");
		stock.add(new Stock(new Date(), 10, StockType.RECEIVED, user, panado));
		boolean success = dswService.sendStockReceived(user, stock);
		Assert.assertTrue(success);
	}
}
