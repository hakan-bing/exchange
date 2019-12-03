package com.openpayd.exchange.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openpayd.exchange.model.ErrorCode;

public class BaseResponse {

    @JsonProperty(value = "error")
    private ErrorCode error;

    public BaseResponse() {
    }

    public BaseResponse(ErrorCode error) {
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }

    public void setError(ErrorCode error) {
        this.error = error;
    }

}
