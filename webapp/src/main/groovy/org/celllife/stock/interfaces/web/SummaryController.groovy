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
class SummaryController {

    @Value('${external.base.url}')
    def String externalBaseUrl;
    
    @Autowired
    RESTClient client;


    @RequestMapping(value="/table", method = RequestMethod.GET)
	def getMap(Model model) {

		return "table";
  

    }
}
