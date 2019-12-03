package com.openpayd.exchange.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openpayd.exchange.entity.Transaction;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionListResponse extends BaseResponse {

    @JsonProperty("transactions")
    private List<Transaction> transactions;

    @JsonProperty("hasNext")
    private Boolean hasNext;

    public ConversionListResponse() {
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }
}
