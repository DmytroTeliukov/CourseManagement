package com.dteliukov.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Order(1)
    @DisplayName("Check set unique id")
    @RepeatedTest(10)
    void checkSetUniqueId() {
        var ids = new LinkedList<Integer>();
        for (int i = 0; i < 50000; i++) {
            ids.add(IdGenerator.generateId());
        }

        Set<Integer> idSet = new HashSet<>(ids);

        assertEquals(ids.size(), idSet.size());
    }
}