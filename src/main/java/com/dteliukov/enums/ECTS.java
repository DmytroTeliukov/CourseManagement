package com.dteliukov.enums;

import java.util.Arrays;

public enum ECTS {
    A(96),
    B(90),
    C(75),
    D(67),
    E(60),
    FX(35),
    F(1);
    private final int minRangeMark;

    ECTS(int minRangeMark) {
        this.minRangeMark = minRangeMark;
    }

    public static ECTS getECTSMark(int mark) {
        return Arrays.stream(values())
                .filter(ects -> ects.minRangeMark <= mark)
                .findFirst()
                .orElseThrow();
    }
}
