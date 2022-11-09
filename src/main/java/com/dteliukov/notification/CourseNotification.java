package com.dteliukov.notification;

import com.dteliukov.dao.CourseDao;
import com.dteliukov.dao.DaoManager;
import com.dteliukov.dao.TypeDao;
import com.dteliukov.model.Course;

import java.util.List;


public class CourseNotification implements Publisher<Student>{
    private final Course course;
    private final CourseDao courseRepository;

    private final List<Student> students;

    public CourseNotification(Course course) {
        this.course = course;
        courseRepository = DaoManager.getRepository(TypeDao.MYSQL).getCourseDao();
        students = courseRepository.retrieveStudents(course.getId()).stream().toList();
    }

    @Override
    public void addSubscriber(Student subscriber) {
        courseRepository.registryStudent(subscriber.getEmail(), course.getId());
    }

    @Override
    public void removeSubscriber(Student subscriber) {
        courseRepository.removeStudent(subscriber.getEmail(), course.getId());
    }

    public void setNotification(CourseItem courseItem) {
        String notification = "Course \"" + course.getName() + "\" has published new "
                + courseItem.getClass().getSimpleName().toLowerCase();
        notifySubscribers(notification);
    }

    @Override
    public void notifySubscribers(String notification) {
        for (var student : students) {
            student.update(notification);
        }
    }
}
