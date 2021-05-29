package com.rdronald.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    private String id;
    @ManyToOne
    private StudyWeek studyWeek;
    @Enumerated(EnumType.STRING)
    @Column(name = "group_name")
    private Group group;
    @ManyToOne
    private Lesson lesson;
}
