package com.dteliukov.exception;

public class AccessDeniedException extends Exception {
    public static final String ACCESS_DENIED_URL = "/403.jsp";

    @Override
    public String getMessage() {
        return "Access denied: you have not rules to the resource!";
    }

}
