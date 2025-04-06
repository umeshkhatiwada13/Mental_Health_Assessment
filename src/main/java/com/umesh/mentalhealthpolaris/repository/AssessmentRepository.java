package com.umesh.mentalhealthpolaris.repository;

import com.umesh.mentalhealthpolaris.model.Assessment;
import com.umesh.mentalhealthpolaris.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByUser(User user);
}
