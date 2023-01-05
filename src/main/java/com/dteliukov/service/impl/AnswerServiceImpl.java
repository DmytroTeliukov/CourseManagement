package com.dteliukov.service.impl;

import com.dteliukov.config.DaoRepositoryConfiguration;
import com.dteliukov.dao.AnswerDao;
import com.dteliukov.dao.DaoFactory;
import com.dteliukov.dao.TypeDao;
import com.dteliukov.model.Answer;
import com.dteliukov.service.AnswerService;

import java.util.Collection;
import java.util.Optional;

public class AnswerServiceImpl implements AnswerService {
    private final AnswerDao answerDao;

    public AnswerServiceImpl() {
        answerDao = DaoRepositoryConfiguration.getRepository().getAnswerDao();
    }
    @Override
    public void addAnswer(Answer answer, Long taskId) {
        answerDao.addAnswer(answer, taskId);
    }

    @Override
    public void editAnswer(Answer answer) {
        answerDao.editAnswer(answer);
    }

    @Override
    public void deleteAnswer(Long id) {
        answerDao.deleteAnswer(id);
    }

    @Override
    public Collection<Answer> retrieveByTask(Long id) {
        return answerDao.retrieveByTask(id);
    }

    @Override
    public Optional<Answer> get(Long id) {
        return answerDao.get(id);
    }
}
