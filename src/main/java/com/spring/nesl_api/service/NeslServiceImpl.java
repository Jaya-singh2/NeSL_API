package com.spring.nesl_api.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.nesl_api.AppConfig;
import com.spring.nesl_api.model.AuditMessaging;
import com.spring.nesl_api.model.Content;
import com.spring.nesl_api.repository.AuditMessageRepository;
import com.spring.nesl_api.utility.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NeslServiceImpl implements NeslService {
    @Autowired
    FileUtility fileUtility;

//    @Override
//    public ResponseEntity<?>  getNeslApi(MultipartFile file, Map<String, String> headers, Map<String, Object> requestBody) throws IOException {
//        String apiUrl = AppConfig.NESL_URL;
//        HttpHeaders httpHeaders = new HttpHeaders();
//        Set<String> allowedHeaders = new HashSet<>(Arrays.asList(
//                "api-key", "authorization", "meta_data", "clientid", "resp-url"
//        ));
//          for (Map.Entry<String, String> entry : headers.entrySet()) {
//            String headerName = entry.getKey().toLowerCase(); // Convert to lowercase for case-insensitive check
//            if (allowedHeaders.contains(headerName)) {
//                httpHeaders.add(entry.getKey(), entry.getValue());
//            }
//        }
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        Map<String, Object> loan = (Map<String, Object>) requestBody.get("loan");
//        Map<String, Object> loanDetails = (Map<String, Object>) loan.get("loandtls");
//        List<Map<String, Object>> documentDetailsList = (List<Map<String, Object>>) loanDetails.get("documentdtls");
//        String base64File = fileUtility.convertFileToBase64(file);
//        fileUtility.saveFile(file);
//
//        // Iterate through the list and update the "docData" field
//        for (Map<String, Object> documentDetails : documentDetailsList) {
//            documentDetails.put("docData", base64File);
//        }
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<?> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
//
//        return responseEntity;
//    }

    @Override
    public ResponseEntity<?>  getNeslApi(MultipartFile file) throws IOException {
        String apiUrl = AppConfig.NESL_URL;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("api-key", "11Ma7sRLPctvRzWIFp2d");
        httpHeaders.add("Authorization", "Basic VGVzdDEyMzQ6ZVFMVEF6RmM=");
        httpHeaders.add("META_DATA", "---EncryptedMetaData------------");
        httpHeaders.add("clientID", "------RegisteredClientID---------------");
        httpHeaders.add("resp-url", "------Response URL---------------");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String base64File = fileUtility.convertFileToBase64(file);
        //fileUtility.saveFile(file);

        Map<String, Object> requestBody = createApiRequest(base64File);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        return responseEntity;
    }

    private static Map<String, Object> createApiRequest(String base64File) {
        Map<String, Object> apiRequest = new HashMap<>();
        Map<String, Object> loanDetails = new HashMap<>();
        Map<String, Object> documentDetails = new HashMap<>();
        documentDetails.put("docId", 1);
        documentDetails.put("docData", base64File);
        loanDetails.put("documentdtls", Arrays.asList(documentDetails));
        apiRequest.put("loan", loanDetails);

        return apiRequest;
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
