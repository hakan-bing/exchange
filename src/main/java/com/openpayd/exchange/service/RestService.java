package com.openpayd.exchange.service;

import com.openpayd.exchange.model.response.FixerLatestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

public interface RestService {

    ResponseEntity<FixerLatestResponse> callFixerLatest(@NonNull String base, @NonNull String[] symbols);
}
