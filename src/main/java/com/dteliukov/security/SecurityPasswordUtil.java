package com.dteliukov.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SecurityPasswordUtil {

    public static String getSecuredPassword(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(password.getBytes(UTF_8));
        return bytesToHex(result);
    }

    public static boolean verifyPassword(String password, String securedPassword) {
        return getSecuredPassword(password).equalsIgnoreCase(securedPassword);
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
