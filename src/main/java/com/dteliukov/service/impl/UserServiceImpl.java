package com.dteliukov.service.impl;

import com.dteliukov.model.AuthorizedUser;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;
import com.dteliukov.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void registry(User user) {

    }

    @Override
    public AuthorizedUser login(UnauthorizedUser user) {
        return null;
    }

    @Override
    public void editProfile(User user) {

    }

    @Override
    public void deleteProfile(String email) {

    }

    @Override
    public void retrieveUsers() {

    }

    @Override
    public void getUserProfile(String email) {

    }
}
