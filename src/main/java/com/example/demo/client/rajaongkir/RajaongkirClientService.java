package com.example.demo.client.rajaongkir;

import com.example.demo.client.rajaongkir.dto.request.CostRequest;
import com.example.demo.client.rajaongkir.dto.response.CostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RajaongkirClientService {

    @Value("${rajaongkir.url}")
    private String baseUrl;

    @Value("${rajaongkir.request.apikey}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public CostResponse getCost(CostRequest costRequest) {
        String url = baseUrl.concat("/cost");
        HttpHeaders headers = setHeader();
        HttpEntity<CostRequest> request = new HttpEntity<>(costRequest, headers);
        try {
            ResponseEntity<CostResponse> response = restTemplate.postForEntity(url, request, CostResponse.class);
            log.debug("Response: " + response.getBody());
            return response.getBody();
        } catch (RestClientException e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("key", apiKey);
        return headers;
    }
}
