package com.dteliukov.service.impl;

import com.dteliukov.config.DaoRepositoryConfiguration;
import com.dteliukov.dao.CourseDao;
import com.dteliukov.dao.UserDao;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;
import com.dteliukov.service.CourseService;

import java.util.Collection;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;

    public CourseServiceImpl() {
        courseDao = DaoRepositoryConfiguration.getRepository().getCourseDao();
    }
    @Override
    public void createCourse(Course course) {
        courseDao.createCourse(course);
    }

    @Override
    public void registryStudent(User student, Long courseId) {
        courseDao.registerStudent(student.getEmail(), courseId);
    }

    @Override
    public void addTask(Task task, Long courseId) {
        courseDao.addTask(task, courseId);
    }

    @Override
    public void addMaterial(Material material, Long courseId) {
        courseDao.addMaterial(material, courseId);
    }


    @Override
    public void editCourse(Course course) {
        courseDao.editCourse(course);
    }

    @Override
    public void editTask(Task task) {
        courseDao.editTask(task);
    }

    @Override
    public void removeStudent(String email, Long courseId) {
        courseDao.removeStudent(email, courseId);
    }

    @Override
    public Collection<Course> retrieveCoursesByStudentEmail(String email) {
        return courseDao.retrieveCoursesByStudentEmail(email);
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseDao.deleteCourse(courseId);
    }

    @Override
    public void deleteTask(Long taskId) {
        courseDao.deleteTask(taskId);
    }

    @Override
    public void deleteMaterial(Long materialId) {
        courseDao.deleteMaterial(materialId);
    }

    @Override
    public Collection<Course> retrieveCourses() {
        return courseDao.retrieveCourses();
    }

    @Override
    public Collection<Student> retrieveStudents(Long courseId) {
        return courseDao.retrieveStudents(courseId);
    }

    @Override
    public Optional<CourseDetail> getDetail(Long courseId) {
        return courseDao.getDetail(courseId);
    }

    @Override
    public Optional<Course> getByName(String name) {
        return courseDao.getByName(name);
    }
}
