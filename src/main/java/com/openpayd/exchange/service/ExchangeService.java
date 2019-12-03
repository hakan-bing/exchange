package com.openpayd.exchange.service;

import com.openpayd.exchange.model.response.ConversionListResponse;
import com.openpayd.exchange.model.response.ConversionResponse;
import com.openpayd.exchange.model.response.RateResponse;
import org.springframework.http.ResponseEntity;

public interface ExchangeService {

    ResponseEntity<RateResponse> retrieveExchangeRate(String source, String target);

    ResponseEntity<ConversionResponse> convertExchangeRate(String source, String target, Double amount);

    ResponseEntity<ConversionListResponse> fetchConversionList(Long transactionId, String date, Integer page);
}
