package com.dteliukov.utils;

import java.util.UUID;

public class IdGenerator {
    public static Long generateId() {
        UUID uuid = UUID.randomUUID();
        long lo = uuid.getLeastSignificantBits();
        long hi = uuid.getMostSignificantBits();
        lo = (lo >> (64-1)) ^ lo;
        hi = (hi >> (64-1)) ^ hi;
        String s = String.format("%010d", Math.abs(hi) + Math.abs(lo));
        return Long.valueOf(s.substring(s.length() - 10));
    }
}
