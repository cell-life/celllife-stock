package org.celllife.stockout.interfaces.service;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stockout.application.service.alert.AlertService;
import org.celllife.stockout.domain.alert.AlertDto;
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
    @RequestMapping(value = "{alertId}", method = RequestMethod.GET, produces = "application/json")
    public AlertDto getAlert(@PathVariable("alertId") Long alertId, HttpServletResponse response) {
        AlertDto alert = alertService.getAlert(alertId);
        if (alert == null) {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        	return null;
        } else {
        	return alert;
        }
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public void saveAlert(@RequestBody AlertDto alert, HttpServletResponse response) {
		System.out.println("hello!! "+alert);
		AlertDto newAlert = alertService.createAlert(alert);
		response.setHeader("Location", baseUrl+"/service/alerts/"+newAlert.getId());
        response.setStatus(HttpServletResponse.SC_CREATED);
	}
}
