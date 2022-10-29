package com.dteliukov.model;

import com.dteliukov.enums.Role;

public record AuthorizedUser(String email, Role role) {}
