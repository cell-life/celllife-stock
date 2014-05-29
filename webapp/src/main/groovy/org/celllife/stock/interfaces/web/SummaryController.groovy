package org.celllife.stock.interfaces.web

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

import java.text.SimpleDateFormat

import org.celllife.stock.framework.restclient.RESTClient 

@Controller
class SummaryController {

    @Value('${external.base.url}')
    def String externalBaseUrl;
    
    @Autowired
    RESTClient client;

  
    @RequestMapping(value="/table", method = RequestMethod.GET)
    def getTable(Model model) {

        //def  = client.get("${externalBaseUrl}/")
        //model.put("map", ,map)
        return "table";

    }

}
