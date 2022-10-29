package com.dteliukov.dao.mysql;

import com.dteliukov.dao.GroupDao;
import com.dteliukov.model.Course;
import com.dteliukov.model.Group;
import com.dteliukov.model.Student;

import java.util.Collection;
import java.util.Optional;

public class GroupMySqlDao implements GroupDao {
    @Override
    public void createGroup(Group group) {

    }

    @Override
    public void deleteGroup(Long id) {

    }

    @Override
    public void addStudent(Student student) {

    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public Collection<Group> retrieveGroups() {
        return null;
    }

    @Override
    public Optional<Group> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Group> getByName(String name) {
        return Optional.empty();
    }
}
