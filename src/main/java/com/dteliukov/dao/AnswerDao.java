package com.dteliukov.dao;

import com.dteliukov.model.Answer;
import com.dteliukov.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface AnswerDao {
    void addAnswer(Answer answer);
    void editAnswer(Answer answer);
    void deleteAnswer(Long id);
    Collection<Answer> retrieveAnswersByTask(Task task);
    Optional<Answer> get(Long id);
    Optional<Answer> searchByStudentEmail(String email);
}
