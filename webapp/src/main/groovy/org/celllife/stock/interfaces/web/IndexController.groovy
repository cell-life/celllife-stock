package org.celllife.stock.interfaces.web

import org.celllife.stock.framework.restclient.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController {

    @Value('${external.base.url}')
    def String externalBaseUrl

    @Autowired
    RESTClient client;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
    def root(Model model) {
        return index(model);
    }
	
	@RequestMapping(value="index", method = RequestMethod.GET)
	def index(Model model) {
    return new ModelAndView("index", model);
		 
	}
}
