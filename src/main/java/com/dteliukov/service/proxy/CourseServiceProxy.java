package com.dteliukov.service.proxy;

import com.dteliukov.enums.Role;
import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;
import com.dteliukov.service.CourseService;
import com.dteliukov.service.impl.CourseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Optional;

public class CourseServiceProxy implements CourseService {
    private static final Logger logger = LogManager.getLogger(CourseServiceProxy.class);

    private final CourseServiceImpl service;
    private final Role role;

    public CourseServiceProxy(Role role) {
        this.role = role;
        service = new CourseServiceImpl();
    }

    @Override
    public void createCourse(Course course) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: createCourse()");
            service.createCourse(course);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void registryStudent(User student, Long courseId) throws AccessDeniedException {
        if (role == Role.STUDENT || role == Role.TEACHER) {
            logger.info("Get access to method: registryStudent()");
            service.registryStudent(student, courseId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void addTask(Task task, Long courseId) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: addTask()");
            service.addTask(task, courseId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void addMaterial(Material material, Long courseId) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: addMaterial()");
            service.addMaterial(material, courseId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }


    @Override
    public void editCourse(Course course) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: editCourse()");
            service.editCourse(course);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void editTask(Task task) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: editTask()");
            service.editTask(task);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void removeStudent(String email, Long courseId) throws AccessDeniedException {
        if (role == Role.TEACHER || role == Role.STUDENT) {
            logger.info("Get access to method: removeStudent()");
            service.removeStudent(email, courseId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public Collection<Course> retrieveCoursesByStudentEmail(String email) throws AccessDeniedException {
        if (role == Role.STUDENT) {
            logger.info("Get access to method: retrieveCoursesByStudentEmail()");
            return service.retrieveCoursesByStudentEmail(email);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteCourse(Long courseId) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: deleteCourse()");
            service.deleteCourse(courseId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteTask(Long taskId) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: deleteTask()");
            service.deleteTask(taskId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteMaterial(Long materialId) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: deleteMaterial()");
            service.deleteMaterial(materialId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public Collection<Course> retrieveCourses() {
        logger.info("Get access to method: retrieveCourses()");
        return service.retrieveCourses();
    }

    @Override
    public Collection<Student> retrieveStudents(Long courseId) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: retrieveStudents()");
            return service.retrieveStudents(courseId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public Optional<CourseDetail> getDetail(Long courseId) throws AccessDeniedException {
        if (role == Role.TEACHER || role == Role.STUDENT || role == Role.ADMIN) {
            logger.info("Get access to method: getDetail()");
            return service.getDetail(courseId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public Optional<Course> getByName(String name) {
        logger.info("Get access to method: getByName()");
        return service.getByName(name);
    }
}
