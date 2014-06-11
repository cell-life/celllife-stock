package org.celllife.stock.integration.dsw

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException

import java.text.SimpleDateFormat

import org.celllife.stock.domain.drug.Drug
import org.celllife.stock.domain.stock.Stock
import org.celllife.stock.domain.stock.StockType
import org.celllife.stock.domain.user.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service


/**
 * See: http://drug-stock-preprod.jembi.org:9000/index.html?raml=raml/drug-stock-him.raml
 */
@Service
class DrugStockWarehouseServiceImpl implements DrugStockWarehouseService {

	private static Logger log = LoggerFactory.getLogger(DrugStockWarehouseServiceImpl.class);

	private static String URL_STOCKTAKE = "pharmacies/{id}/stocktakes/{date}";
	private static String URL_STOCKARRIVALS = "pharmacies/{id}/stockarrivals/{date}";
	private static String URL_ACTIVATION = "pharmacies/{id}/activation";
	private static String URL_DRUGS = "drugs";

	private static String DATE_FORMAT = "yyyyMMdd";

	@Value('${dsw.api}')
	private String apiUrl;

	@Value('${dsw.username}')
	private String username

	@Value('${dsw.password}')
	private String password


	@Override
	public boolean sendStockTakes(User user, List<Stock> stock) {
		/*{
			"stockLevels": [
				{
					"drugCode": "DRUG-1",
					"drugCodeType": "NAPPI",
					"level": 1000
				},
				{
					"drugCode": "DRUG-2",
					"drugCodeType": "NAPPI",
					"level": 2000
				}
			]
		}*/
		return sendStock(user, stock, StockType.ORDER);
	}

	@Override
	public boolean sendStockReceived(User user, List<Stock> stock) {
		/*{
			"stockArrived": [
				{
					"drugCode": "DRUG-1",
					"drugCodeType": "NAPPI",
					"quantity": 500
				},
				{
					"drugCode": "DRUG-2",
					"drugCodeType": "NAPPI",
					"quantity": 1000
				}
			]
		}*/
		return sendStock(user, stock, StockType.RECEIVED);
	}
	
	@Override
	public boolean sendActivation(User user, List<Stock> stock) {
		String todayDate = new SimpleDateFormat(DATE_FORMAT).format(stock.get(0).getDate());
		String url = URL_ACTIVATION.replace("{id}", user.getClinicCode());
		// create the request
		Map<String, Object> activationMap = new HashMap<String, Object>();
        activationMap.put("leadTime", user.getLeadTime());
        // FIXME: need to send safetyLevel too....
		List<Map<String, Object>> stockMap = new ArrayList<Map<String, Object>>();
		for (Stock s : stock) {
			stockMap.add(convertStock(s));
		}
		activationMap.put("stockLevels", stockMap);
		/*{
			"leadTime": 5,
			"stockLevels": [
				{
					"drugCode": "DRUG-1",
					"drugCodeType": "NAPPI",
					"level": 1000
				},
				{
					"drugCode": "DRUG-2",
					"drugCodeType": "NAPPI",
					"level": 2000
				}
			]
		}*/
		return post(url, activationMap);
	}

	@Async("defaultTaskExecutor")
	@Override
	public boolean createDrug(Drug drug) {
		Map<String, Object> drugMap = new HashMap<String, Object>();
		drugMap.put("barcode", drug.getBarcode());
		drugMap.put("name", drug.getName());
		drugMap.put("description", drug.getDescription());
		List<Map<String, Object>> ids = new ArrayList<Map<String, Object>>();
		Map<String, Object> idMap = new HashMap<String, Object>();
		idMap.put("drugCode", drug.getBarcode())
		idMap.put("drugCodeType", "EAN-13");
		ids.add(idMap);
		drugMap.put("identifiers", ids);
		/*{
			"name": "Panado",
			"description": "This is Panado",
			"identfiers": [
				{
					"drugCode": "PANADO",
					"drugCodeType": "NAPPI"
				}
			],
			"container": "box",
			"ingredient": "paracetamol",
			"manufacturer": "The people that make Panado",
			"form": "pill",
			"barcode": "1234"
		}*/
		return post(URL_DRUGS, drugMap);
	}
	
	private boolean sendStock(User user, List<Stock> stock, StockType type) {
		if (stock == null || stock.isEmpty()) {
			return true;
		}
		boolean status = sendStock(user, stock);
		return status;
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
			return post(url, stockMap);
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
			client.handler.'409' = { resp ->
				println "Duplicate" 
			}
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("requestContentType", ContentType.JSON);
			body.put("body", data);
			def response = client.post(body)/* {
				response.'409' = { resp ->
					println 'duplicate'
				}
			}*/
			log.debug("POST returned status "+response.status);
			return true;
		} catch (HttpResponseException e) {
			if (e.getStatusCode() == 409) {
				// if the document already exists or has been created, return a success
				return true;
			} else {
				log.error("Could not send stock. Status="+e.getStatusCode(), e)
				log.error("URL="+url);
				log.error("Data="+data);
			}
		} catch (Throwable t) {
			log.error("Could not send stock.", t)
			log.error("URL="+url);
			log.error("Data="+data);
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