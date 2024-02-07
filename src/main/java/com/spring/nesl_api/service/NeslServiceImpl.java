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

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
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
    private static final String AES_ALGORITHM = "AES";
    private static final String RSA_ALGORITHM = "RSA";

    @Override
    public ResponseEntity<?>  getNeslApi(MultipartFile file) throws IOException {
        String apiUrl = AppConfig.NESL_URL;
        String sessionId = UUID.randomUUID().toString();
        String base64File = fileUtility.convertFileToBase64(file);
        //fileUtility.saveFile(file);

        //METADATA value encryption
        Map<String, Object> metaDataRequestBody = createMetaDataRequest(base64File);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(metaDataRequestBody);
        String encryptedJson = encryptWithAES(jsonRequest, sessionId);
        String encryptedSessionId = encryptWithRSA(sessionId);
        String metadata = encryptedSessionId + ":" + encryptedJson;
        // Request Body
        Map<String, Object> requestBody = createJsonResponse(base64File, metadata);
        // Request Heaader
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //httpHeaders.add("api-key", "11Ma7sRLPctvRzWIFp2d");
        //httpHeaders.add("Authorization", "Basic VGVzdDEyMzQ6ZVFMVEF6RmM=");
        //httpHeaders.add("META_DATA", metadata);
        //httpHeaders.add("META_DATA", "------EncryptedMetaData---------------");
        //httpHeaders.add("clientID", "------RegisteredClientID---------------");
        //httpHeaders.add("resp-url", "------Response URL---------------");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        return responseEntity;
    }

    private static Map<String, Object> createMetaDataRequest(String base64File) {
        Map<String, Object> apiRequest = new HashMap<>();
        Map<String, Object> loanDetails = new HashMap<>();
        Map<String, Object> documentDetails = new HashMap<>();
        documentDetails.put("docId", 1);
        documentDetails.put("docData", base64File);
        loanDetails.put("documentdtls", Arrays.asList(documentDetails));
        loanDetails.put("txnID","9ab08500-44ed-4bd6-98ce-83d7e5228d1b");
        apiRequest.put("loan", loanDetails);

        return apiRequest;
    }

    private static Map<String, Object> createJsonResponse(String base64File, String metaData) {
        Map<String, Object> response = new HashMap<>();

        response.put("api-key", "11Ma7sRLPctvRzWIFp2d");
        response.put("Authorization", "Basic VGVzdDEyMzQ6ZVFMVEF6RmM=");
        response.put("META_DATA", metaData);
        response.put("clientID", "");
        response.put("txnID", "abcd1234");
        response.put("pan", "BUNPP5528B");
        response.put("aadhaarName", "John Doe");
        response.put("panName", "John Doe");
        response.put("dob", "1990-01-01");
        response.put("email", "john.doe@example.com");
        response.put("mobile", "1234567890");
        response.put("address", "123 Main Street, City");
        response.put("entityID", "987654");
        response.put("docData", base64File);
        response.put("signFlag", "1");
        response.put("estampFlag", "Y");
        response.put("loanDocFlag", "Y");
        response.put("regType", "1");
        response.put("origin", "Indian");
        response.put("doi", "2022-01-01");
        response.put("regAddress", "456 Business Street, City");
        response.put("regPin", 543210);
        response.put("entityEmail", "entity@example.com");
        response.put("telNo", "9876543210");
        response.put("templateID", 987);
        response.put("f2f", "Y");

        return response;
    }


    private static String encryptWithAES(String data, String key) {
        try {
            SecretKey secretKey = KeyGenerator.getInstance(AES_ALGORITHM).generateKey();
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String encryptWithRSA(String data) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());

            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
