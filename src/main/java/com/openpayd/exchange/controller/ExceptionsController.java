package com.openpayd.exchange.controller;

import com.openpayd.exchange.exception.AtLeastOneParamException;
import com.openpayd.exchange.exception.EmptyParamException;
import com.openpayd.exchange.exception.IllegalDateFormatException;
import com.openpayd.exchange.model.ErrorCode;
import com.openpayd.exchange.model.HttpCode;
import com.openpayd.exchange.model.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(value = EmptyParamException.class)
    public ResponseEntity<BaseResponse> emptyException(EmptyParamException emptyParamException) {
        ErrorCode error = new ErrorCode(HttpCode.EMPTY_PARAM);
        return new ResponseEntity<>(new BaseResponse(error), HttpStatus.OK);
    }

    @ExceptionHandler(value = AtLeastOneParamException.class)
    public ResponseEntity<BaseResponse> atLeastOneException(AtLeastOneParamException atLeastOneParamException) {
        ErrorCode error = new ErrorCode(HttpCode.AT_LEAST_ONE_PARAM);
        return new ResponseEntity<>(new BaseResponse(error), HttpStatus.OK);
    }

    @ExceptionHandler(value = IllegalDateFormatException.class)
    public ResponseEntity<BaseResponse> illegalDateFormatException(IllegalDateFormatException illegalDateFormatException) {
        ErrorCode error = new ErrorCode(HttpCode.ILLEGAL_DATE_FORMAT);
        return new ResponseEntity<>(new BaseResponse(error), HttpStatus.OK);
    }
}
