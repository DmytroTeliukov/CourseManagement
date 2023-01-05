package com.dteliukov.dao;

import com.dteliukov.model.Answer;

import java.util.Collection;
import java.util.Optional;

public interface AnswerDao {
    void addAnswer(Answer answer, Long taskId);
    void editAnswer(Answer answer);
    void deleteAnswer(Long id);
    Collection<Answer> retrieveByTask(Long id);
    Optional<Answer> get(Long id);
}
