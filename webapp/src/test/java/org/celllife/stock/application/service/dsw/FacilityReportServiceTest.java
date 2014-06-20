package org.celllife.stock.application.service.dsw;


import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.stock.Stock;
import org.celllife.stock.domain.stock.StockRepository;
import org.celllife.stock.domain.stock.StockStatus;
import org.celllife.stock.domain.stock.StockType;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.celllife.stock.integration.dsw.DrugStockWarehouseService;
import org.celllife.stock.test.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class FacilityReportServiceTest {

	// service being tested
	@Autowired
	FacilityReportService facilityReportService;

	// repositories used during the test to setup the DB
    @Autowired
    private DrugRepository drugRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;
    
    @Before
    public void setup() {
    	
    }
	
	@Test
	public void testStockTakeReport() {
		User user = null;
		Drug drug = null;
		Stock stock = null;
		
		try {
			final User u = createAndSaveUser("0738847292", true);
			user = u;
			drug = createAndSaveDrug("1112223333");
			final Stock s = createAndSaveStock(user, drug, StockType.ORDER, StockStatus.NEW);
			stock = s;
			
			facilityReportService.setDrugStockWarehouseService(new DrugStockWarehouseService() {

				@Override
				public boolean sendStockTakes(User user, List<Stock> stock, Boolean update) {
					Assert.assertEquals(u, user);
					Assert.assertNotNull(stock);
					Assert.assertTrue(stock.contains(s));
					Assert.assertFalse(update);
					return true;
				}

				@Override
				public boolean sendStockReceived(User user, List<Stock> stock, Boolean update) {
					Assert.fail();
					return true;
				}

				@Override
				public boolean sendActivation(User user, List<Stock> stock) {
					Assert.fail();
					return true;
				}

				@Override
				public boolean createDrug(Drug drug) {
					Assert.fail();
					return true;
				}				
			});
			
			facilityReportService.runStockTakeReport();
			Stock savedStock = stockRepository.findOne(stock.getId());
			Assert.assertNotNull(savedStock);
			Assert.assertEquals(StockStatus.SENT, savedStock.getStatus());
			
    	} finally {
    		if (stock != null) stockRepository.delete(stock);
        	if (user != null) userRepository.delete(user);
        	if (drug != null) drugRepository.delete(drug);
    	}
	}
	
    @Test
    public void testStockTakeReportDuplicate() {
        User user = null;
        Drug drug1 = null;
        Drug drug2 = null;
        Stock stock1 = null;
        Stock stock2 = null;
        
        try {
            final User u = createAndSaveUser("0738847292", true);
            user = u;
            drug1 = createAndSaveDrug("1112223333");
            drug2 = createAndSaveDrug("1112223334");
            stock1 = createAndSaveStock(user, drug1, StockType.ORDER, StockStatus.SENT);
            stock2 = createAndSaveStock(user, drug2, StockType.ORDER, StockStatus.NEW);
            final Stock s = stock2;
            
            facilityReportService.setDrugStockWarehouseService(new DrugStockWarehouseService() {

                @Override
                public boolean sendStockTakes(User user, List<Stock> stock, Boolean update) {
                    Assert.assertEquals(u, user);
                    Assert.assertNotNull(stock);
                    Assert.assertTrue(stock.contains(s));
                    Assert.assertTrue(update);
                    return true;
                }

                @Override
                public boolean sendStockReceived(User user, List<Stock> stock, Boolean update) {
                    Assert.fail();
                    return true;
                }

                @Override
                public boolean sendActivation(User user, List<Stock> stock) {
                    Assert.fail();
                    return true;
                }

                @Override
                public boolean createDrug(Drug drug) {
                    Assert.fail();
                    return true;
                }               
            });
            
            facilityReportService.runStockTakeReport();
            Stock savedStock = stockRepository.findOne(stock1.getId());
            Assert.assertNotNull(savedStock);
            Assert.assertEquals(StockStatus.SENT, savedStock.getStatus());
            
        } finally {
            if (stock1 != null) stockRepository.delete(stock1);
            if (stock2 != null) stockRepository.delete(stock2);
            if (user != null) userRepository.delete(user);
            if (drug1 != null) drugRepository.delete(drug1);
            if (drug2 != null) drugRepository.delete(drug2);
        }
    }

	@Test
	public void testStockArrivalReport() {
		User user = null;
		Drug drug = null;
		Stock stock = null;
		
		try {
			final User u = createAndSaveUser("0738847292", true);
			user = u;
			drug = createAndSaveDrug("1112223333");
			final Stock s = createAndSaveStock(user, drug, StockType.RECEIVED, StockStatus.NEW);
			stock = s;
			
			facilityReportService.setDrugStockWarehouseService(new DrugStockWarehouseService() {

				@Override
				public boolean sendStockTakes(User user, List<Stock> stock, Boolean update) {
					Assert.fail();
					return true;
				}

				@Override
				public boolean sendStockReceived(User user, List<Stock> stock, Boolean update) {
					Assert.assertEquals(u, user);
					Assert.assertNotNull(stock);
					Assert.assertTrue(stock.contains(s));
					Assert.assertFalse(update);
					return true;
				}

				@Override
				public boolean sendActivation(User user, List<Stock> stock) {
					Assert.fail();
					return true;
				}

				@Override
				public boolean createDrug(Drug drug) {
					Assert.fail();
					return true;
				}				
			});
			
			facilityReportService.runStockArrivalReport();
			
			Stock savedStock = stockRepository.findOne(stock.getId());
			Assert.assertNotNull(savedStock);
			Assert.assertEquals(StockStatus.SENT, savedStock.getStatus());
			
    	} finally {
    		if (stock != null) stockRepository.delete(stock);
        	if (user != null) userRepository.delete(user);
        	if (drug != null) drugRepository.delete(drug);
    	}
	}
	
	@Test
	public void testStockTakeReportFails() {
		User user = null;
		Drug drug = null;
		Stock stock = null;
		
		try {
			final User u = createAndSaveUser("0738847292", true);
			user = u;
			drug = createAndSaveDrug("1112223333");
			final Stock s = createAndSaveStock(user, drug, StockType.ORDER, StockStatus.NEW);
			stock = s;
			
			facilityReportService.setDrugStockWarehouseService(new DrugStockWarehouseService() {

				@Override
				public boolean sendStockTakes(User user, List<Stock> stock, Boolean update) {
					return false;
				}

				@Override
				public boolean sendStockReceived(User user, List<Stock> stock, Boolean update) {
					Assert.fail();
					return true;
				}

				@Override
				public boolean sendActivation(User user, List<Stock> stock) {
					Assert.fail();
					return true;
				}

				@Override
				public boolean createDrug(Drug drug) {
					Assert.fail();
					return true;
				}				
			});
			
			facilityReportService.runStockTakeReport();
			Stock savedStock = stockRepository.findOne(stock.getId());
			Assert.assertNotNull(savedStock);
			Assert.assertEquals(StockStatus.FAILED, savedStock.getStatus());
			
    	} finally {
    		if (stock != null) stockRepository.delete(stock);
        	if (user != null) userRepository.delete(user);
        	if (drug != null) drugRepository.delete(drug);
    	}
	}

    @Test
    public void testClinicActivation() {
        User user = null;
        Drug drug = null;
        Stock stock = null;
        
        try {
            final User u = createAndSaveUser("0738847292", false);
            user = u;
            drug = createAndSaveDrug("1112223333");
            final Stock s = createAndSaveStock(user, drug, StockType.RECEIVED, StockStatus.NEW);
            stock = s;
            
            facilityReportService.setDrugStockWarehouseService(new DrugStockWarehouseService() {

                @Override
                public boolean sendStockTakes(User user, List<Stock> stock, Boolean update) {
                    Assert.fail();
                    return true;
                }

                @Override
                public boolean sendStockReceived(User user, List<Stock> stock, Boolean update) {
                    Assert.fail();
                    return true;
                }

                @Override
                public boolean sendActivation(User user, List<Stock> stock) {
                    Assert.assertEquals(u, user);
                    Assert.assertNotNull(stock);
                    Assert.assertTrue(stock.contains(s));
                    return true;
                }

                @Override
                public boolean createDrug(Drug drug) {
                    Assert.fail();
                    return true;
                }               
            });
            
            facilityReportService.runStockArrivalReport();
            
            Stock savedStock = stockRepository.findOne(stock.getId());
            Assert.assertNotNull(savedStock);
            Assert.assertEquals(StockStatus.SENT, savedStock.getStatus());
            
            User savedUser = userRepository.findOneByMsisdn("0738847292");
            Assert.assertEquals(Boolean.TRUE, savedUser.isActivated());
            
        } finally {
            if (stock != null) stockRepository.delete(stock);
            if (user != null) userRepository.delete(user);
            if (drug != null) drugRepository.delete(drug);
        }
    }

	private Stock createAndSaveStock(User user, Drug drug, StockType type, StockStatus status) {
		Stock stock = new Stock(new Date(), 22, type, user, drug);
		stock.setStatus(status);
    	return stockRepository.save(stock);
	}
	
	private User createAndSaveUser(String msisdn, Boolean activated) {
		User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
		user.setActivated(activated);
    	return userRepository.save(user);
	}
	
	private Drug createAndSaveDrug(String barcode) {
		Drug drug = new Drug(barcode, "Disprin");
    	return drugRepository.save(drug);
	}
}
