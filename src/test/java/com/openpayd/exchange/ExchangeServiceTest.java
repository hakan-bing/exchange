package com.openpayd.exchange;

import com.openpayd.exchange.component.DateValidator;
import com.openpayd.exchange.entity.Transaction;
import com.openpayd.exchange.exception.EmptyParamException;
import com.openpayd.exchange.model.response.ConversionResponse;
import com.openpayd.exchange.model.response.FixerLatestResponse;
import com.openpayd.exchange.model.response.RateResponse;
import com.openpayd.exchange.repository.TransactionRepository;
import com.openpayd.exchange.service.ExchangeServiceImpl;
import com.openpayd.exchange.service.RestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ExchangeServiceTest {

    @Mock
    RestService restService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    DateValidator dateValidator;

    @InjectMocks
    ExchangeServiceImpl exchangeService;

    private FixerLatestResponse fixerLatestResponse;
    private Transaction transaction;

    @Before
    public void init() {
        long time = System.currentTimeMillis();
        fixerLatestResponse = new FixerLatestResponse();
        fixerLatestResponse.setSuccess(true);
        fixerLatestResponse.setTimestamp(time);
        fixerLatestResponse.setBase("USD");
        fixerLatestResponse.setDate(new Date(time).toString());
        Map<String, Double> ratesMap = new HashMap<>();
        ratesMap.put("EUR", 0.813399);
        fixerLatestResponse.setRates(ratesMap);

        transaction = new Transaction();
        transaction.setDate(new Date(time));
        transaction.setSource("USD");
        transaction.setTarget("EUR");
        transaction.setId(1L);
        transaction.setRate(0.813399);
    }

    @Test(expected = EmptyParamException.class)
    public void emptyParamsRetrieveExchangeRateTest() {
        when(exchangeService.retrieveExchangeRate(any(), any())).thenThrow(new EmptyParamException());
    }

    @Test
    public void successRetrieveExchangeRateTest() {
        when(restService.callFixerLatest(any(), any())).thenReturn(ResponseEntity.ok(fixerLatestResponse));

        ResponseEntity<RateResponse> response = exchangeService.retrieveExchangeRate("USD", "EUR");
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertEquals(0.813399, response.getBody().getRate(), 0);
    }

    @Test(expected = EmptyParamException.class)
    public void emptyParamsConvertExchangeRateTest() {
        when(exchangeService.convertExchangeRate(any(), any(), any())).thenThrow(new EmptyParamException());
    }

    @Test
    public void successConvertExchangeRateTest() {
        when(restService.callFixerLatest(any(), any())).thenReturn(ResponseEntity.ok(fixerLatestResponse));

        when(transactionRepository.saveAndFlush(any())).thenReturn(transaction);

        ResponseEntity<ConversionResponse> response = exchangeService.convertExchangeRate("USD", "EUR", 1D);
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertEquals(0.813399, response.getBody().getAmount(), 0);
        assertEquals(1L, response.getBody().getTransactionId(), 0);
    }
}
