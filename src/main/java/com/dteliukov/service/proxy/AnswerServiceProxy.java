package com.dteliukov.service.proxy;

import com.dteliukov.enums.Role;
import com.dteliukov.exception.AccessDeniedException;
import com.dteliukov.model.Answer;
import com.dteliukov.service.AnswerService;
import com.dteliukov.service.impl.AnswerServiceImpl;
import com.dteliukov.service.impl.CourseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Optional;

public class AnswerServiceProxy implements AnswerService {
    private static final Logger logger = LogManager.getLogger(AnswerServiceProxy.class);

    private final AnswerServiceImpl service;
    private final Role role;

    public AnswerServiceProxy(Role role) {
        this.role = role;
        service = new AnswerServiceImpl();
    }
    @Override
    public void addAnswer(Answer answer, Long taskId) throws AccessDeniedException {
        if (role == Role.STUDENT) {
            logger.info("Get access to method: addAnswer()");
            service.addAnswer(answer, taskId);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void editAnswer(Answer answer) throws AccessDeniedException {
        if (role == Role.STUDENT) {
            logger.info("Get access to method: editAnswer()");
            service.editAnswer(answer);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteAnswer(Long id) throws AccessDeniedException {
        if (role == Role.STUDENT) {
            logger.info("Get access to method: deleteAnswer()");
            service.deleteAnswer(id);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public Collection<Answer> retrieveByTask(Long id) throws AccessDeniedException {
        if (role == Role.TEACHER) {
            logger.info("Get access to method: retrieveByTask()");
            return service.retrieveByTask(id);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }

    @Override
    public Optional<Answer> get(Long id) throws AccessDeniedException {
        if (role == Role.TEACHER || role == Role.STUDENT) {
            logger.info("Get access to method: get()");
            return service.get(id);
        } else {
            logger.error("Access denied");
            throw new AccessDeniedException();
        }
    }
}
