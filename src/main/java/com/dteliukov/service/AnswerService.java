package com.dteliukov.service;

import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.Answer;

import java.util.Collection;
import java.util.Optional;

public interface AnswerService {
    void addAnswer(Answer answer, Long taskId) throws AccessDeniedException;
    void editAnswer(Answer answer) throws AccessDeniedException;
    void deleteAnswer(Long id) throws AccessDeniedException;
    Collection<Answer> retrieveByTask(Long id) throws AccessDeniedException;
    Optional<Answer> get(Long id) throws AccessDeniedException;
}
