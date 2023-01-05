package com.dteliukov.patterns;

import com.dteliukov.enums.Role;
import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.service.UserService;
import com.dteliukov.service.proxy.UserServiceProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyTest {

    @Test
    @DisplayName("Retrieve users by role ADMIN")
    void retrieveUsersByRoleADMIN() {
        UserService adminUserService = new UserServiceProxy(Role.ADMIN);

        Assertions.assertDoesNotThrow(adminUserService::retrieveUsers);
    }

    @Test
    @DisplayName("Retrieve users by role TEACHER")
    void retrieveUsersByRoleTEACHER() {
        UserService teacherUserService = new UserServiceProxy(Role.TEACHER);

        Assertions.assertThrows(AccessDeniedException.class, teacherUserService::retrieveUsers);
    }
}
