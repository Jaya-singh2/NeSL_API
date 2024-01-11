package com.spring.nesl_api;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class NeSLController {
    private static final Logger logger = LoggerFactory.getLogger(NeSLController.class);

    @ApiOperation(value = "Get note list by contact id")
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


}
