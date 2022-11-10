package com.dteliukov.dao;

import com.dteliukov.model.*;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Optional<AuthorizedUser> login(UnauthorizedUser user);
    void registerUser(User user);
    void editUser(User user);
    void deleteUser(String email);
    Collection<User> retrieveUsers();
    Optional<User> getUserByEmail(String email);
}
