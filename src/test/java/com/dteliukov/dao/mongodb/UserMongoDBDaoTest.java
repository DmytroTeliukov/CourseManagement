package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.DaoFactory;
import com.dteliukov.dao.DaoRepository;
import com.dteliukov.dao.TypeDao;
import com.dteliukov.dao.UserDao;
import com.dteliukov.enums.Role;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;
import com.dteliukov.security.SecurityPasswordUtil;
import org.junit.jupiter.api.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserMongoDBDaoTest {
    private static UserDao dao;
    private static User prototypeUser;

    private static final String password = "user";

    @BeforeAll
    static void setUpUserDaoTest() {
        DaoRepository repository = DaoFactory.getRepository(TypeDao.MONGODB);
        dao = Objects.requireNonNull(repository).getUserDao();
        prototypeUser = new User("testLastname", "testFirstname",
                "teststudent@gmail", SecurityPasswordUtil.getSecuredPassword(password), Role.STUDENT);

        dao.registerUser(prototypeUser);
    }

    @Test
    @Order(1)
    @DisplayName("Create user successfully")
    void createStudentSuccessfully() {
        var user = dao.getUserByEmail(prototypeUser.getEmail());
        assertTrue(user.isPresent());
        assertEquals(user.get(), prototypeUser);
    }

    @Test
    @Order(2)
    @DisplayName("Success login")
    void login() {
        var authorizedUser =
                dao.login(new UnauthorizedUser(prototypeUser.getEmail(), password));

        assertTrue(authorizedUser.isPresent());
    }

    @Test
    @Order(4)
    @DisplayName("Failed login by password")
    void errorPasswordLogin() {
        var errorPasswordAuthorizedUser =
                dao.login(new UnauthorizedUser("admin@gmail.com", "admin2"));

        assertTrue(errorPasswordAuthorizedUser.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Failed login by unknown data")
    void errorUserLogin() {
        var errorEmailAuthorizedUser =
                dao.login(new UnauthorizedUser(prototypeUser.getEmail(), "admin2"));

        assertTrue(errorEmailAuthorizedUser.isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve all users")
    void retrieveUsers() {
        dao.retrieveUsers().forEach(System.out::println);
    }

    @Test
    @Order(7)
    @DisplayName("Get user profile successfully")
    void getUserProfileSuccessfully() {
        var user = dao.getUserByEmail(prototypeUser.getEmail());
        assertTrue(user.isPresent());
    }

    @Test
    @Order(8)
    @DisplayName("Fail getting user profile")
    void failGetUserProfile() {
        var user = dao.getUserByEmail("admin2@gmail.com");
        assertTrue(user.isEmpty());
    }



    @Test
    @Order(13)
    @DisplayName("Update user's data successfully")
    void updateStudentSuccessfully() {
        var user = dao.getUserByEmail(prototypeUser.getEmail());
        var editedUser = user.get().firstname("updatedTestFirstname").clone();
        dao.editUser(editedUser);
        var updatedUser = dao.getUserByEmail(prototypeUser.getEmail());
        assertEquals(editedUser, updatedUser.get());
    }

    @AfterAll
    static void deletePrototypeUsers() {
        dao.deleteUser(prototypeUser.getEmail());
    }

}