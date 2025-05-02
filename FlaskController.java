package com.example.demo.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/ml")
public class FlaskController {

    @PostMapping("/predict")
    public ResponseEntity<String> predictFromFlask(@RequestBody List<Double> features) {
        String flaskApiUrl = "http://localhost:5000/predict";

        // Prepare request body
        Map<String, Object> body = new HashMap<>();
        body.put("features", features);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // Send request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(flaskApiUrl, request, String.class);

        return ResponseEntity.ok(response.getBody());
    }
}
