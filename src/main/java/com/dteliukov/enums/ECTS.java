package com.dteliukov.enums;

public enum ECTS {
    F(1),
    FX(35),
    E(60),
    D(67),
    C(75),
    B(90),
    A(96);
    private final int minRangeMark;

    ECTS(int minRangeMark) {
        this.minRangeMark = minRangeMark;
    }
}
