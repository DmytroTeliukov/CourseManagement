package com.dteliukov.dao;

import com.dteliukov.dao.mongodb.MongoDBDaoRepository;
import com.dteliukov.dao.mysql.MySqlDaoRepository;

public class DaoManager {
    private static DaoRepository repository = null;
    public static DaoRepository getRepository(TypeDao type) {
        if (repository == null) {
            return switch (type) {
                case MYSQL -> repository = new MySqlDaoRepository();
                case MONGODB -> repository = new MongoDBDaoRepository();
            };
        }
        return repository;
    }
}
