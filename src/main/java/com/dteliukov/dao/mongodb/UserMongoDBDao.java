package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.UserDao;
import com.dteliukov.dao.schema.Collections;
import com.dteliukov.dao.schema.Columns;
import com.dteliukov.model.AuthorizedUser;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;
import com.dteliukov.security.SecurityPasswordUtil;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.mongodb.client.model.Filters.eq;

public class UserMongoDBDao implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserMongoDBDao.class);
    private final Gson gson;

    public UserMongoDBDao() {
        gson = new Gson();
    }
    @Override
    public Optional<AuthorizedUser> login(UnauthorizedUser user) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            FindIterable<Document> filter = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.users)
                    .find(new Document(Columns.email, user.email()));
            logger.info("Authorize user: " + user);
            try (var cursor = filter.cursor()) {
                if (cursor.hasNext()) {
                    var retrievedUser = gson.fromJson(cursor.next().toJson(), User.class);
                    if (SecurityPasswordUtil.verifyPassword(user.password(), retrievedUser.getPassword())) {
                        AuthorizedUser authorizedUser = new AuthorizedUser(user.email(), retrievedUser.getRole());
                        logger.info("Found user: " + authorizedUser);
                        return Optional.of(authorizedUser);
                    } else {
                        logger.error("User with email " + user.email() + " was not authorized!");
                        return Optional.empty();
                    }
                }
                logger.error("User with email " + user.email() + " was not found!");
            }
        }
        return Optional.empty();
    }

    @Override
    public void registerUser(User user) {
        MongoClient connection = MongoDBConnection.getConnection();
            var usersCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.users);
            Document newUser = new Document();
            newUser.append(Columns.lastname, user.getLastname())
                    .append(Columns.firstname, user.getFirstname())
                    .append(Columns.email, user.getEmail())
                    .append(Columns.password, user.getPassword())
                    .append(Columns.role, user.getRole().name());
            usersCollection.insertOne(newUser);
            //logger.info("User inserted into database: " + user);


    }

    @Override
    public void editUser(User user) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.users);
            Bson filter = eq(Columns.email, user.getEmail());
            Bson updates = Updates.combine(
                    Updates.set(Columns.lastname, user.getLastname()),
                    Updates.set(Columns.firstname, user.getFirstname()),
                    Updates.set(Columns.email, user.getEmail()),
                    Updates.set(Columns.role, user.getRole().name()));
            collection.updateOne(filter, updates);
            logger.info("Updated user inserted into database: " + user);
        }
    }

    @Override
    public void deleteUser(String email) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.users);
            Bson filter = eq(Columns.email, email);
            collection.deleteOne(filter);
            logger.info("User " + email + " deleted successfully!");
        }
    }

    @Override
    public Collection<User> retrieveUsers() {
        Collection<User> users = new LinkedList<>();
        MongoClient connection = MongoDBConnection.getConnection();
            var collection = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.users).find();
            try (var cursor = collection.cursor()) {
                while (cursor.hasNext()) {
                    var user = gson.fromJson(cursor.next().toJson(), User.class);
                    logger.info("Get user: " + user);
                    users.add(user);
                }
                logger.info("Get users from mongodb database!");
            }

        return users;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            FindIterable<Document> filter = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.users).find(new Document(Columns.email, email));
            try (var cursor = filter.cursor()) {
                if (cursor.hasNext()) {
                    var user = gson.fromJson(cursor.next().toJson(), User.class);
                    logger.info("Get user profile : " + user);
                    return Optional.of(user);
                }
            }
        }
        logger.error("Do not get user profile");
        return Optional.empty();
    }


}
