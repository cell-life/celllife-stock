package org.celllife.stock.integration.dsw

import groovyx.net.http.ContentType

import java.text.SimpleDateFormat

import org.celllife.stock.domain.stock.Stock
import org.celllife.stock.domain.stock.StockComparator
import org.celllife.stock.domain.stock.StockType
import org.celllife.stock.domain.user.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DrugStockWarehouseServiceImpl implements DrugStockWarehouseService {

	private static Logger log = LoggerFactory.getLogger(DrugStockWarehouseServiceImpl.class);

	private static String URL_STOCKTAKE = "pharmacies/{id}/stocktakes/{date}";
	private static String URL_STOCKARRIVALS = "pharmacies/{id}/stockarrivals/{date}";
	private static String URL_ACTIVATION = "pharmacies/{id}/activation";

	private static String DATE_FORMAT = "yyyyMMdd";

	@Value('${dsw.api}')
	private String apiUrl;

	@Value('${dsw.username}')
	private String username

	@Value('${dsw.password}')
	private String password


	@Override
	public boolean sendStockTakes(User user, List<Stock> stock) {
		sendStock(user, stock, StockType.ORDER);
	}

	@Override
	public boolean sendStockReceived(User user, List<Stock> stock) {
		sendStock(user, stock, StockType.RECEIVED);
	}
	
	@Override
	public boolean sendActivation(User user, List<Stock> stock) {
		String todayDate = new SimpleDateFormat(DATE_FORMAT).format(stock.get(0).getDate());
		String url = URL_ACTIVATION.replace("{id}", user.getClinicCode());
		// create the request
		Map<String, Object> activationMap = new HashMap<String, Object>();
		// FIXME: need to implement device setup and get this information from the phone
		activationMap.put("leadTime", 5);
		List<Map<String, Object>> stockMap = new ArrayList<Map<String, Object>>();
		for (Stock s : stock) {
			stockMap.add(convertStock(s));
		}
		activationMap.put("stockLevels", stockMap);
		return post(url, activationMap);
	}
	
	private boolean sendStock(User user, List<Stock> stock, StockType type) {
		if (stock == null || stock.isEmpty()) {
			return true;
		}
		boolean status  = false;
		if (stock.size() == 1) {
			// only got one to send
			Stock s = stock.get(0);
			if (s.getType() == type) {
				status = sendStock(stock);
			}
		} else {
			// go through each date
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Calendar cal = Calendar.getInstance();
			Collections.sort(stock, new StockComparator());
			Date firstDate = stock.get(0).getDate();
			Integer date1 = Integer.valueOf(sdf.format(firstDate));
			Date lastDate = stock.get(stock.size()-1).getDate();
			Integer date2 = Integer.valueOf(sdf.format(lastDate));
			Integer date3 = date1;
			while (date3 >= date2) {
				// now send the stock
				List<Stock> thisDayStock = filterStockList(stock, type, cal.getTime());
				status = sendStock(user, thisDayStock);
				if (!status) {
					log.warn("Could not send stock for date "+cal.getTime()+" so aborting other data. "+thisDayStock);
					break;
				}
				// now go to the previous date
				cal.add(Calendar.DATE, -1);
				date3 = Integer.valueOf(sdf.format(cal.getTime()));
			}
		}
		return status;
	}

	private List<Stock> filterStockList(List<Stock> stock, StockType type, Date date) {
		List<Stock> stocks = new ArrayList<Stock>();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String todayDate = sdf.format(date);
		for (Stock s : stock) {
			if (s.getType() == type && sdf.format(s.getDate()).equals(todayDate)) {
				stocks.add(s)
			}
		}
		return stocks;
	}

	private boolean sendStock(User user, List<Stock> stock) {
		if (stock == null || stock.isEmpty()) {
			return true;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String todayDate = sdf.format(stock.get(0).getDate());
		if (stock.size() > 0) {
			// create the request
			Stock s = stock.get(0);
			Map<String, Object> stockMap = new HashMap<String, Object>();
			String url = null;
			String mapName = null;
			if (s.getType() == StockType.RECEIVED) {
				url = URL_STOCKARRIVALS.replace("{id}", user.getClinicCode()).replace("{date}", todayDate);
				mapName = "stockArrived";
			} else {
				url = URL_STOCKTAKE.replace("{id}", user.getClinicCode()).replace("{date}", todayDate);
				mapName = "stockLevels";
			}
			List<Map<String, Object>> stockList = new ArrayList<Map<String, Object>>();
			for (Stock st : stock) {
				stockList.add(convertStock(st));
			}
			stockMap.put(mapName, stockList);
			post(url, stockMap);
		} else {
			// no stock to send, so nothing to go wrong!
			return true;
		}
		return false;
	}

	private boolean post(String url, Map<String, Object> data) {
		try {
			// post the data
			if (apiUrl.endsWith("/")) {
				apiUrl = apiUrl.substring(0, apiUrl.length()-1);
			}
			url = apiUrl + "/" + url;
			log.debug("POST data "+data+" to url "+url);
			def client = new groovyx.net.http.RESTClient(url)
			client.auth.basic(username, password)
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("requestContentType", ContentType.JSON);
			body.put("body", data);
			def response = client.post(body)
			log.debug("POST returned status "+response.status);
			if (response.status == 409 || response.status == 201) {
				// if the document already exists or has been created, return a success
				return true;
			}
		} catch (Throwable t) {
			log.error("Could not send stock.", t)
		}
		return false;
	}

	private Map<String, Object> convertStock(Stock stock) {
		Map<String, Object> stockMap = new HashMap<String, Object>();
		stockMap.put("drugCode", stock.getDrug().getBarcode())
		stockMap.put("drugCodeType", "EAN-13");
		if (stock.getType() == StockType.RECEIVED) {
			stockMap.put("quantity", stock.getQuantity());
		} else {
			stockMap.put("level", stock.getQuantity());
		}
		return stockMap;
	}

	private boolean sameDate(Date date1, Date date2) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return (sdf.format(date1).equals(sdf.format(date2)));
	}
}