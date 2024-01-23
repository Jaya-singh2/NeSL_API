package com.spring.nesl_api.service;

import com.spring.nesl_api.payload.request.AuditMessagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NeslServiceImpl implements NeslService {

    @Override
    public ResponseEntity<String> postAuditMessaging(Map<String, Object> requestBodyMap) {
        String apiUrl = "https://legalcloudaudit.azurewebsites.net/api/AuditMessaging";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Create the request body based on the incoming data
        AuditMessagingRequest requestBody = new AuditMessagingRequest();
        requestBody.setEmail((String) requestBodyMap.get("email"));
        requestBody.setActionName((String) requestBodyMap.get("ActionName"));
        requestBody.setControllerName((String) requestBodyMap.get("ControllerName"));
        requestBody.setSenderName((String) requestBodyMap.get("SenderName"));
        requestBody.setClientId((String) requestBodyMap.get("ClientId"));
        requestBody.setCompanyId((String) requestBodyMap.get("CompanyId"));
        requestBody.setContent((Map<String, Object>) requestBodyMap.get("Content"));

        HttpEntity<AuditMessagingRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        return responseEntity;

    }
}
