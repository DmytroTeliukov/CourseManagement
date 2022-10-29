package com.dteliukov.dao.mysql;

import com.dteliukov.dao.AnswerDao;
import com.dteliukov.model.Answer;
import com.dteliukov.model.Task;

import java.util.Collection;
import java.util.Optional;

public class AnswerMySqlDao implements AnswerDao {

    @Override
    public void addAnswer(Answer answer) {

    }

    @Override
    public void editAnswer(Answer answer) {

    }

    @Override
    public void deleteAnswer(Long id) {

    }

    @Override
    public Collection<Answer> retrieveAnswersByTask(Task task) {
        return null;
    }

    @Override
    public Optional<Answer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Answer> searchByStudentEmail(String email) {
        return Optional.empty();
    }
}
