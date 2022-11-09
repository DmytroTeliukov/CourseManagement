package com.dteliukov.service.impl;

import com.dteliukov.model.*;
import com.dteliukov.notification.Student;
import com.dteliukov.service.CourseService;

import java.util.Collection;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {
    @Override
    public void createCourse(Course course) {

    }

    @Override
    public void registryStudent(User student, Long courseId) {

    }

    @Override
    public void addTask(Task task, Long courseId) {

    }

    @Override
    public void addMaterial(Material material, Long courseId) {

    }

    @Override
    public void editTeacherByEmail(String email) {

    }

    @Override
    public void editCourse(Course course) {

    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public void removeStudent(Long studentId) {

    }

    @Override
    public void deleteCourse(Long courseId) {

    }

    @Override
    public void deleteTask(Long taskId) {

    }

    @Override
    public void deleteMaterial(Long materialId) {

    }

    @Override
    public Collection<Course> retrieveCourses() {
        return null;
    }

    @Override
    public Collection<Student> retrieveStudents(Long courseId) {
        return null;
    }

    @Override
    public Optional<CourseDetail> getDetail(Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> getByName(String name) {
        return Optional.empty();
    }
}
