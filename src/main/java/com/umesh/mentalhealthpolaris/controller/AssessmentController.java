package com.umesh.mentalhealthpolaris.controller;

import com.umesh.mentalhealthpolaris.response.ApiResponse;
import com.umesh.mentalhealthpolaris.services.AssessmentService;
import com.umesh.mentalhealthpolaris.dto.AssessmentDto;
import com.umesh.mentalhealthpolaris.model.Assessment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;

    @PostMapping("submit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Only USER or ADMIN can submit assessments
    public ResponseEntity<ApiResponse> submitAssessment(@Valid @RequestBody AssessmentDto dto) {
        try {
            assessmentService.submitAssessment(dto);
            return ResponseEntity.ok(new ApiResponse("Assessment submitted successfully", 200));
        } catch (IllegalArgumentException e) {
            // Validation errors (e.g., rating not between 1–5)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), 401));
        } catch (Exception e) {
            // Unknown or server-side error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal server error", 501));
        }
    }

    @GetMapping("my_data")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Only USER or ADMIN can view their own assessments
    public ResponseEntity<ApiResponse> getMyAssessments() {
        try {
            List<Assessment> assessments = assessmentService.getAssessmentsForCurrentUser();
            return ResponseEntity.ok(new ApiResponse("Data fetched successfully", 200, assessments));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal server error", 501));
        }
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can view all assessmentss
    @GetMapping("all")
    public ResponseEntity<ApiResponse> getAllAssessments() {

        try {
            List<Assessment> assessments = assessmentService.getAllAssessments();
            return ResponseEntity.ok(new ApiResponse("Data fetched successfully", 200, assessments));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal server error", 501));
        }
    }

}
