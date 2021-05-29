package com.rdronald.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Institute {
    IMPIT("ИМФиИТ", "[^\"]+имфит%20{weekNumber}[^\\d][^\"]+");

    private final String name;
    private final String regex;
}
