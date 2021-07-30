package com.example.demo.client.rajaongkir;

import com.example.demo.client.rajaongkir.dto.request.CostRequest;
import com.example.demo.client.rajaongkir.dto.response.CostResponse;
import com.example.demo.enums.ResponseEnum;
import com.example.demo.exception.SpringBootSimpleAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        prepareFormData(map, costRequest);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        try {
            log.info("Sending cost request to RajaOngkir API: " + request);
            ResponseEntity<CostResponse> response = restTemplate.postForEntity(url, request, CostResponse.class);
            log.info("Response status: " + response.getStatusCode());
            return response.getBody();
        } catch (RestClientException e){
            log.warn("Failed sending request to RajaOngkir");
            throw new SpringBootSimpleAppException(ResponseEnum.CLIENT_ERROR.getMessage(), ResponseEnum.CLIENT_ERROR.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void prepareFormData(MultiValueMap<String, Object> map, CostRequest r) {
        map.add("origin", r.getOrigin());
        map.add("destination", r.getDestination());
        map.add("courier", r.getCourier());
        map.add("weight", r.getWeight());
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("key", apiKey);
        return headers;
    }
}
