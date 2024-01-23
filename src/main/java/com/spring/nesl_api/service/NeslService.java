package com.spring.nesl_api.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface NeslService {
    ResponseEntity<String> postAuditMessaging(Map<String, Object> requestBodyMap);
}
