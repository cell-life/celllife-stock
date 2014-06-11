package org.celllife.stock.interfaces.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.celllife.stock.application.service.user.UserService;
import org.celllife.stock.domain.exception.StockException;
import org.celllife.stock.domain.user.ClinicDto;
import org.celllife.stock.domain.user.UserDto;
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
@RequestMapping("/service/clinics")
public class ClinicController {

    private static Logger log = LoggerFactory.getLogger(ClinicController.class);

    @Autowired
    UserService userService;

    @Value("${external.base.url}")
    String baseUrl;

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public void activateUser(@RequestBody ClinicDto clinic, HttpServletResponse response) throws IOException {
        try {
            userService.activateClinic(clinic);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (StockException e) {
            log.error("Error while activating clinic " + clinic, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
