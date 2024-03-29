package com.spring.nesl_api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public interface NeslService {
    ResponseEntity<String> postAuditMessaging(Map<String, Object> requestBodyMap);
    //ResponseEntity<?> getNeslApi(MultipartFile file,Map<String, String> headers, Map<String, Object> requestBody) throws IOException;
    ResponseEntity<?> getNeslApi(MultipartFile file) throws IOException;
}
