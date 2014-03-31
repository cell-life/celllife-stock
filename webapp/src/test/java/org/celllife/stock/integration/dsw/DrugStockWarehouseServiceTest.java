package org.celllife.stock.integration.dsw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.stock.Stock;
import org.celllife.stock.domain.stock.StockType;
import org.celllife.stock.domain.user.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:/META-INF/spring/spring-application.xml",
        "classpath:/META-INF/spring/spring-cache.xml",
        "classpath:/META-INF/spring/spring-config.xml",
        "classpath:/META-INF/spring/spring-domain.xml",
        "classpath:/META-INF/spring/spring-jdbc.xml",
        "classpath:/META-INF/spring/spring-orm.xml",
        "classpath:/META-INF/spring/spring-tx.xml",
        "classpath:/META-INF/spring-data/spring-data-jpa.xml",
        "classpath:/META-INF/spring-integration/spring-integration-core.xml"
})
public class DrugStockWarehouseServiceTest {
	
	@Autowired
	DrugStockWarehouseService dswService;

	@Test
	@Ignore("integration test")
	public void testActivation() throws Exception {
		User user = new User();
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
