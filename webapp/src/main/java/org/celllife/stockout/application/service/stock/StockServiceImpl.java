package org.celllife.stockout.application.service.stock;

import org.celllife.stockout.domain.alert.Alert;
import org.celllife.stockout.domain.alert.AlertRepository;
import org.celllife.stockout.domain.alert.AlertStatus;
import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.drug.DrugRepository;
import org.celllife.stockout.domain.exception.StockOutException;
import org.celllife.stockout.domain.stock.Stock;
import org.celllife.stockout.domain.stock.StockDto;
import org.celllife.stockout.domain.stock.StockRepository;
import org.celllife.stockout.domain.stock.StockType;
import org.celllife.stockout.domain.user.User;
import org.celllife.stockout.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public 	StockDto getStock(Long id) {
		Stock stock = stockRepository.findOne(id);
		if (stock != null) {
			return new StockDto(stock);
		} else {
			return null;
		}
	}

	private User getUser(StockDto stock) {
		if (stock.getUser() == null) {
			throw new StockOutException("No user specified for stock. "+stock);
		}
		User user = userRepository.findOneByMsisdn(stock.getUser().getMsisdn());
		if (user == null) {
			throw new StockOutException("Could not find user with msisdn '"+stock.getUser().getMsisdn()+"'.");
		}
		return user;
	}

	private Drug getDrug(StockDto stock) {
		if (stock.getDrug() == null) {
			throw new StockOutException("No drug specified for stock. "+stock);
		}
		Drug drug = drugRepository.findOneByBarcode(stock.getDrug().getBarcode());
		if (drug == null) {
			throw new StockOutException("Could not find drug with barcode '"+stock.getDrug().getBarcode()+"'.");
		}
		return drug;
	}
	
	private Stock convert(StockDto stock, User user, Drug drug) {
		Stock newStock = new Stock(stock.getDate(), stock.getQuantity(), stock.getType(), user, drug);
		return newStock;
	}
}
