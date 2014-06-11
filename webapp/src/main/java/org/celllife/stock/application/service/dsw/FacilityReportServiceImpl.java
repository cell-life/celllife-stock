package org.celllife.stock.application.service.dsw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.celllife.stock.domain.stock.Stock;
import org.celllife.stock.domain.stock.StockRepository;
import org.celllife.stock.domain.stock.StockStatus;
import org.celllife.stock.domain.stock.StockType;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.celllife.stock.integration.dsw.DrugStockWarehouseService;
//import org.celllife.stock.integration.dsw.DrugStockWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacilityReportServiceImpl implements FacilityReportService {
	
	private static Logger log = LoggerFactory.getLogger(FacilityReportServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	DrugStockWarehouseService dswService;

	@Override
	@Scheduled(cron = "${report.stocktake.cron}")
	public void runStockTakeReport() {
		runReport(StockType.ORDER);
	}

	@Override
	@Scheduled(cron = "${report.stockarrival.cron}")
	public void runStockArrivalReport() {
		runReport(StockType.RECEIVED);
	}

	@Transactional
	void runReport(StockType type) {
		log.info("Running report for type "+type);
		// get all new stock takes
		List<Stock> stock = stockRepository.findNewByType(type);
		// submit each stock report (submitted by user/date)
		Map<User, List<Stock>> stockByUser = getStockByUser(stock);
		for (User u : stockByUser.keySet()) {
			List<Stock> stockForUser = stockByUser.get(u);
			Map<Date, List<Stock>> stockByDate = getStockByDate(stockForUser);
			for (Date d : stockByDate.keySet()) {
				List<Stock> stockForDate = stockByDate.get(d);
				boolean success = false;
				if (u.isActivated()) {
    				if (type == StockType.ORDER) {
    					success = dswService.sendStockTakes(u, stockForDate);
    				} else {
    					success = dswService.sendStockReceived(u, stockForDate);
    				}
				} else {
				    success = dswService.sendActivation(u, stockForDate);
				    if (success) {
				        u.setActivated(true);
				        userRepository.save(u);
				    } else {
				        // if the activation failed, then don't continue with stocks for this user
				        log.warn("Could not activate user "+u.getMsisdn()+", so continuing with the next user.");
				        break;
				    }
				}
				// mark as success or failure
				if (success) {
					log.info(type+" report for user "+u.getMsisdn()+" on "+d+" was successful");
					for (Stock s : stockForDate) {
						s.setStatus(StockStatus.SENT);
					}
				} else {
					log.info(type+" report for user "+u.getMsisdn()+" on "+d+" was unsuccessful");
					for (Stock s : stockForDate) {
						s.setStatus(StockStatus.FAILED);
					}
				}
				stockRepository.save(stockForDate);
			}
		}
	}

	Map<User, List<Stock>> getStockByUser(List<Stock> stock) {
		Map<User, List<Stock>> stockByUserMap = new HashMap<User, List<Stock>>();
		for (Stock s : stock) {
			User u = s.getUser();
			List<Stock> stockForUser = stockByUserMap.get(u);
			if (stockForUser == null) {
				stockForUser = new ArrayList<Stock>();
				stockByUserMap.put(u, stockForUser);
			}
			stockForUser.add(s);
		}
		return stockByUserMap;
	}
	
	Map<Date, List<Stock>> getStockByDate(List<Stock> stock) {
		Map<Date, List<Stock>> stockByDate = new HashMap<Date, List<Stock>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (Stock s : stock) {
			Date stockDate;
			try {
				stockDate = sdf.parse(sdf.format(s.getDate()));  // convert the stock's date to yyyyMMdd
				List<Stock> stockOnDay = stockByDate.get(stockDate);
				if (stockOnDay == null) {
					stockOnDay = new ArrayList<Stock>();
					stockByDate.put(stockDate, stockOnDay);
				}
				stockOnDay.add(s);
			} catch (ParseException e) {
				// this exception shouldn't happen
				log.error("Problem parsing the date for stock "+s, e);
			}
		}
		return stockByDate;
	}
	
	@Override
	public void setDrugStockWarehouseService(DrugStockWarehouseService dswService) {
		this.dswService = dswService;
	}
}
