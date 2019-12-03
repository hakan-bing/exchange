package com.openpayd.exchange.service;

import com.openpayd.exchange.component.DateValidator;
import com.openpayd.exchange.entity.Transaction;
import com.openpayd.exchange.exception.AtLeastOneParamException;
import com.openpayd.exchange.exception.EmptyParamException;
import com.openpayd.exchange.exception.IllegalDateFormatException;
import com.openpayd.exchange.model.response.ConversionListResponse;
import com.openpayd.exchange.model.response.ConversionResponse;
import com.openpayd.exchange.model.response.FixerLatestResponse;
import com.openpayd.exchange.model.response.RateResponse;
import com.openpayd.exchange.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.Objects;

@Service("exchangeService")
public class ExchangeServiceImpl implements ExchangeService {

    private final int DEFAULT_PAGE = 0;
    private final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    RestService restService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DateValidator dateValidator;

    @Override
    public ResponseEntity<RateResponse> retrieveExchangeRate(String source, String target) {
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(target)) {
            throw new EmptyParamException();
        }

        ResponseEntity<FixerLatestResponse> responseEntity = restService.callFixerLatest(source, new String[]{target});
        FixerLatestResponse fixerLatestResponse = responseEntity.getBody();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            RateResponse rateResponse = new RateResponse();
            /* When an error receiver from Fixer API Service set error */
            rateResponse.setError(fixerLatestResponse.getError());
            if (fixerLatestResponse.getSuccess()) {
                rateResponse.setRate(responseEntity.getBody().getRates().get(target));
                return new ResponseEntity<>(rateResponse, HttpStatus.OK);
            } else {
                /* When an error receiver from Fixer API Service set error */
                rateResponse.setError(fixerLatestResponse.getError());
                return new ResponseEntity<>(rateResponse, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(responseEntity.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<ConversionResponse> convertExchangeRate(String source, String target, Double amount) {
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(target) || Objects.isNull(amount)) {
            throw new EmptyParamException();
        }

        ResponseEntity<FixerLatestResponse> responseEntity = restService.callFixerLatest(source, new String[]{target});
        FixerLatestResponse fixerLatestResponse = responseEntity.getBody();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            ConversionResponse conversionResponse = new ConversionResponse();
            /* When an error receiver from Fixer API Service set error */
            conversionResponse.setError(fixerLatestResponse.getError());

            if (fixerLatestResponse.getSuccess()) {
                /* Prepare transaction entity */
                Double rate = fixerLatestResponse.getRates().get(target);
                Transaction entity = new Transaction();
                entity.setSource(source);
                entity.setTarget(target);
                entity.setRate(rate);
                entity.setDate(Date.valueOf(fixerLatestResponse.getDate()));
                /* Save and flush Transaction entity to DB */
                Transaction savedEntity = transactionRepository.saveAndFlush(entity);
                /* After use transaction id */
                conversionResponse.setTransactionId(savedEntity.getId());
                conversionResponse.setAmount(rate * amount);
            }
            return new ResponseEntity<>(conversionResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseEntity.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<ConversionListResponse> fetchConversionList(Long transactionId, String date, Integer page) {
        if (StringUtils.isEmpty(transactionId) && StringUtils.isEmpty(date)) {
            throw new AtLeastOneParamException();
        }

        if (!dateValidator.isValid(date)) {
            throw new IllegalDateFormatException();
        }

        ConversionListResponse conversionListResponse = new ConversionListResponse();
        /* If param of page is empty use default page */
        Pageable pageable = Objects.isNull(page) ? PageRequest.of(DEFAULT_PAGE, DEFAULT_PAGE_SIZE)
                : PageRequest.of(page, DEFAULT_PAGE_SIZE);
        Page<Transaction> transactionPage = transactionRepository.findByTransactionIdAndDate(transactionId, date, pageable);
        conversionListResponse.setHasNext(transactionPage.hasNext());
        conversionListResponse.setTransactions(transactionPage.getContent());

        return new ResponseEntity<>(conversionListResponse, HttpStatus.OK);
    }
}
