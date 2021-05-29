package com.rdronald.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Group {
    PMIM_2001A("ПМИм-2001а",Institute.IMPIT,Status.MASTER_DEGREE, 1);

    private final String name;
    private final Institute institute;
    private final Status status;
    private final Integer course;
}
