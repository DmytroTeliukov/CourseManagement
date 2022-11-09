package com.dteliukov.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityPasswordUtilTest {

    private static final String testPassword = "student";

    private static String securedPassword;

    @Test
    @Order(1)
    @DisplayName("Secure password")
    public void securePassword() {
        securedPassword = SecurityPasswordUtil.getSecuredPassword(testPassword);
        assertNotEquals(testPassword, securedPassword);
    }

    @Test
    @Order(2)
    @DisplayName("Verify password")
    public void verifyPassword() {
        assertTrue(SecurityPasswordUtil.verifyPassword(testPassword, securedPassword));
    }
}