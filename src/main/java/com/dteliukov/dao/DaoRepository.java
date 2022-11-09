package com.dteliukov.dao;

public abstract class DaoRepository {
    public abstract AnswerDao getAnswerDao();
    public abstract CourseDao getCourseDao();
    public abstract UserDao getUserDao();
}
