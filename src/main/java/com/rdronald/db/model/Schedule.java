package com.rdronald.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    private String id;
    private LocalDate firstDayWeek;
    private LocalDate lastDayWeek;
    @ManyToOne
    private Group group;
    @ManyToOne
    private Lesson lesson;
}
