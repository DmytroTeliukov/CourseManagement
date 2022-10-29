package com.dteliukov.dao;

import com.dteliukov.model.*;

import java.util.Collection;
import java.util.Optional;

public interface CourseDao {
    void createCourse(Course course);

    void addTeacherByUsername(String username);
    void addMaterial(Material material);
    void addTask(Task task);

    void deleteMaterial(Long id);
    void editTask(Task task);
    void editTeacherByUsername(String username);

    void deleteTask(Long id);
    void editCourse(Course course);
    void deleteAnswer(Long idCourse);

    Collection<Course> retrieveCourses();
    Collection<Course> retrieveCoursesByTeacher(Teacher teacher);

    Optional<CourseDetail> getDetail(Long id);
    Optional<Course> getByName(String name);
}
