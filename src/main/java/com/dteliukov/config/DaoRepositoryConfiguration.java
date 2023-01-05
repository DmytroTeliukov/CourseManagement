package com.dteliukov.config;

import com.dteliukov.dao.DaoFactory;
import com.dteliukov.dao.DaoRepository;
import com.dteliukov.dao.TypeDao;

public class DaoRepositoryConfiguration {

    public static DaoRepository getRepository() {
        return DaoFactory.getRepository(TypeDao.MONGODB);
    }
}
