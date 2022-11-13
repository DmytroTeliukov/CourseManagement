package com.dteliukov.dao;

import com.dteliukov.model.*;
import com.dteliukov.notification.Student;

import java.util.Collection;
import java.util.Optional;

public interface CourseDao {
    void createCourse(Course course);
    void addMaterial(Material material, Long courserId);
    void addTask(Task task, Long courseId);
    void deleteMaterial(Long id);
    void registerStudent(String email, Long courseId);
    void removeStudent(String email, Long courseId);
    void editTask(Task task);
    void deleteTask(Long id);
    void editCourse(Course course);
    void deleteCourse(Long id);
    Collection<Course> retrieveCourses();
    Collection<Student> retrieveStudents(Long courseId);
    Optional<CourseDetail> getDetail(Long courseId);
    Optional<Course> getByName(String name);

}
