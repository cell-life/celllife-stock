package org.celllife.stock.application.service.stock;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertRepository;
import org.celllife.stock.domain.alert.AlertStatus;
import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.stock.Stock;
import org.celllife.stock.domain.stock.StockDto;
import org.celllife.stock.domain.stock.StockRepository;
import org.celllife.stock.domain.stock.StockType;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.celllife.stock.test.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class StockServiceTest {

	// service being tested
	@Autowired
	StockService stockService;

	// repositories used during the test to setup the DB
    @Autowired
    private DrugRepository drugRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private StockRepository stockRepository;
	
	@Test
	public void testCreateStockTake() {
		User user = null;
		Drug drug = null;
		Alert alert = null;
		Stock stock = null;
		
		try {
			user = createAndSaveUser("0738847292");
			drug = createAndSaveDrug("1112223333");
			alert = createAndSaveAlert(1, user, drug);
			stock = createStock(user, drug);
			
			stockService.createStockTake(new StockDto(stock));

			// alert should be marked as resolved
			Alert savedAlert = alertRepository.findOne(alert.getId());
			Assert.assertEquals(AlertStatus.RESOLVED, savedAlert.getStatus());
			
    	} finally {
    		if (alert != null) alertRepository.delete(alert);
    		List<Stock> stocks = stockRepository.findByDrug(drug);
    		for (Stock s : stocks) {
    			stockRepository.delete(s);
    		}
        	if (user != null) userRepository.delete(user);
        	if (drug != null) drugRepository.delete(drug);
    	}
	}

	@Test
	public void testCreateStockArrival() {
		User user = null;
		Drug drug = null;
		Alert alert = null;
		Stock stock = null;
		
		try {
			user = createAndSaveUser("0738847292");
			drug = createAndSaveDrug("1112223333");
			alert = createAndSaveAlert(1, user, drug);
			stock = createStock(user, drug);
			
			stockService.createStockTake(new StockDto(stock));

			// alert should be marked as resolved
			Alert savedAlert = alertRepository.findOne(alert.getId());
			Assert.assertEquals(AlertStatus.RESOLVED, savedAlert.getStatus());

			stockService.createStockArrival(new StockDto(stock));
			// alert should be marked as closed
			savedAlert = alertRepository.findOne(alert.getId());
			Assert.assertEquals(AlertStatus.CLOSED, savedAlert.getStatus());

			
    	} finally {
    		if (alert != null) alertRepository.delete(alert);
    		List<Stock> stocks = stockRepository.findByDrug(drug);
    		for (Stock s : stocks) {
    			stockRepository.delete(s);
    		}
        	if (user != null) userRepository.delete(user);
        	if (drug != null) drugRepository.delete(drug);
    	}
	}

	private Stock createStock(User user, Drug drug) {
		Stock stock = new Stock(new Date(), 22, StockType.ORDER, user, drug);
    	return stock;
	}
	
	private Alert createAndSaveAlert(Integer level, User user, Drug drug) {
    	Alert alert = new Alert(new Date(), level, "Testing Alerts", AlertStatus.NEW, user, drug);
    	return alertRepository.save(alert);
	}
	
	private User createAndSaveUser(String msisdn) {
		User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
    	return userRepository.save(user);
	}
	
	private Drug createAndSaveDrug(String barcode) {
		Drug drug = new Drug(barcode, "Disprin");
    	return drugRepository.save(drug);
	}
}
