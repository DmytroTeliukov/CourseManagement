package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.UserDao;
import com.dteliukov.model.AuthorizedUser;
import com.dteliukov.model.UnauthorizedUser;
import com.dteliukov.model.User;
import com.dteliukov.security.SecurityPasswordUtil;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
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
    @Override
    public Optional<AuthorizedUser> login(UnauthorizedUser user) {
        Gson gson = new Gson();
        try (var connection = MongoDBConnection.getConnection()) {
            FindIterable<Document> filter = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection("users")
                    .find(new Document("email", user.email()));
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
        try (var connection = MongoDBConnection.getConnection()) {
            var usersCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection("users");
            Document newUser = new Document();
            newUser.append("lastname", user.getLastname())
                    .append("firstname", user.getFirstname())
                    .append("email", user.getEmail())
                    .append("password", SecurityPasswordUtil.getSecuredPassword(user.getPassword()))
                    .append("role", user.getRole().name());
            usersCollection.insertOne(newUser);
        }

    }

    @Override
    public void editUser(User user) {
        try (var connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection("users");
            Bson filter = eq("email", user.getEmail());
            Bson updates = Updates.combine(
                    Updates.set("lastname", user.getLastname()),
                    Updates.set("firstname", user.getFirstname()),
                    Updates.set("email", user.getEmail()),
                    Updates.set("role", user.getRole().name()));
            collection.updateOne(filter, updates);
        }
    }

    @Override
    public void deleteUser(String email) {
        try (var connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection("users");
            Bson filter = eq("email", email);
            collection.deleteOne(filter);
        }
    }

    @Override
    public Collection<User> retrieveUsers() {
        Gson gson = new Gson();
        Collection<User> users = new LinkedList<>();
        try (var connection = MongoDBConnection.getConnection()) {
            var collection = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection("users").find();
            try (var cursor = collection.cursor()) {
                if (cursor.hasNext()) {
                    users.add(gson.fromJson(cursor.next().toJson(), User.class));
                }
            }
        }
        return users;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Gson gson = new Gson();
        try (var connection = MongoDBConnection.getConnection()) {
            FindIterable<Document> filter = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection("users")
                    .find(new Document("email", email));
            try (var cursor = filter.cursor()) {
                if (cursor.hasNext()) {
                    return Optional.of(gson.fromJson(cursor.next().toJson(), User.class));
                }
            }
        }
        return Optional.empty();
    }


}
