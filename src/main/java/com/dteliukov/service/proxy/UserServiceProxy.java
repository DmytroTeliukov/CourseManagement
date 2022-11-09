package com.dteliukov.service.proxy;

import com.dteliukov.enums.Role;
import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.AuthorizedUser;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;
import com.dteliukov.service.UserService;
import com.dteliukov.service.impl.UserServiceImpl;

public class UserServiceProxy implements UserService {

    private final UserServiceImpl service;
    private final Role role;

    public UserServiceProxy(Role role) {
        this.role = role;
        service = new UserServiceImpl();
    }

    @Override
    public void registry(User user) {
        this.service.registry(user);
    }

    @Override
    public AuthorizedUser login(UnauthorizedUser user) {
        return service.login(user);
    }

    @Override
    public void editProfile(User user) throws AccessDeniedException {
        if (role == Role.STUDENT || role == Role.TEACHER || role == Role.ADMIN) {
            service.editProfile(user);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteProfile(String email) throws AccessDeniedException {
        if (role == Role.STUDENT || role == Role.TEACHER || role == Role.ADMIN) {
            service.deleteProfile(email);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void retrieveUsers() throws AccessDeniedException {
        if (role == Role.ADMIN) {
            service.retrieveUsers();
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void getUserProfile(String email) throws AccessDeniedException {
        if (role == Role.STUDENT || role == Role.TEACHER || role == Role.ADMIN) {
            service.getUserProfile(email);
        } else {
            throw new AccessDeniedException();
        }
    }


}
