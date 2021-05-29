package com.rdronald.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyWeek {
    @Id
    private Integer number;
    private LocalDate firstDay;
    private LocalDate lastDay;
}
