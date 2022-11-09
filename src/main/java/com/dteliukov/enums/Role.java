package com.dteliukov.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ADMIN, TEACHER, STUDENT;

    public static Role getRole(String name) {
        return Arrays.stream(values())
                .filter(role -> role.name().equals(name))
                .findFirst().orElseThrow();
    }
}
