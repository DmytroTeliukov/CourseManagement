package com.dteliukov.dao.mysql;

import com.dteliukov.dao.CourseDao;
import com.dteliukov.model.*;

import java.util.Collection;
import java.util.Optional;

public class CourseMySqlDao implements CourseDao {
    @Override
    public void createCourse(Course course) {

    }

    @Override
    public void addTeacherByUsername(String username) {

    }

    @Override
    public void addMaterial(Material material) {

    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void deleteMaterial(Long id) {

    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public void editTeacherByUsername(String username) {

    }

    @Override
    public void deleteTask(Long id) {

    }

    @Override
    public void editCourse(Course course) {

    }

    @Override
    public void deleteAnswer(Long idCourse) {

    }

    @Override
    public Collection<Course> retrieveCourses() {
        return null;
    }

    @Override
    public Collection<Course> retrieveCoursesByTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public Optional<CourseDetail> getDetail(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> getByName(String name) {
        return Optional.empty();
    }
}
