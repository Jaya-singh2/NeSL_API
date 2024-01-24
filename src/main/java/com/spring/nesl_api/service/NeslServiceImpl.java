package com.spring.nesl_api.service;

import com.spring.nesl_api.model.AuditMessaging;
import com.spring.nesl_api.model.Content;
import com.spring.nesl_api.payload.request.AuditMessagingRequest;
import com.spring.nesl_api.repository.AuditMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NeslServiceImpl implements NeslService {
    @Override
    public String getNeslApi(){
        String apiUrl = "https://stg.nesl.co.in/DDE_IU_Registration_V3/IUREG_API";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        return  response;
    }

    @Autowired
    AuditMessageRepository auditMessageRepository;
    @Override
    public ResponseEntity<String> postAuditMessaging(Map<String, Object> requestBodyMap) {
        String apiUrl = "https://legalcloudaudit.azurewebsites.net/api/AuditMessaging";
        AuditMessaging auditMessaging = new AuditMessaging();
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
        if(null != requestBody.getEmail()){
            auditMessaging.setEmail(requestBody.getEmail());
        }
        if(null != requestBody.getActionName()){
            auditMessaging.setActionName(requestBody.getActionName());
        }
        if(null != requestBody.getControllerName()){
            auditMessaging.setControllerName(requestBody.getControllerName());
        }
        if(null != requestBody.getSenderName()){
            auditMessaging.setSenderName(requestBody.getSenderName());
        }
        if(null != requestBody.getClientId()){
            auditMessaging.setClientId(requestBody.getClientId());
        }
        if(null != requestBody.getCompanyId()){
            auditMessaging.setCompanyId(requestBody.getCompanyId());
        }
        Map<String, Object> contentMap = requestBody.getContent();
        if (contentMap != null) {
            Content content = new Content();
            content.setKey1(getStringFromMap(contentMap, "key1"));
            content.setKey2(getStringFromMap(contentMap, "key2"));
            auditMessaging.setContent(content);
        }
        AuditMessaging auditMessaging1 = auditMessageRepository.save(auditMessaging);
        HttpEntity<AuditMessagingRequest> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        return responseEntity;
    }

    private String getStringFromMap(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (String) map.get(key) : null;
    }

}
