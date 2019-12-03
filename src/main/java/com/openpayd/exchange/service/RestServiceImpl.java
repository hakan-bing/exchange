package com.openpayd.exchange.service;

import com.openpayd.exchange.model.response.FixerLatestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service("restService")
public class RestServiceImpl implements RestService {

    @Value("${fixer.api.url}")
    private String fixerApiUrl;

    @Value("${fixer.api.key}")
    private String fixerApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public RestServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public ResponseEntity<FixerLatestResponse> callFixerLatest(String base, String[] symbols) {
        String symbolsStr = Arrays.stream(symbols)
                .collect(Collectors.joining(","));

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(fixerApiUrl + "/latest")
                .queryParam("access_key", fixerApiKey)
                .queryParam("base", base)
                .queryParam("symbols", symbolsStr)
                .build();
        return restTemplate.getForEntity(uriComponents.toUriString(), FixerLatestResponse.class);
    }
}
