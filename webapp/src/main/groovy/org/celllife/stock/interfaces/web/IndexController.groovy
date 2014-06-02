package org.celllife.stock.interfaces.web
import groovyx.net.http.HttpResponseDecorator

import java.security.Principal

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.ModelAndView

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.prepost.PreAuthorize
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import java.text.SimpleDateFormat
import java.util.Date

import org.celllife.stock.framework.restclient.RESTClient 

@Controller
class IndexController {

    @Value('${external.base.url}')
    def String externalBaseUrl
    
    @Autowired
    RESTClient client;

    @RequestMapping(value="/", method = RequestMethod.POST)
    def createAlerts(@RequestParam("level") String level, @RequestParam("message") String message,  @RequestParam("user") String user,@RequestParam("bar_code") String bar_code,
            Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
            def date = new Date()
            def sdf = new SimpleDateFormat("yyyy-MM-dd") 
            def today = sdf.format(date)
            System.out.println(today);
		    def alert = "{date: " + today + ", level: " + level + " , message: " + message +" , user: {msisdn: "+ user +" },drug: {barcode: "+bar_code+ "}"
			def httpResponseDecorator = client.postJson(externalBaseUrl, alert)
			client.postJson(externalBaseUrl, alert)
    if(alert.empty)
    {
       model.put("errorMessage", "Error while saving Alert. ("+getErrorMessage(httpResponseDecorator)+")");
    }
    //return new ModelAndView("index", model);
	index(model)

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
