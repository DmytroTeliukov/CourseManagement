package com.dteliukov.patterns;

import com.dteliukov.dao.*;
import com.dteliukov.enums.AnswerStatus;
import com.dteliukov.enums.Role;
import com.dteliukov.model.*;
import com.dteliukov.notification.CourseNotification;
import com.dteliukov.notification.Student;
import com.dteliukov.review.Review;
import com.dteliukov.security.SecurityPasswordUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MementoTest {
    private static UserDao userDao;
    private static CourseDao courseDao;
    private static AnswerDao answerDao;

    private static User teacher;
    private static User student;
    private static Course course;
    private static Review review;
    private static DaoRepository daoRepository = DaoFactory.getRepository(TypeDao.MYSQL);
    private static Task task;
    private static Answer answer;
    private static List<Student> students;
    private static long courseId;
    private static long taskId;

    private static long answerId;

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
        courseId = courseDao.getByName(course.getName()).get().getId();

        student = new User()
                .lastname(faker.name().lastName())
                .firstname(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .password(SecurityPasswordUtil.getSecuredPassword(faker.internet().password()))
                .role(Role.STUDENT);


        userDao.registerUser(student);
        courseDao.registerStudent(student.getEmail(), courseId);

        task = new Task(null, "firstTheme", "firstDescription",
                LocalDateTime.now().toString(), LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        courseDao.addTask(task, courseId);

        taskId = courseDao.getDetail(courseId).get().getTasks().get(0).getId();
        answerDao = daoRepository.getAnswerDao();
        answer = new Answer().student(student)
                .filePath("D:/answer")
                .sent(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(AnswerStatus.WAITING);
        answerDao.addAnswer(answer, taskId);
        answerId = answerDao.retrieveByTask(taskId).stream().toList().get(0).getId();
        review = new Review(answer.id(answerId), TypeDao.MYSQL);
    }

    @AfterEach
    void checkRecords() {
        review.getRecords().forEach(System.out::println);
    }

    @Order(1)
    @Test
    @DisplayName("1. Give mark")
    void giveMarkAndSave() {
        review.setMark(95);
        review.save();

        var updatedAnswer = answerDao.get(answerId).get();
        System.out.println("Saved record: " + updatedAnswer);

        review.cancel();
        updatedAnswer = answerDao.get(answerId).get();
        System.out.println("Restored record: " + updatedAnswer);
    }

    @Order(2)
    @Test
    @DisplayName("2. Give mark and comment")
    void giveCommentAndSave() {
        review.setMark(95);
        review.save();

        var updatedAnswer = answerDao.get(answerId).get();
        System.out.println("Saved record 1: " +updatedAnswer);

        review.setComment("Good work!!!");
        review.save();

        updatedAnswer = answerDao.get(answerId).get();
        System.out.println("Saved record 2: " +updatedAnswer);

        review.cancel();
        updatedAnswer = answerDao.get(answerId).get();
        System.out.println("Restored record 1: " + updatedAnswer);

        review.cancel();
        updatedAnswer = answerDao.get(answerId).get();
        System.out.println("Restored record 2: " + updatedAnswer);

        review.setMark(95);
        review.save();
        review.cancel();
        review.cancel();
        updatedAnswer = answerDao.get(answerId).get();
        System.out.println("Restored record 2: " + updatedAnswer);
    }


    @AfterAll
    static void deletePrototypes() {
        review.clearRecords();
        answerDao.deleteAnswer(answerId);
        courseDao.deleteTask(taskId);
        courseDao.removeStudent(student.getEmail(), courseId);
        userDao.deleteUser(student.getEmail());
        courseDao.deleteCourse(courseId);
        userDao.deleteUser(teacher.getEmail());
    }
}
