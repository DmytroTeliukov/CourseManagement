package com.dteliukov.utils;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {
    public static int generateId() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }
}
