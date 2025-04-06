package com.umesh.mentalhealthpolaris.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "assessment")
@Getter
@Setter
public class Assessment {
    @Id
    @GeneratedValue
    private Long id;

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

    private double averageScore;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // manual constructor
    public Assessment(Long id, int question1, int question2, int question3, int question4, int question5, double score, User user) {
        this.id = id;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.averageScore = score;
        this.user = user;
    }

    public Assessment() {

    }
}
