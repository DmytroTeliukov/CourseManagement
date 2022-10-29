package com.dteliukov.dao;

import com.dteliukov.model.Course;
import com.dteliukov.model.Group;
import com.dteliukov.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface GroupDao {
    void createGroup(Group group);
    void deleteGroup(Long id);
    void addStudent(Student student);
    void addCourse(Course course);
    Collection<Group> retrieveGroups();
    Optional<Group> get(Long id);
    Optional<Group> getByName(String name);
}
