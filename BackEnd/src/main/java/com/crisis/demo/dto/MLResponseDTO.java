package com.crisis.demo.dto;

import lombok.Data;

@Data
public class MLResponseDTO {
    private String prediction;
    private double confidence;
}