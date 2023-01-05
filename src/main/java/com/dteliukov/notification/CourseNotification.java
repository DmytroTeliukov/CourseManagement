package com.dteliukov.notification;

import com.dteliukov.dao.CourseDao;
import com.dteliukov.dao.DaoFactory;
import com.dteliukov.dao.TypeDao;
import com.dteliukov.model.Course;
import com.dteliukov.model.Material;
import com.dteliukov.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CourseNotification implements Publisher<Student>{
    private static final Logger logger = LogManager.getLogger(CourseNotification.class);
    private final Course course;
    private final CourseDao courseRepository;

    private List<Student> students;

    public CourseNotification(Course course, TypeDao typeDao) {
        this.course = course;
        courseRepository = DaoFactory.getRepository(typeDao).getCourseDao();
        students = updateListStudents();
    }

    @Override
    public void addSubscriber(Student subscriber) {
        logger.info("Subscriber (" + subscriber.getEmail() + ") was added to course \"" + course.getName() + "\"");
        courseRepository.registerStudent(subscriber.getEmail(), course.getId());
        students = updateListStudents();
    }

    @Override
    public void removeSubscriber(Student subscriber) {
        logger.info("Subscriber (" + subscriber.getEmail() + ") was removed from course \"" + course.getName() + "\"");
        courseRepository.removeStudent(subscriber.getEmail(), course.getId());
        students = updateListStudents();
    }

    public void setNotification(CourseItem courseItem) {
        String notification = "Course \"" + course.getName() + "\" has published new "
                + courseItem.getClass().getSimpleName().toLowerCase();
        logger.info(notification);
        if (courseItem instanceof Material) {
            courseRepository.addMaterial((Material) courseItem, course.getId());
        } else {
            courseRepository.addTask((Task) courseItem, course.getId());
        }
        notifySubscribers(notification);
    }

    @Override
    public void notifySubscribers(String notification) {
        for (var student : students) {
            student.update(notification);
        }
    }

    private List<Student> updateListStudents() {
       return courseRepository.retrieveStudents(course.getId()).stream().toList();
    }
}
