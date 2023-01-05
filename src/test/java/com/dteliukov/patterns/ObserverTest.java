package com.dteliukov.patterns;

import com.dteliukov.dao.*;
import com.dteliukov.enums.Role;
import com.dteliukov.model.Course;
import com.dteliukov.model.Material;
import com.dteliukov.model.Task;
import com.dteliukov.model.User;
import com.dteliukov.notification.CourseNotification;
import com.dteliukov.notification.Student;
import com.dteliukov.security.SecurityPasswordUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ObserverTest {

    private static UserDao userDao;
    private static CourseDao courseDao;

    private static User teacher;
    private static Course course;
    private static CourseNotification notification;
    private static DaoRepository daoRepository = DaoFactory.getRepository(TypeDao.MYSQL);
    private static Task task;
    private static Material material;
    private static List<Student> students;

    @BeforeAll
    static void setUp() {
        var faker = new Faker(new Locale("en"));
        userDao = daoRepository.getUserDao();
        courseDao = daoRepository.getCourseDao();
        teacher = new User()
                .lastname(faker.name().lastName())
                .firstname(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .password(SecurityPasswordUtil.getSecuredPassword(faker.internet().password()))
                .role(Role.TEACHER);
        userDao.registerUser(teacher);
        course = new Course(null, teacher, "Design Pattern");
        courseDao.createCourse(course);
        long courseId = courseDao.getByName(course.getName()).get().getId();
        notification = new CourseNotification(course.id(courseId), TypeDao.MYSQL);

        students = new LinkedList<>();
        Student student = new Student();
        for (int i = 0; i < 5; i++) {
            student.lastname(faker.name().lastName())
                    .firstname(faker.name().firstName())
                    .email(faker.internet().emailAddress())
                    .password(SecurityPasswordUtil.getSecuredPassword(faker.internet().password()))
                    .role(Role.STUDENT);
            students.add(student.clone());
        }

        for (var user : students) {
            userDao.registerUser(user);
            notification.addSubscriber(user);
        }

        task = new Task(null, "firstTheme", "firstDescription",
                LocalDateTime.now().toString(), LocalDateTime.now().toString());
        material = new Material(1L, "firstMaterial", "C:/1");
    }

    @Test
    @DisplayName("Send new material")
    void sendNewMaterial() {
        notification.setNotification(material);
    }

    @Test
    @DisplayName("Send new task")
    void sendNewTask() {
        notification.setNotification(task);
    }

    @AfterAll
    static void deletePrototypes() {
        var courseDetail = courseDao.getDetail(course.getId());
        System.out.println(courseDetail);

        for (var material : courseDetail.get().getMaterials())
            courseDao.deleteMaterial(material.getId());

        for (var task : courseDetail.get().getTasks())
            courseDao.deleteTask(task.getId());

        for (var student : students) {
            notification.removeSubscriber(student);
            userDao.deleteUser(student.getEmail());
        }
        courseDao.deleteCourse(course.getId());
        userDao.deleteUser(teacher.getEmail());

    }
}
