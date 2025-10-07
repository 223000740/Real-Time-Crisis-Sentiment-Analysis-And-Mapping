package com.crisis.demo.dto;

import lombok.Data;

@Data
public class SubmissionRequestDTO {
    private String text;
    private double lat;
    private double lng;
}