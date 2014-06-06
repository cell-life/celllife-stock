package org.celllife.stock.interfaces.service;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stock.application.service.alert.AlertService;
import org.celllife.stock.domain.alert.AlertDto;
import org.celllife.stock.domain.alert.AlertSummaryDto;
import org.celllife.stock.domain.exception.StockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/service/alerts")
public class AlertController {
    
    private static Logger log = LoggerFactory.getLogger(AlertController.class);

	@Autowired
	AlertService alertService;

	@Value("${external.base.url}") 
	String baseUrl;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<AlertDto> getAlerts(@RequestParam("msisdn") String msisdn) {
		return alertService.getNewAlerts(msisdn);
	}

	@ResponseBody
	@RequestMapping(value = "/summary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<AlertSummaryDto> getAlertSummary() {
		return alertService.getAlertSummary();
	}

	@ResponseBody
    @RequestMapping(value = "{alertId}", method = RequestMethod.GET, produces = "application/json")
    public AlertDto getAlert(@PathVariable("alertId") Long alertId, HttpServletResponse response) throws IOException {
		try {
	        AlertDto alert = alertService.getAlert(alertId);
	        if (alert == null) {
	        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        	return null;
	        } else {
	        	return alert;
	        }
		} catch (StockException e) {
		    log.error("Error while getting alert with id "+alertId, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public void saveAlert(@RequestBody AlertDto alert, HttpServletResponse response) throws IOException {
		try {
			AlertDto newAlert = alertService.createAlert(alert);
			response.setHeader("Location", baseUrl+"/service/alerts/"+newAlert.getId());
	        response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (StockException e) {
		    log.error("Error while saving alert "+alert, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
