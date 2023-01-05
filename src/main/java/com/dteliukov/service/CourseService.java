package com.dteliukov.service;

import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;

import java.util.Collection;
import java.util.Optional;

public interface CourseService {
    void createCourse(Course course) throws AccessDeniedException;
    void registryStudent(User student, Long courseId) throws AccessDeniedException;
    void addTask(Task task, Long courseId) throws AccessDeniedException;
    void addMaterial(Material material, Long courseId) throws AccessDeniedException;
    void editCourse(Course course) throws AccessDeniedException;
    void editTask(Task task) throws AccessDeniedException;
    void removeStudent(String email, Long courseId) throws AccessDeniedException;
    Collection<Course> retrieveCoursesByStudentEmail(String email) throws AccessDeniedException;
    void deleteCourse(Long courseId) throws AccessDeniedException;
    void deleteTask(Long taskId) throws AccessDeniedException;
    void deleteMaterial(Long materialId) throws AccessDeniedException;
    Collection<Course> retrieveCourses();
    Collection<Student> retrieveStudents(Long courseId) throws AccessDeniedException;
    Optional<CourseDetail> getDetail(Long courseId) throws AccessDeniedException;
    Optional<Course> getByName(String name);

}
