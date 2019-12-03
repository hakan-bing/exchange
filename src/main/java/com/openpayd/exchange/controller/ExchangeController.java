package com.openpayd.exchange.controller;

import com.openpayd.exchange.model.response.ConversionListResponse;
import com.openpayd.exchange.model.response.ConversionResponse;
import com.openpayd.exchange.model.response.RateResponse;
import com.openpayd.exchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "api")
public class ExchangeController {

    @Autowired
    ExchangeService exchangeService;

    @RequestMapping(path = "/rate", method = RequestMethod.GET)
    ResponseEntity<RateResponse> rate(@RequestParam(name = "source") String source, @RequestParam(name = "target") String target) {
        return exchangeService.retrieveExchangeRate(source, target);
    }

    @RequestMapping(path = "/conversion", method = RequestMethod.GET)
    ResponseEntity<ConversionResponse> conversion(@RequestParam(name = "source") String source, @RequestParam(name = "target") String target, @RequestParam(name = "amount") Double amount) {
        return exchangeService.convertExchangeRate(source, target, amount);
    }

    @RequestMapping(path = "/conversions", method = RequestMethod.GET)
    ResponseEntity<ConversionListResponse> conversionList(@RequestParam(name = "transactionId", required = false) Long transactionId, @RequestParam(name = "date", required = false) String date,
                                                          @RequestParam(name = "page", required = false) Integer page) {
        return exchangeService.fetchConversionList(transactionId, date, page);
    }

}
