package com.spring.nesl_api.controller;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value = "/nesl/get-nesl-api", method = RequestMethod.POST)
    public ResponseEntity<?> getNeslApi(
            @RequestHeader Map<String, String> headers,
            @RequestBody Map<String, Object> requestBody) throws Exception {
        try {
            return ResponseEntity.ok(neslService.getNeslApi( headers, requestBody));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
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
            throw new Exception(e.getMessage());
        }
    }

}



