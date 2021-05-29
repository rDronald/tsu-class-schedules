package com.rdronald.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    BACHELOR_DEGREE("Бакалавриат"), MASTER_DEGREE("Магистратура");

    private final String name;
}
