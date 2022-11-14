package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.*;
import com.dteliukov.enums.Role;
import com.dteliukov.model.Course;
import com.dteliukov.model.Material;
import com.dteliukov.model.Task;
import com.dteliukov.model.User;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CourseMongoDBDaoTest {
    private static UserDao userDao;
    private static CourseDao courseDao;
    private static User prototypeStudent;
    private static User prototypeTeacher;

    private static Material prototypeMaterial;

    private static Task prototypeTask;
    private static Course prototypeCourse;

    private static long materialId;
    private static long taskId;
    private static long courseId;
    private static String editedCourseName = "editedCourseName";

    @BeforeAll
    static void setUpCourseDaoTest() {
        DaoRepository repository = DaoFactory.getRepository(TypeDao.MONGODB);
        userDao = Objects.requireNonNull(repository).getUserDao();
        courseDao = Objects.requireNonNull(repository).getCourseDao();
        prototypeStudent = new User("testLastname", "testFirstname",
                "teststudent@gmail", "user", Role.STUDENT);
        prototypeTeacher = new User("testLastname", "testFirstname",
                "testteacher@gmail", "user", Role.TEACHER);
        prototypeCourse = new Course(null, prototypeTeacher, "testCourse");
        prototypeMaterial = new Material(null, "nameMaterial", "C:/path" );
        prototypeTask = new Task(null, "theme", "description",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString(), null);
        userDao.registerUser(prototypeStudent);
        userDao.registerUser(prototypeTeacher);
        courseDao.createCourse(prototypeCourse);
        courseId = courseDao.getByName(prototypeCourse.getName()).get().getId();
        courseDao.registerStudent(prototypeStudent.getEmail(), courseId);
    }

    @Test
    @Order(5)
    @DisplayName("Retrieve students from course successfully")
    void retrieveStudentFromCourseSuccessfully() {
        var students = courseDao.retrieveStudents(courseId);

        assertFalse(students.isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve courses successfully")
    void retrieveCoursesSuccessfully() {
        var courses = courseDao.retrieveCourses();

        assertFalse(courses.isEmpty());
    }

    @AfterAll
    static void deletePrototypes() {
        courseDao.removeStudent(prototypeStudent.getEmail(), courseId);
        courseDao.deleteCourse(courseDao.getByName(prototypeCourse.getName()).get().getId());
        userDao.deleteUser(prototypeStudent.getEmail());
        userDao.deleteUser(prototypeTeacher.getEmail());
    }



}