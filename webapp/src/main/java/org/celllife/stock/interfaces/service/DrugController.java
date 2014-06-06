package org.celllife.stock.interfaces.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stock.application.service.drug.DrugService;
import org.celllife.stock.domain.drug.DrugDto;
import org.celllife.stock.domain.exception.StockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service/drugs")
public class DrugController {
    
    private static Logger log = LoggerFactory.getLogger(DrugController.class);

	@Autowired
	DrugService drugService;

	@Value("${external.base.url}") 
	String baseUrl;

	@ResponseBody 
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DrugDto getDrug(@RequestParam("barcode") String barcode, HttpServletResponse response) throws IOException {
		try {
			DrugDto drug = drugService.getDrug(barcode);
	        if (drug == null) {
	        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        	return null;
	        } else {
	        	return drug;
	        }
		} catch (StockException e) {
		    log.error("Error while getting drug with barcode "+barcode, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public void saveDrug(@RequestBody DrugDto drug, HttpServletResponse response) throws IOException {
		try {
			DrugDto newDrug = drugService.createDrug(drug);
			response.setHeader("Location", baseUrl+"/service/drugs?barcode="+newDrug.getBarcode());
	        response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (StockException e) {
		    log.error("Error while saving drug "+drug, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
