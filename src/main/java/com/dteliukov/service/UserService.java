package com.dteliukov.service;

import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.AuthorizedUser;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;

public interface UserService {
    void register(User user);
    AuthorizedUser login(UnauthorizedUser user);
    void editProfile(User user) throws AccessDeniedException;
    void deleteProfile(String email) throws AccessDeniedException;
    void retrieveUsers() throws AccessDeniedException;
    void getUserProfile(String email) throws AccessDeniedException;
}
