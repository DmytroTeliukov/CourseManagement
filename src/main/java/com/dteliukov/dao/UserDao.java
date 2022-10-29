package com.dteliukov.dao;

import com.dteliukov.model.*;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Optional<AuthorizedUser> login(UnauthorizedUser user);
    void createStudentProfile(Student student);
    void createTeacherProfile(Teacher student);
    void editStudentProfile(Student student);
    void editTeacherProfile(Teacher student);
    void deleteProfile(String email);
    Collection<User> retrieveUsers();
    Optional<User> getProfileByEmail(String email);
}
