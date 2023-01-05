package com.dteliukov.patterns;

import com.dteliukov.dao.DaoFactory;
import com.dteliukov.dao.TypeDao;
import com.dteliukov.dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DaoPatternTest {
    @Test
    @DisplayName("Create dao object")
    void createSingletonObject() {
        UserDao firstDao = DaoFactory.getRepository(TypeDao.MYSQL).getUserDao();
        UserDao secondDao = DaoFactory.getRepository(TypeDao.MONGODB).getUserDao();

        System.out.println("First object class type: " + firstDao.getClass().getSimpleName());
        System.out.println("Second object class type: " + secondDao.getClass().getSimpleName());

        Assertions.assertEquals(firstDao.getClass(), secondDao.getClass());
    }

}
