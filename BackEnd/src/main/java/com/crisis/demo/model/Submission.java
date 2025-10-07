package com.crisis.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Data
@Document(collection = "submissions") 
public class Submission {

    @Id
    private String id; 

    private String text;
    private double lat;
    private double lng;
    private String prediction;
    private double confidence;
    private Instant timestamp;
}