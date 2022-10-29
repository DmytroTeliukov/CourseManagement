package com.dteliukov.dao;

public abstract class DaoFactory {
    public abstract AnswerDao getAnswerDao();
    public abstract CourseDao getCourseDao();
    public abstract GroupDao getGroupDao();
    public abstract UserDao getUserDao();
}
