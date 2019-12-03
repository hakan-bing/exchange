package com.openpayd.exchange.controller;

import com.openpayd.exchange.model.ErrorCode;
import com.openpayd.exchange.model.response.BaseResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorsController extends AbstractErrorController {

    public ErrorsController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ResponseEntity<BaseResponse> handleError(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        ErrorCode error = new ErrorCode(status);
        return new ResponseEntity<>(new BaseResponse(error), status);
    }
}
