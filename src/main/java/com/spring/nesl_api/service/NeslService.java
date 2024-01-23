package com.spring.nesl_api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface NeslService {
    ResponseEntity<String> postAuditMessaging(Map<String, Object> requestBodyMap);
    String getNeslApi();
}
