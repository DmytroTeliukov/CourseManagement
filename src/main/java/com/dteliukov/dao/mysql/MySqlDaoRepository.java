package com.dteliukov.dao.mysql;

import com.dteliukov.dao.*;

public class MySqlDaoRepository extends DaoRepository {

    @Override
    public AnswerDao getAnswerDao() {
        return new AnswerMySqlDao();
    }

    @Override
    public CourseDao getCourseDao() {
        return new CourseMySqlDao();
    }

    @Override
    public UserDao getUserDao() {
        return new UserMySqlDao();
    }
}
