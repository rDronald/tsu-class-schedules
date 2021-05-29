package com.rdronald.front.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private String id;
    private Integer number;
    private String description;
    private LocalDate date;
}
