package com.dteliukov.service;

import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;

import java.util.Collection;
import java.util.Optional;

public interface CourseService {
    void createCourse(Course course) throws AccessDeniedException;
    void registryStudent(User student, Long courseId) throws AccessDeniedException;
    void addTask(Task task, Long courseId);
    void addMaterial(Material material, Long courseId);
    void editTeacherByEmail(String email);
    void editCourse(Course course);
    void editTask(Task task);
    void removeStudent(Long studentId);
    void deleteCourse(Long courseId);
    void deleteTask(Long taskId);
    void deleteMaterial(Long materialId);
    Collection<Course> retrieveCourses();
    Collection<Student> retrieveStudents(Long courseId);
    Optional<CourseDetail> getDetail(Long courseId);
    Optional<Course> getByName(String name);

}
