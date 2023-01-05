package com.dteliukov.migration;

import com.dteliukov.dao.CourseDao;
import com.dteliukov.dao.UserDao;
import com.dteliukov.dao.mongodb.CourseMongoDBDao;
import com.dteliukov.dao.mongodb.UserMongoDBDao;
import com.dteliukov.dao.mysql.CourseMySqlDao;
import com.dteliukov.dao.mysql.UserMySqlDao;
import com.dteliukov.enums.Role;
import com.dteliukov.security.SecurityPasswordUtil;
import org.junit.jupiter.api.*;

public class MigrationTest {
    private static UserDao userMongoDBDao;
    private static UserDao userMySQLDao;

    private static CourseDao courseMongoDBDao;
    private static CourseDao courseMySQLDao;

    @BeforeAll
    static void setUpMigrationTest() {
        userMongoDBDao = new UserMongoDBDao();
        userMySQLDao = new UserMySqlDao();
        courseMongoDBDao = new CourseMongoDBDao();
        courseMySQLDao = new CourseMySqlDao();
    }


    @Test
    @Order(1)
    @DisplayName("Migrate users from mysql to mongodb")
    void migrateUsersFromMysqlToMongodb() {
        var dataFromMySQL = userMySQLDao.retrieveUsers();
        dataFromMySQL.forEach(user -> userMongoDBDao.registerUser(userMySQLDao.getUserByEmail(user.getEmail()).get()));
        var dataFromMongoDB = userMongoDBDao.retrieveUsers();

        Assertions.assertEquals(dataFromMySQL.size(), dataFromMongoDB.size());
    }

    @Test
    @Order(2)
    @DisplayName("Migrate courses from mongodb to mysql")
    void migrateCoursesFromMongodbToMysql() {
        var dataFromMongoDB = courseMongoDBDao.retrieveCourses();
        dataFromMongoDB.forEach(data -> userMySQLDao.registerUser(data.getTeacher()
                .password(SecurityPasswordUtil.getSecuredPassword("teacher")).role(Role.TEACHER)));
        dataFromMongoDB.forEach(data -> courseMySQLDao.createCourse(data));
        var dataFromMySQL = courseMySQLDao.retrieveCourses();

        Assertions.assertEquals(dataFromMongoDB.size(), dataFromMySQL.size());
    }

}
