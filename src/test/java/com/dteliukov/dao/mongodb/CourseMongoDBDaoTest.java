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
    @Order(1)
    @DisplayName("Get course by name successfully")
    void getCourseByNameSuccessfully() {
        var course = courseDao.getByName("testCourse");

        assertTrue(course.isPresent());
    }

    @Test
    @Order(2)
    @DisplayName("Fail getting course by name")
    void failGettingCourseByName() {
        var course = courseDao.getByName("testWrongCourse");

        assertTrue(course.isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Add material to course")
    void addMaterialToCourse() {
        courseDao.addMaterial(prototypeMaterial, courseId);
        var getCourseMaterials = courseDao.getDetail(courseId).get().getMaterials();
        materialId = getCourseMaterials.stream()
                .filter(material -> material.equals(prototypeMaterial))
                .findFirst().get().getId();

        assertTrue(getCourseMaterials.contains(prototypeMaterial));
    }

    @Test
    @Order(4)
    @DisplayName("Add task to course")
    void addTaskToCourse() {
        courseDao.addTask(prototypeTask, courseId);
        var getCourseTasks = courseDao.getDetail(courseId).get().getTasks();
        taskId = getCourseTasks.stream()
                .filter(task -> task.equals(prototypeTask))
                .findFirst().get().getId();

        assertTrue(getCourseTasks.contains(prototypeTask));
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

    @Test
    @Order(7)
    @DisplayName("Retrieve course detail successfully")
    void retrieveCourseDetailSuccessfully() {
        var courses = courseDao.getDetail(courseId);

        assertFalse(courses.isEmpty());
    }

    @Test
    @Order(8)
    @DisplayName("Fail retrieving course detail")
    void failRetrievingCourseDetail() {
        var courses = courseDao.getDetail(Long.MAX_VALUE);

        assertTrue(courses.isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName("Edit name of course")
    void editNameCourse() {
        var course = courseDao.getByName(prototypeCourse.getName());
        var editedCourse = course.get().name(editedCourseName);
        courseDao.editCourse(editedCourse);
        var updatedCourse = courseDao.getByName(editedCourseName);
        courseDao.editCourse(prototypeCourse.id(editedCourse.getId()));

        assertEquals(editedCourse, updatedCourse.get());
    }

    @Test
    @Order(10)
    @DisplayName("Edit task on course")
    void editTaskOnCourse() {
        var course = courseDao.getDetail(courseId);
        var editedTask = course.get().getTasks().stream()
                .filter(task -> task.equals(prototypeTask))
                .findFirst().get().theme("updatedTheme");
        courseDao.editTask(editedTask);
        var updatedTask = courseDao.getDetail(courseId).get()
                .getTasks().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst().get();

        assertEquals(editedTask, updatedTask);
    }

    @Test
    @Order(11)
    @DisplayName("Retrieve courses of student")
    void retrieveCoursesStudent() {
        var courses = courseDao.retrieveCoursesByStudentEmail(prototypeStudent.getEmail());

        assertFalse(courses.isEmpty());
    }

    @AfterAll
    static void deletePrototypes() {
        courseDao.deleteMaterial(materialId);
        courseDao.deleteTask(taskId);
        courseDao.removeStudent(prototypeStudent.getEmail(), courseId);
        courseDao.deleteCourse(courseDao.getByName(prototypeCourse.getName()).get().getId());
        userDao.deleteUser(prototypeTeacher.getEmail());
        userDao.deleteUser(prototypeStudent.getEmail());
    }
}