package org.celllife.stock.application.service.stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.domain.alert.AlertRepository;
import org.celllife.stock.domain.alert.AlertStatus;
import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.exception.StockException;
import org.celllife.stock.domain.stock.Stock;
import org.celllife.stock.domain.stock.StockDto;
import org.celllife.stock.domain.stock.StockRepository;
import org.celllife.stock.domain.stock.StockType;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements StockService {
	
	private static Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	AlertRepository alertRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DrugRepository drugRepository;

	@Override
	@Transactional
	public StockDto createStockTake(StockDto stock) {
		User user = getUser(stock);
		Drug drug = getDrug(stock);

		Stock newStock = convert(stock, user, drug);
		newStock.setType(StockType.ORDER);
		Stock savedStock = stockRepository.save(newStock);

		// update latest alert to be response (latest alert is only status NEW or SENT)
		Alert oldAlert = alertRepository.findOneLatestByUserAndDrug(user, drug);
		if (oldAlert != null) {
			if (log.isDebugEnabled()) {
				log.debug("Alert status is now RESOLVED due to stock take '"+savedStock+"' for " +
						"user '"+user.getMsisdn()+"' in clinic '"+user.getClinicName()+"' for " +
								"drug '"+drug.getDescription()+"'");
			}
			oldAlert.setStatus(AlertStatus.RESOLVED);
			alertRepository.save(oldAlert);
		}

		return new StockDto(savedStock);
	}

	@Override
	@Transactional
	public StockDto createStockArrival(StockDto stock) {
		User user = getUser(stock);
		Drug drug = getDrug(stock);

		Stock newStock = convert(stock, user, drug);
		newStock.setType(StockType.RECEIVED);
		Stock savedStock = stockRepository.save(newStock);

		// update latest alert to be response (latest alert is only RESPONSE)
		Alert oldAlert = alertRepository.findOneLatestByUserAndDrug(user, drug);
		if (oldAlert != null) {
			if (log.isDebugEnabled()) {
				log.debug("Alert status is now CLOSED due to stock arrival '"+savedStock+"' for " +
						"user '"+user.getMsisdn()+"' in clinic '"+user.getClinicName()+"' for " +
								"drug '"+drug.getDescription()+"'");
			}
			oldAlert.setStatus(AlertStatus.CLOSED);
			alertRepository.save(oldAlert);
		}

		return new StockDto(savedStock);
	}

	@Transactional(readOnly = true)
	public 	StockDto getStock(Long id) {
		Stock stock = stockRepository.findOne(id);
		if (stock != null) {
			return new StockDto(stock);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Set<StockDto> getTodayStockTake(String msisdn) {
		return getTodayStock(msisdn, StockType.ORDER);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<StockDto> getTodayStockArrival(String msisdn) {
		return getTodayStock(msisdn, StockType.RECEIVED);
	}

	private Set<StockDto> getTodayStock(String msisdn, StockType type) {
		List<Stock> stocks = new ArrayList<Stock>();
		if (msisdn != null && !msisdn.isEmpty()) {
			User user = userRepository.findOneByMsisdn(msisdn);
			if (user == null) {
				throw new StockException("Could not find user with msisdn '"+msisdn+"'.");
			}
			Date start = getBeginningOfToday();
			Date end = getEndOfToday();
			log.debug("Getting today's stock "+type+" between "+start+" and "+end+" for msisdn "+msisdn);
			stocks = stockRepository.findByDateBetweenByUserAndType(
					start, end, user, type);
		} else {
			// find all stocks
			Date start = getBeginningOfToday();
			Date end = getEndOfToday();
			log.debug("Getting today's stock "+type+" between "+start+" and "+end);
			stocks = stockRepository.findByDateBetweenAndType(
					start, end, type);
		}
		Set<StockDto> stockDtos = new HashSet<StockDto>();
		for (Stock s : stocks) {
			StockDto dto = new StockDto(s);
			stockDtos.add(dto);
		}
		return stockDtos;
	}

	private Date getBeginningOfToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private Date getEndOfToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);
		return cal.getTime();
	}

	private User getUser(StockDto stock) {
		if (stock.getUser() == null) {
			throw new StockException("No user specified for stock. "+stock);
		}
		User user = userRepository.findOneByMsisdn(stock.getUser().getMsisdn());
		if (user == null) {
			throw new StockException("Could not find user with msisdn '"+stock.getUser().getMsisdn()+"'.");
		}
		return user;
	}

	private Drug getDrug(StockDto stock) {
		if (stock.getDrug() == null) {
			throw new StockException("No drug specified for stock. "+stock);
		}
		Drug drug = drugRepository.findOneByBarcode(stock.getDrug().getBarcode());
		if (drug == null) {
			throw new StockException("Could not find drug with barcode '"+stock.getDrug().getBarcode()+"'.");
		}
		return drug;
	}
	
	private Stock convert(StockDto stock, User user, Drug drug) {
		Stock newStock = new Stock(stock.getDate(), stock.getQuantity(), stock.getType(), user, drug);
		return newStock;
	}
}
