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

import org.celllife.stock.framework.restclient.RESTClient 

@Controller
class SummaryController {

    @Value('${external.base.url}')
    def String externalBaseUrl;
    
    @Autowired
    RESTClient client;

    @RequestMapping(value="/summary/loadStockTake", method = RequestMethod.GET)
    def getSummary(Model model) {
       def data = client.get(externalBaseUrl+"/service/stocks/stocktake/summary")  
       def summaryMap  = [] as Set
        for (sum in data) {
                def id = sum["id"]
                def date = sum["date"]
                def quantity = sum["quantity"]
                def clinicCode = sum["user"]["clinicCode"]				
				def clinicName = sum["user"]["clinicName"]				
				def drugName = sum["drug"]["name"]
				def stockTake = ["id" : id,"date" : date,	"quantity" : quantity,	"clinicCode" : clinicCode,	"clinicName" : clinicName,	"drugName" : drugName]				
				summaryMap.add(stockTake)
            }
		
	     model.put("stockList", summaryMap)
 
		 return new ModelAndView("table", model);
        }
    

 }

