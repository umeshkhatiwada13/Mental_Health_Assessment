package com.umesh.mentalhealthpolaris.services;

import com.umesh.mentalhealthpolaris.dto.AssessmentDto;
import com.umesh.mentalhealthpolaris.model.Assessment;
import com.umesh.mentalhealthpolaris.model.User;
import com.umesh.mentalhealthpolaris.repository.AssessmentRepository;
import com.umesh.mentalhealthpolaris.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;

    public void submitAssessment(AssessmentDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        // Check if any rating is less than 1 or greater than 5
        if (dto.getQuestion1() < 1 || dto.getQuestion1() > 5 ||
                dto.getQuestion2() < 1 || dto.getQuestion2() > 5 ||
                dto.getQuestion3() < 1 || dto.getQuestion3() > 5 ||
                dto.getQuestion4() < 1 || dto.getQuestion4() > 5 ||
                dto.getQuestion5() < 1 || dto.getQuestion5() > 5) {
            throw new IllegalArgumentException("Ratings must be between 1 and 5 for all questions.");
        }

        double average = (dto.getQuestion1() + dto.getQuestion2() + dto.getQuestion3() + dto.getQuestion4() + dto.getQuestion5()) / 5.0;

        Assessment assessment = new Assessment();
        assessment.setQuestion1(dto.getQuestion1());
        assessment.setQuestion2(dto.getQuestion2());
        assessment.setQuestion3(dto.getQuestion3());
        assessment.setQuestion4(dto.getQuestion4());
        assessment.setQuestion5(dto.getQuestion5());
        assessment.setAverageScore(average);
        assessment.setUser(user);
        assessmentRepository.save(assessment);
    }

    public List<Assessment> getAssessmentsForCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        return assessmentRepository.findByUser(user);
    }

    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

}
