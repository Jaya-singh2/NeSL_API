package com.spring.nesl_api.controller;

import com.spring.nesl_api.payload.request.LoanRequest;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.spring.nesl_api.service.NeslService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class NeSLController {
    private static final Logger logger = LoggerFactory.getLogger(NeSLController.class);

    @Autowired
    NeslService neslService;

    @ApiOperation(value = "Get external nesl api call")
    @RequestMapping(value = "/nesl/get-nesl-api", method = RequestMethod.GET)
    public ResponseEntity<?> getNeslApi() throws Exception {
        try {
            //System.out.println("nesl api response"+neslService.getNeslApi() );
            return ResponseEntity.ok(neslService.getNeslApi());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @ApiOperation(value = "This API will be used to audit messaging")
    @RequestMapping(value = {"/nesl/post-audit-messaging"}, method = RequestMethod.POST)
    public ResponseEntity<?> postAuditMessaging(@RequestBody Map<String, Object> requestBodyMap) throws Exception {
        try {
            ResponseEntity<String> responseEntity = neslService.postAuditMessaging(requestBodyMap);
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

}



