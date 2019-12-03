package com.openpayd.exchange.model;

public enum HttpCode {
    EMPTY_PARAM(1001, "Parameter can not be empty."),
    AT_LEAST_ONE_PARAM(1002, "At least one of the optional parameters must not be empty."),
    ILLEGAL_DATE_FORMAT(1003, "Unsupported date format.");

    private final int value;
    private final String reason;

    HttpCode(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public int getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }
}
