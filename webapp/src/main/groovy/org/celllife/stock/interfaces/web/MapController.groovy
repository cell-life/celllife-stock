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
class MapController {


	@RequestMapping(value="/map", method = RequestMethod.GET)
	def getMap(Model model) {

		return "map";
	}
}
