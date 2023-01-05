package com.dteliukov.service.proxy;

import com.dteliukov.enums.Role;
import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.AuthorizedUser;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;
import com.dteliukov.service.UserService;
import com.dteliukov.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class UserServiceProxy implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceProxy.class);
    private final UserServiceImpl service;
    private final Role role;

    public UserServiceProxy(Role role) {
        this.role = role;
        service = new UserServiceImpl();
    }

    @Override
    public void register(User user) {
        logger.info("Get access to method: register()");
        service.register(user);
    }

    @Override
    public AuthorizedUser login(UnauthorizedUser user) {
        logger.info("Get access to method: login()");
        return service.login(user);
    }

    @Override
    public void editProfile(User user) throws AccessDeniedException {
        if (role == Role.STUDENT || role == Role.TEACHER || role == Role.ADMIN) {
            logger.info("Get access to method: editProfile()");
            service.editProfile(user);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteProfile(String email) throws AccessDeniedException {
        if (role == Role.STUDENT || role == Role.TEACHER || role == Role.ADMIN) {
            service.deleteProfile(email);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public Collection<User> retrieveUsers() throws AccessDeniedException {
        if (role == Role.ADMIN) {
            logger.info("Get access to method: retrieveUsers()");
            return service.retrieveUsers();
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public User getUserProfile(String email) throws AccessDeniedException {
        if (role == Role.STUDENT || role == Role.TEACHER || role == Role.ADMIN) {
            logger.info("Get access to method: getUserProfile()");
            return service.getUserProfile(email);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }


}
