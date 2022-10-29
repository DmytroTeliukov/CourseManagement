package com.dteliukov.dao.mysql;

import com.dteliukov.dao.UserDao;
import com.dteliukov.model.*;

import java.util.Collection;
import java.util.Optional;

public class UserMySqlDao implements UserDao {
    @Override
    public Optional<AuthorizedUser> login(UnauthorizedUser user) {
        return Optional.empty();
    }

    @Override
    public void createStudentProfile(Student student) {

    }

    @Override
    public void createTeacherProfile(Teacher student) {

    }

    @Override
    public void editStudentProfile(Student student) {

    }

    @Override
    public void editTeacherProfile(Teacher student) {

    }

    @Override
    public void deleteProfile(String email) {

    }

    @Override
    public Collection<User> retrieveUsers() {
        return null;
    }

    @Override
    public Optional<User> getProfileByEmail(String email) {
        return Optional.empty();
    }
}
