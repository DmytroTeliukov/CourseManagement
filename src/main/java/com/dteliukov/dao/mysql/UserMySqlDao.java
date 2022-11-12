package com.dteliukov.dao.mysql;

import com.dteliukov.dao.UserDao;
import com.dteliukov.dao.schema.Columns;
import com.dteliukov.enums.Role;
import com.dteliukov.model.*;
import com.dteliukov.security.SecurityPasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public class UserMySqlDao implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserMySqlDao.class);

    @Override
    public Optional<AuthorizedUser> login(UnauthorizedUser user) {
        String loginScript = getLoginScript();
        logger.info("Get login sql script: " + loginScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(loginScript)) {
                preparedStatement.setString(1, user.email());
                logger.info("Authorize user: " + user);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String role = resultSet.getString(Columns.role);
                        String password = resultSet.getString(Columns.password);
                        if (SecurityPasswordUtil.verifyPassword(user.password(), password)) {
                            AuthorizedUser authorizedUser = new AuthorizedUser(user.email(), Role.getRole(role));
                            logger.info("Found user: " + authorizedUser);
                            return Optional.of(authorizedUser);
                        } else {
                            logger.error("User with email " + user.email() + " was not authorized!");
                            return Optional.empty();
                        }
                    } else {
                        logger.error("User with email " + user.email() + " was not found!");
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void registerUser(User user) {
        String createUserScript = createUserScript();
        logger.info("Registry user sql script: " + createUserScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(createUserScript)) {
                preparedStatement.setString(1, user.getLastname());
                preparedStatement.setString(2, user.getFirstname());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, SecurityPasswordUtil.getSecuredPassword(user.getPassword()));
                preparedStatement.setString(5, user.getRole().name());
                preparedStatement.executeUpdate();
                logger.info("User inserted into database: " + user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void editUser(User user) {
        String editUserScript = editUserScript();
        logger.info("Edit user sql script: " + editUserScript);
        try (PreparedStatement preparedStatement =
                     MySqlConnection.getConnection().prepareStatement(editUserScript)) {
            preparedStatement.setString(1, user.getLastname());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.executeUpdate();
            logger.info("Updated user inserted into database: " + user);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


    @Override
    public void deleteUser(String email) {
        String deleteScript = getDeleteUserScript();
        logger.info("Delete user sql script: " + deleteScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteScript)) {
                preparedStatement.setString(1, email);
                preparedStatement.executeUpdate();
                logger.info("User " + email + " deleted successfully!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Collection<User> retrieveUsers() {
        String retrieveUsersScript = getUsersScript();
        Collection<User> collection = new LinkedList<>();
        logger.info("Get users sql script: " + retrieveUsersScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveUsersScript)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User prototypeBuilderUser = new User()
                                .lastname(resultSet.getString(Columns.lastname))
                                .firstname(resultSet.getString(Columns.firstname))
                                .email(resultSet.getString(Columns.email))
                                .role(Role.getRole(resultSet.getString(Columns.role)));
                        logger.info("Get user: " + prototypeBuilderUser);
                        collection.add(prototypeBuilderUser.clone());
                    }
                }
                logger.info("Get users from mysql database!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return collection;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String getUserProfileScript = getUserProfileScript();
        logger.info("Get user profile sql script: " + getUserProfileScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUserProfileScript)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User prototypeBuilderUser = new User()
                                .lastname(resultSet.getString(Columns.lastname))
                                .firstname(resultSet.getString(Columns.firstname))
                                .email(email)
                                .password(resultSet.getString(Columns.password))
                                .role(Role.getRole(resultSet.getString(Columns.role)));
                        logger.info("Get user profile : " + prototypeBuilderUser);
                        return Optional.of(prototypeBuilderUser.clone());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }


    private String getLoginScript() {
        return "select role, password from `user` where email = ?;";
    }

    private String createUserScript() {
        return "insert into `user`(lastname, firstname, email, password, role) values(?,?,?,?,?);";
    }

    private String editUserScript() {
        return "update `user` set lastname = ?, firstname = ?, password = ? where email = ?";
    }

    private String getDeleteUserScript() {
        return "delete from `user` where email = ?;";
    }

    private String getUsersScript() {
        return "select lastname, firstname, email, role from `user`;";
    }

    private String getUserProfileScript() {
        return "select lastname, firstname, password, role from `user` where email = ?;";
    }

}
