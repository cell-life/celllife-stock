package org.celllife.stock.interfaces.service;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stock.application.service.stock.StockService;
import org.celllife.stock.domain.alert.AlertSummaryDto;
import org.celllife.stock.domain.exception.StockException;
import org.celllife.stock.domain.stock.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service/stocks")
public class StockController {

	@Autowired
	StockService stockService;

	@Value("${external.base.url}") 
	String baseUrl;

	@ResponseBody
    @RequestMapping(value = "{stockId}", method = RequestMethod.GET, produces = "application/json")
    public StockDto getStock(@PathVariable("stockId") Long stockId, HttpServletResponse response) throws IOException {
		try {
	        StockDto stock = stockService.getStock(stockId);
	        if (stock == null) {
	        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        	return null;
	        } else {
	        	return stock;
	        }
		} catch (StockException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
    }

	@ResponseBody
	@RequestMapping(value = "/stocktake/summary", method = RequestMethod.GET, produces = "application/json")
	public Set<StockDto> getTodayStockTakes(@RequestParam(value="msisdn", required=false) String msisdn, HttpServletResponse response) throws IOException {
		return stockService.getTodayStockTake(msisdn);
	}

	@ResponseBody
	@RequestMapping(value = "/stockarrival/summary", method = RequestMethod.GET, produces = "application/json")
	public Set<StockDto> getTodayStockArrivals(@RequestParam(value="msisdn", required=false) String msisdn, HttpServletResponse response) throws IOException {
		return stockService.getTodayStockArrival(msisdn);
	}
	
	@RequestMapping(value = "/stocktake", method = RequestMethod.POST)
	public void saveStockTake(@RequestBody StockDto stock, HttpServletResponse response) throws IOException {
		try {
			StockDto newStock= stockService.createStockTake(stock);
			response.setHeader("Location", baseUrl+"/service/stocks/"+newStock.getId());
	        response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (StockException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = "/stockarrival", method = RequestMethod.POST)
	public void saveStockArrival(@RequestBody StockDto stock, HttpServletResponse response) throws IOException {
		try {
			StockDto newStock= stockService.createStockArrival(stock);
			response.setHeader("Location", baseUrl+"/service/stocks/"+newStock.getId());
	        response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (StockException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
