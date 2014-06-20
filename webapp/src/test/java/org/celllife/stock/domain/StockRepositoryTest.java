package org.celllife.stock.domain;

import java.util.Calendar;
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
import org.celllife.stock.test.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class StockRepositoryTest {
	
    @Autowired
    private DrugRepository drugRepository;

	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private StockRepository stockRepository;

    @Test
    public void testCreateOne() throws Exception {
    	Stock savedStock = null;
    	User savedUser = null;
    	Drug savedDrug = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	savedUser = userRepository.save(user);
	    	
	    	String barcode = "0762837491";
	    	Drug drug = new Drug(barcode, "Disprin");
	    	savedDrug = drugRepository.save(drug);

	    	Calendar cal = Calendar.getInstance();
	    	Stock stock = new Stock(cal.getTime(), 10, StockType.ORDER, savedUser, savedDrug);
	    	savedStock = stockRepository.save(stock);
	    	
	    	setTimeToStart(cal);
	    	Date startDate = cal.getTime();
	    	
	    	setTimeToEnd(cal);
	    	Date endDate = cal.getTime();
	    	
	    	List<Stock> allStock = stockRepository.findByDateBetweenByUserAndType(startDate, endDate, user, StockType.ORDER);
	    	Assert.assertNotNull(allStock);
	    	Assert.assertEquals(1, allStock.size());
	    	Assert.assertEquals(savedStock, allStock.get(0));

    	} finally {
    		if (savedStock != null) stockRepository.delete(savedStock);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug != null) drugRepository.delete(savedDrug);
    	}    	
    }

    @Test
    public void testCreateMany() throws Exception {
    	Stock savedStock1 = null;
    	Stock savedStock2 = null;
    	Stock savedStock3 = null;
    	Stock savedStock4 = null;
    	User savedUser = null;
    	Drug savedDrug = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	savedUser = userRepository.save(user);
	    	
	    	String barcode = "0762837491";
	    	Drug drug = new Drug(barcode, "Disprin");
	    	savedDrug = drugRepository.save(drug);

	    	Calendar cal = Calendar.getInstance();

	    	Stock stock1 = new Stock(cal.getTime(), 10, StockType.ORDER, savedUser, savedDrug);
	    	savedStock1 = stockRepository.save(stock1);

	    	Stock stock2 = new Stock(cal.getTime(), 20, StockType.RECEIVED, savedUser, savedDrug);
	    	savedStock2 = stockRepository.save(stock2);

	    	setTimeToStart(cal);
	    	Date startDate = cal.getTime();

	    	cal.add(Calendar.DATE, 2);
	    	
	    	Stock stock3 = new Stock(cal.getTime(), 30, StockType.ORDER, savedUser, savedDrug);
	    	savedStock3 = stockRepository.save(stock3);
	    	
	    	Stock stock4 = new Stock(cal.getTime(), 40, StockType.RECEIVED, savedUser, savedDrug);
	    	savedStock4 = stockRepository.save(stock4);
	    	
	    	setTimeToEnd(cal);
	    	Date endDate = cal.getTime();
	    	
	    	List<Stock> allStock = stockRepository.findByDateBetweenByUserAndType(startDate, endDate, user, StockType.RECEIVED);
	    	Assert.assertNotNull(allStock);
	    	Assert.assertEquals(2, allStock.size());
	    	Assert.assertTrue(allStock.contains(savedStock2));
	    	Assert.assertTrue(allStock.contains(savedStock4));

    	} finally {
    		if (savedStock1 != null) stockRepository.delete(savedStock1);
    		if (savedStock2 != null) stockRepository.delete(savedStock2);
    		if (savedStock3 != null) stockRepository.delete(savedStock3);
    		if (savedStock4 != null) stockRepository.delete(savedStock4);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug != null) drugRepository.delete(savedDrug);
    	}    	
    }

    @Test
    public void testFindByStatus() throws Exception {
    	Stock savedStock1 = null;
    	Stock savedStock2 = null;
    	Stock savedStock3 = null;
    	Stock savedStock4 = null;
    	User savedUser = null;
    	Drug savedDrug = null;
    	
    	try {
	    	String msisdn = "0762837491";
	    	User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
	    	savedUser = userRepository.save(user);
	    	
	    	String barcode = "0762837491";
	    	Drug drug = new Drug(barcode, "Disprin");
	    	savedDrug = drugRepository.save(drug);
	    	System.out.println("saved="+savedDrug);

	    	Calendar cal = Calendar.getInstance();

	    	Stock stock1 = new Stock(cal.getTime(), 10, StockType.ORDER, savedUser, savedDrug);
	    	stock1.setStatus(StockStatus.FAILED);
	    	savedStock1 = stockRepository.save(stock1);

	    	Stock stock2 = new Stock(cal.getTime(), 20, StockType.ORDER, savedUser, savedDrug);
	    	stock2.setStatus(StockStatus.SENT);
	    	savedStock2 = stockRepository.save(stock2);

	    	cal.add(Calendar.DATE, 2);
	    	
	    	Stock stock3 = new Stock(cal.getTime(), 30, StockType.ORDER, savedUser, savedDrug);
	    	savedStock3 = stockRepository.save(stock3);
	    	
	    	Stock stock4 = new Stock(cal.getTime(), 40, StockType.ORDER, savedUser, savedDrug);
	    	savedStock4 = stockRepository.save(stock4);
	    	
	    	List<Stock> allStock = stockRepository.findNewByType(StockType.RECEIVED);
	    	Assert.assertNotNull(allStock);
	    	Assert.assertEquals(0, allStock.size());
	    	
	    	allStock = stockRepository.findNewByType(StockType.ORDER);
	    	Assert.assertNotNull(allStock);
	    	Assert.assertEquals(2, allStock.size());
	    	Assert.assertTrue(allStock.contains(savedStock3));
	    	Assert.assertTrue(allStock.contains(savedStock4));


	    	allStock = stockRepository.findFailedByType(StockType.ORDER);
	    	Assert.assertNotNull(allStock);
	    	Assert.assertEquals(1, allStock.size());
	    	Assert.assertTrue(allStock.contains(savedStock1));
	    	
    	} finally {
    		if (savedStock1 != null) stockRepository.delete(savedStock1);
    		if (savedStock2 != null) stockRepository.delete(savedStock2);
    		if (savedStock3 != null) stockRepository.delete(savedStock3);
    		if (savedStock4 != null) stockRepository.delete(savedStock4);
        	if (savedUser != null) userRepository.delete(savedUser);
        	if (savedDrug != null) drugRepository.delete(savedDrug);
    	}    	
    }

    @Test
    public void testFindSentByTypeAndUserAndDateAlreadySent() throws Exception {
        Stock savedStock1 = null;
        User savedUser = null;
        Drug savedDrug = null;
        
        try {
            String msisdn = "0762837491";
            User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
            savedUser = userRepository.save(user);
            
            String barcode = "0762837491";
            Drug drug = new Drug(barcode, "Disprin");
            savedDrug = drugRepository.save(drug);

            Stock stock1 = new Stock(new Date(), 10, StockType.ORDER, savedUser, savedDrug);
            stock1.setStatus(StockStatus.SENT);
            savedStock1 = stockRepository.save(stock1);
            
            Calendar cal = Calendar.getInstance();
            setTimeToStart(cal);
            Date start = cal.getTime();
            setTimeToEnd(cal);
            Date end = cal.getTime();
            
            List<Stock> allStock = stockRepository.findSentByTypeAndUserAndDateBetween(StockType.ORDER, user, start, end);
            Assert.assertNotNull(allStock);
            Assert.assertEquals(1, allStock.size());
            
        } finally {
            if (savedStock1 != null) stockRepository.delete(savedStock1);
            if (savedUser != null) userRepository.delete(savedUser);
            if (savedDrug != null) drugRepository.delete(savedDrug);
        }       
    }

    @Test
    public void testFindSentByTypeAndUserAndDateNotAlreadySent() throws Exception {
        Stock savedStock1 = null;
        User savedUser = null;
        Drug savedDrug = null;
        
        try {
            String msisdn = "0762837491";
            User user = new User(msisdn, "jsdfklllllll", "ssss", "0000", "Demo Clinic 1");
            savedUser = userRepository.save(user);
            
            String barcode = "0762837491";
            Drug drug = new Drug(barcode, "Disprin");
            savedDrug = drugRepository.save(drug);

            Stock stock1 = new Stock(new Date(), 10, StockType.ORDER, savedUser, savedDrug);
            stock1.setStatus(StockStatus.NEW);
            savedStock1 = stockRepository.save(stock1);
            
            Calendar cal = Calendar.getInstance();
            setTimeToStart(cal);
            Date start = cal.getTime();
            setTimeToEnd(cal);
            Date end = cal.getTime();
            
            List<Stock> allStock = stockRepository.findSentByTypeAndUserAndDateBetween(StockType.ORDER, user, start, end);

            Assert.assertNotNull(allStock);
            Assert.assertEquals(0, allStock.size());
            
        } finally {
            if (savedStock1 != null) stockRepository.delete(savedStock1);
            if (savedUser != null) userRepository.delete(savedUser);
            if (savedDrug != null) drugRepository.delete(savedDrug);
        }       
    }

	private void setTimeToEnd(Calendar cal) {
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);
	}

	private void setTimeToStart(Calendar cal) {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}
}

