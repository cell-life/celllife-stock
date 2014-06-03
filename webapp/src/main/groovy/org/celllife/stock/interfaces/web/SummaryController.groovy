package org.celllife.stock.interfaces.web
import org.celllife.stock.framework.restclient.RESTClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class SummaryController {
    
    private static Logger LOGGER = LoggerFactory.getLogger(SummaryController.class)

    @Value('${external.base.url}')
    def String externalBaseUrl;
    
    @Autowired
    RESTClient client;

    @RequestMapping(value="/table", method = RequestMethod.GET)
    def getSummary(Model model) {
       def data = client.get(externalBaseUrl+"/service/stocks/stocktake/summary")
       LOGGER.debug("Called "+externalBaseUrl+"/service/stocks/stocktake/summary and got "+data)  
       def summaryMap  = [] as Set
       for (sum in data) {
            def id = sum["id"]
            def date = sum["date"]
            def quantity = sum["quantity"]
            def clinicCode = sum["user"]["clinicCode"]				
            def clinicName = sum["user"]["clinicName"]				
            def drugName = sum["drug"]["description"]
            def stockTake = ["id" : id,"date" : date,	"quantity" : quantity,	"clinicCode" : clinicCode,	"clinicName" : clinicName,	"drugName" : drugName]				
            summaryMap.add(stockTake)
        }
		
	    model.put("stockList", summaryMap)
 
	    return new ModelAndView("table", model);
    }
}