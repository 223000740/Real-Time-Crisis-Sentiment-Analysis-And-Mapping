package com.crisis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.crisis.demo.dto.MLResponseDTO;
import com.crisis.demo.dto.SubmissionRequestDTO;
import com.crisis.demo.model.Submission;
import com.crisis.demo.repository.SubmissionRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submissions")
@CrossOrigin(origins = "*") 
public class SubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String fastApiUrl = "http://172.16.97.152:5000/predict"; 


    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    
    @PostMapping
    public ResponseEntity<Object> createSubmission(@RequestBody SubmissionRequestDTO requestDTO) {
        try {
            ResponseEntity<MLResponseDTO> mlResponse = restTemplate.postForEntity(
                fastApiUrl,
                Map.of("text", requestDTO.getText()), 
                MLResponseDTO.class
            );
            if (mlResponse.getStatusCode() == HttpStatus.NO_CONTENT) {
                return ResponseEntity.ok().body(Map.of("message", "Submission received but not classified as urgent."));
            }

            if (mlResponse.hasBody()) {
                MLResponseDTO prediction = mlResponse.getBody();
                if (prediction == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "ML service returned null prediction."));
                }
                
                Submission newSubmission = new Submission();
                newSubmission.setText(requestDTO.getText());
                newSubmission.setLat(requestDTO.getLat());
                newSubmission.setLng(requestDTO.getLng());
                newSubmission.setTimestamp(Instant.now());
                newSubmission.setPrediction(prediction.getPrediction());
                newSubmission.setConfidence(prediction.getConfidence());

                Submission savedSubmission = submissionRepository.save(newSubmission);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedSubmission);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Received an empty response from ML service."));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
