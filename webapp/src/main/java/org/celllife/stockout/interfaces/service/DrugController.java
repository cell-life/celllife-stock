package org.celllife.stockout.interfaces.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stockout.application.service.drug.DrugService;
import org.celllife.stockout.domain.drug.DrugDto;
import org.celllife.stockout.domain.exception.StockOutException;
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

	@Autowired
	DrugService drugService;

	@Value("${external.base.url}") 
	String baseUrl;

	@ResponseBody 
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DrugDto getDrug(@RequestParam("barcode") String barcode) {
		return drugService.getDrug(barcode);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void saveDrug(@RequestBody DrugDto drug, HttpServletResponse response) throws IOException {
		try {
			DrugDto newDrug = drugService.createDrug(drug);
			response.setHeader("Location", baseUrl+"/service/drugs?barcode="+newDrug.getBarcode());
	        response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (StockOutException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
