package com.umesh.mentalhealthpolaris.dto;

//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AssessmentDto {
    @Min(1)
    @Max(5)
    private int question1;

    @Min(1)
    @Max(5)
    private int question2;

    @Min(1)
    @Max(5)
    private int question3;

    @Min(1)
    @Max(5)
    private int question4;

    @Min(1)
    @Max(5)
    private int question5;
}
