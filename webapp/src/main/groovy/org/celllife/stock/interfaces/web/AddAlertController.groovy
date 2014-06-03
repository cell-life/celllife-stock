package org.celllife.stock.interfaces.web
import groovyx.net.http.HttpResponseDecorator

import java.security.Principal
import java.text.SimpleDateFormat

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.celllife.stock.framework.restclient.RESTClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class AddAlertController {
    
    private static Logger LOGGER = LoggerFactory.getLogger(AddAlertController.class)

    @Value('${external.base.url}')
    def String externalBaseUrl
    
    @Autowired
    RESTClient client;

	@RequestMapping(value="alert", method = RequestMethod.GET)
    def alert(Model model) {
        return new ModelAndView("alert", model);
	}

    @RequestMapping(value="/alert", method = RequestMethod.POST)
    def createAlerts(@RequestParam("level") String level, @RequestParam("message") String message,
            @RequestParam("msisdn") String msisdn, @RequestParam("drug") String drug, Model model) {
        def date = new Date()
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        def today = sdf.format(date)
        def alert = "{ \"date\": \""+today+"\", \"level\": \""+level+"\", \"message\": \""+message+"\", \"user\": {\"msisdn\": \""+msisdn+"\"}, \"drug\": {\"barcode\": \""+drug+"\"} }"
        def url = externalBaseUrl+"/service/alerts"
        LOGGER.info("Creating alert on "+url+" - "+alert)
        def httpResponseDecorator = client.postJson(url, alert)
        def alertId = getIdentifierFromLocation(httpResponseDecorator)
        LOGGER.info("Created alert="+alertId)
        if (alertId.empty) {
            model.put("errorMessage", "Error while saving Alert. ("+getErrorMessage(httpResponseDecorator)+")");
        } else {
            model.put("regMessage", "Alert for msisdn '"+msisdn+"' and drug '"+drug+"' saved successfully.");
        }
        return new ModelAndView("alert", model);
    }

    private String getIdentifierFromLocation(HttpResponseDecorator httpResponseDecorator) {
        if (httpResponseDecorator.isSuccess()) {
            def location = httpResponseDecorator.headers.getAt("Location").value
            def locationElements = location.split("/")
            def identifier = locationElements[locationElements.length-1]
            LOGGER.debug("created element "+location+" with identifer ="+identifier)
            return identifier;
        }
    }

    private String getErrorMessage(HttpResponseDecorator httpResponseDecorator) {
        return "" + httpResponseDecorator.status + " " + httpResponseDecorator.statusCode
    }

 }      