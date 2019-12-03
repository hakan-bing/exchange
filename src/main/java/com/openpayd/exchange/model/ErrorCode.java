package com.openpayd.exchange.model;

import org.springframework.http.HttpStatus;

public class ErrorCode {

    private int code;
    private String info;

    public ErrorCode() {
    }

    public ErrorCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public ErrorCode(HttpCode httpCode) {
        this.code = httpCode.getValue();
        this.info = httpCode.getReason();
    }

    public ErrorCode(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.info = httpStatus.getReasonPhrase();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
