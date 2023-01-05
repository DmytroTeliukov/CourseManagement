package com.dteliukov.dao.mongodb;

import com.mongodb.MongoClient;

public class MongoDBConnection {
    private static final String host = "localhost";
    private static final int port = 27017;
    public static final String dbName = "course_database";

    private static MongoClient connection = null;
    public static MongoClient getConnection() {
        if (connection == null)
            connection = new MongoClient(host, port);
        return connection;
    }
}
