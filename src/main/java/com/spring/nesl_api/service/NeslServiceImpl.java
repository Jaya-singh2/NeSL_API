package com.spring.nesl_api.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.nesl_api.AppConfig;
import com.spring.nesl_api.model.AuditMessaging;
import com.spring.nesl_api.model.Content;
import com.spring.nesl_api.repository.AuditMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NeslServiceImpl implements NeslService {
    @Override
    public ResponseEntity<?>  getNeslApi( Map<String, String> queryParams, Map<String, Object> requestBody){
        String apiUrl = AppConfig.NESL_URL+ "?" +
                queryParams.entrySet().stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(Collectors.joining("&"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        return responseEntity;
    }

    @Autowired
    AuditMessageRepository auditMessageRepository;
    @Override
    public ResponseEntity<String> postAuditMessaging(Map<String, Object> requestBodyMap) {
        String apiUrl = AppConfig.AUDIT_MESSAGING_URL;
        AuditMessaging auditMessaging = new AuditMessaging();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(null != requestBodyMap.get("email")){
            auditMessaging.setEmail((String) requestBodyMap.get("email"));
        }
        if(null != requestBodyMap.get("ActionName")){
            auditMessaging.setActionName((String) requestBodyMap.get("ActionName"));
        }
        if(null != requestBodyMap.get("ControllerName")){
            auditMessaging.setControllerName((String) requestBodyMap.get("ControllerName"));
        }
        if(null != requestBodyMap.get("SenderName")){
            auditMessaging.setSenderName((String) requestBodyMap.get("SenderName"));
        }
        if(null != requestBodyMap.get("ClientId")){
            auditMessaging.setClientId((String) requestBodyMap.get("ClientId"));
        }
        if(null != requestBodyMap.get("CompanyId")){
            auditMessaging.setCompanyId((String) requestBodyMap.get("CompanyId"));
        }
        Map<String, Object> contentMap = (Map<String, Object>) requestBodyMap.get("Content");
        if (contentMap != null) {
            Content content = new Content();
            content.setKey1(getStringFromMap(contentMap, "key1"));
            content.setKey2(getStringFromMap(contentMap, "key2"));
            auditMessaging.setContent(content);
        }
        auditMessageRepository.save(auditMessaging);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBodyMap, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        return responseEntity;
    }

    private String getStringFromMap(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (String) map.get(key) : null;
    }

}
