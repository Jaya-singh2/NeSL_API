package com.spring.nesl_api.controller;

import com.spring.nesl_api.payload.request.AuditMessagingRequest;
import com.spring.nesl_api.payload.request.LoanRequest;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class NeSLController {
    private static final Logger logger = LoggerFactory.getLogger(NeSLController.class);

    @ApiOperation(value = "Get external nesl api call")
    @RequestMapping(value = "/nesl-external-api-call", method = RequestMethod.GET)
    public ResponseEntity<?> callEternalApi() throws Exception {
        try {
            String apiUrl = "https://stg.nesl.co.in/DDE_IU_Registration_V3/IUREG_API";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw e;
        }
    }


    @ApiOperation(value = "This API will be used to get loans details")
    @RequestMapping(value = {"/nesl/get-loans-details"}, method = RequestMethod.POST)
    public ResponseEntity<?> getLoanDetails(@RequestBody LoanRequest loanRequest) throws Exception {
        try {
            String apiUrl = "https://stg.nesl.co.in/DDEEsignLinkAPI/request/retriggeringthelink/loans/";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("your_username", "your_password");
            HttpEntity<LoanRequest> requestEntity = new HttpEntity<>(loanRequest, headers);
            RestTemplate restTemplate = new RestTemplate();
            return ResponseEntity.ok(restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            ));
        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to audit messaging")
    @RequestMapping(value = {"/nesl/audit-messaging"}, method = RequestMethod.POST)
    public ResponseEntity<?> callAuditMessaging(@RequestBody AuditMessagingRequest auditMessagingRequest) throws Exception {
        try {
            String apiUrl = "https://legalcloudaudit.azurewebsites.net/api/AuditMessaging";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request body based on the incoming data
            AuditMessagingRequest requestBody = new AuditMessagingRequest();
            requestBody.setEmail(auditMessagingRequest.getEmail());
            requestBody.setActionName(auditMessagingRequest.getActionName());
            requestBody.setControllerName(auditMessagingRequest.getControllerName());
            requestBody.setSenderName(auditMessagingRequest.getSenderName());
            requestBody.setClientId(auditMessagingRequest.getClientId());
            requestBody.setCompanyId(auditMessagingRequest.getCompanyId());
            requestBody.setContent(auditMessagingRequest.getContent());

            HttpEntity<AuditMessagingRequest> requestEntity = new HttpEntity<>(auditMessagingRequest, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            // Handle exceptions appropriately
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

}



