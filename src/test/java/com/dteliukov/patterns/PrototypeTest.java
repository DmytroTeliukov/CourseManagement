package com.dteliukov.patterns;

import com.dteliukov.enums.Role;
import com.dteliukov.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PrototypeTest {
    @Test
    @DisplayName("Create object and clone to another")
    void createObjectAndCloneTtoAnother() {
        User originalUser = new User("testLastname", "testFirstname",
                "test@gmail.com", "test", Role.ADMIN);
        User clonedUser = originalUser.clone();

        System.out.println("Original user (" + System.identityHashCode(originalUser) + "):" + originalUser);
        System.out.println("Cloned user (" + System.identityHashCode(clonedUser) + "):" + clonedUser);

        Assertions.assertEquals(originalUser, clonedUser);
        Assertions.assertNotSame(originalUser, clonedUser);
    }
}
