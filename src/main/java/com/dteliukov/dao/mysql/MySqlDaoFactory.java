package com.dteliukov.dao.mysql;

import com.dteliukov.dao.*;

public class MySqlDaoFactory extends DaoFactory {
    @Override
    public AnswerDao getAnswerDao() {
        return new AnswerMySqlDao();
    }

    @Override
    public CourseDao getCourseDao() {
        return new CourseMySqlDao();
    }

    @Override
    public GroupDao getGroupDao() {
        return new GroupMySqlDao();
    }

    @Override
    public UserDao getUserDao() {
        return new UserMySqlDao();
    }
}
