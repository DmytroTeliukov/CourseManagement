package com.dteliukov.service.impl;

import com.dteliukov.config.DaoRepositoryConfiguration;
import com.dteliukov.dao.DaoFactory;
import com.dteliukov.dao.TypeDao;
import com.dteliukov.dao.UserDao;
import com.dteliukov.model.AuthorizedUser;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;
import com.dteliukov.service.UserService;

import java.util.Collection;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = DaoRepositoryConfiguration.getRepository().getUserDao();
    }
    @Override
    public void register(User user) {
        if (userDao.getUserByEmail(user.getEmail()).isEmpty()) {
            userDao.registerUser(user);
        }
    }

    @Override
    public AuthorizedUser login(UnauthorizedUser user) {
        return userDao.login(user).orElseThrow();
    }

    @Override
    public void editProfile(User user) {
        userDao.editUser(user);
    }

    @Override
    public void deleteProfile(String email) {
        userDao.deleteUser(email);
    }

    @Override
    public Collection<User> retrieveUsers() {
        return userDao.retrieveUsers();
    }

    @Override
    public User getUserProfile(String email) {
        return userDao.getUserByEmail(email).orElseThrow();
    }
}
