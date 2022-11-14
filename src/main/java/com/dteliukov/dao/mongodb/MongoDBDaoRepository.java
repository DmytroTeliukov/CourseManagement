package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.*;

public class MongoDBDaoRepository extends DaoRepository {
    @Override
    public AnswerDao getAnswerDao() {
        return null;
    }

    @Override
    public CourseDao getCourseDao() {
        return new CourseMongoDBDao();
    }

    @Override
    public UserDao getUserDao() {
        return new UserMongoDBDao();
    }
}
