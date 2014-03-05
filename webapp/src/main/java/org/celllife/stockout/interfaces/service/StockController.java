package org.celllife.stockout.interfaces.service;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stockout.application.service.stock.StockService;
import org.celllife.stockout.domain.stock.AlertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public AlertDto getStock(@PathVariable("stockId") Long stockId, HttpServletResponse response) {
        AlertDto stock = stockService.getStock(stockId);
        if (stock == null) {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        	return null;
        } else {
        	return stock;
        }
    }
	
	@RequestMapping(value = "/stocktake", method = RequestMethod.POST)
	public void saveStockTake(@RequestBody AlertDto stock, HttpServletResponse response) {
		AlertDto newStock= stockService.createStockTake(stock);
		response.setHeader("Location", baseUrl+"/service/stocks/"+newStock.getId());
        response.setStatus(HttpServletResponse.SC_CREATED);
	}

	@RequestMapping(value = "/stockarrival", method = RequestMethod.POST)
	public void saveStockArrival(@RequestBody AlertDto stock, HttpServletResponse response) {
		AlertDto newStock= stockService.createStockArrival(stock);
		response.setHeader("Location", baseUrl+"/service/stocks/"+newStock.getId());
        response.setStatus(HttpServletResponse.SC_CREATED);
	}
}
