package com.dteliukov.dao.mysql;

import com.dteliukov.dao.*;
import com.dteliukov.enums.AnswerStatus;
import com.dteliukov.enums.ECTS;
import com.dteliukov.enums.Role;
import com.dteliukov.model.*;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AnswerMySqlDaoTest {
    private static UserDao userDao;
    private static CourseDao courseDao;

    private static AnswerDao answerDao;
    private static User prototypeStudent;
    private static User prototypeTeacher;
    private static Task prototypeTask;
    private static Course prototypeCourse;

    private static Answer prototypeAnswer;

    private static long answerId;
    private static long taskId;
    private static long courseId;

    @BeforeAll
    static void setUpAnswerDaoTest() {
        DaoRepository repository = DaoManager.getRepository(TypeDao.MYSQL);
        userDao = Objects.requireNonNull(repository).getUserDao();
        courseDao = Objects.requireNonNull(repository).getCourseDao();
        answerDao = Objects.requireNonNull(repository).getAnswerDao();
        prototypeStudent = new User("testLastname", "testFirstname",
                "teststudent@gmail", "user", Role.STUDENT);
        prototypeTeacher = new User("testLastname", "testFirstname",
                "testteacher@gmail", "user", Role.TEACHER);
        prototypeCourse = new Course(null, prototypeTeacher, "testCourse");
        prototypeTask = new Task(null, "theme", "description",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), null);
        prototypeAnswer = new Answer(null, prototypeStudent,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), null,
                null, "C:/path", 0, null, AnswerStatus.WAITING);
        userDao.registryUser(prototypeStudent);
        userDao.registryUser(prototypeTeacher);
        courseDao.createCourse(prototypeCourse);
        courseId = courseDao.getByName(prototypeCourse.getName()).get().getId();
        courseDao.registryStudent(prototypeStudent.getEmail(), courseId);
        courseDao.addTask(prototypeTask, courseId);
        taskId = courseDao.getDetail(courseId).get().getTasks().stream()
                .filter(task -> task.equals(prototypeTask))
                .findFirst().get().getId();
        answerDao.addAnswer(prototypeAnswer, taskId);
        answerId = answerDao.retrieveByTask(taskId).stream()
                .filter(answer -> answer.equals(prototypeAnswer))
                .findFirst().get().getId();
    }

    @Test
    @Order(1)
    @DisplayName("Retrieve answers by task")
    void retrieveAnswersByTask() {
        var answers = answerDao.retrieveByTask(taskId);

        assertFalse(answers.isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Get answer by id")
    void getAnswerById() {
        var answer = answerDao.get(answerId);


        assertTrue(answer.isPresent());
    }

    @Test
    @Order(3)
    @DisplayName("Review answer")
    void reviewAnswer() {
        var reviewedAnswer = answerDao.get(answerId);
        reviewedAnswer.get()
                .mark(96)
                .ectsMark(ECTS.getECTSMark(96).name())
                .checked(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(AnswerStatus.CHECKED);
        answerDao.editAnswer(reviewedAnswer.get());
        var updatedAnswer = answerDao.get(answerId);
        assertEquals(reviewedAnswer.get(), updatedAnswer.get());
    }

    @AfterAll
    static void deletePrototypes() {
        answerDao.deleteAnswer(answerId);
        courseDao.deleteTask(taskId);
        courseDao.removeStudent(prototypeStudent.getEmail(), courseId);
        courseDao.deleteCourse(courseDao.getByName(prototypeCourse.getName()).get().getId());
        userDao.deleteUser(prototypeTeacher.getEmail());
        userDao.deleteUser(prototypeStudent.getEmail());
    }

}