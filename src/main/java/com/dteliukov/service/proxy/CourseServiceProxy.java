package com.dteliukov.service.proxy;

import com.dteliukov.enums.Role;
import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;
import com.dteliukov.service.CourseService;
import com.dteliukov.service.impl.CourseServiceImpl;
import com.dteliukov.service.impl.UserServiceImpl;

import java.util.Collection;
import java.util.Optional;

public class CourseServiceProxy implements CourseService {

    private final CourseServiceImpl service;
    private final Role role;

    public CourseServiceProxy(Role role) {
        this.role = role;
        service = new CourseServiceImpl();
    }

    @Override
    public void createCourse(Course course) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            service.createCourse(course);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void registryStudent(User student, Long courseId) throws AccessDeniedException {
        if (role == Role.STUDENT) {
            service.registryStudent(student, courseId);
        } else {
            throw new AccessDeniedException();
        }
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
