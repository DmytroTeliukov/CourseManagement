package com.dteliukov.review;

import com.dteliukov.dao.AnswerDao;
import com.dteliukov.dao.DaoFactory;
import com.dteliukov.dao.TypeDao;
import com.dteliukov.enums.AnswerStatus;
import com.dteliukov.enums.ECTS;
import com.dteliukov.model.Answer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Stack;

public class Review {
    private static final Logger logger = LogManager.getLogger(Review.class);
    private final Stack<AnswerRecord> records = new Stack<>();
    private final Answer originator;
    private final AnswerDao answerDao;

    public Review(Answer originator, TypeDao typeDao){
        this.originator = originator;
        records.add(originator.create());
        answerDao = DaoFactory.getRepository(typeDao).getAnswerDao();
    }

    public void setMark(int mark){
        logger.info("Set mark: " + mark);
        originator.mark(mark);
        originator.ectsMark(ECTS.getECTSMark(mark).name());
        originator.checked(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        originator.status(AnswerStatus.CHECKED);
    }
    public void setComment(String comment){
        logger.info("Set comment: \"" + comment + "\"");
        originator.comment(comment);
        originator.checked(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        originator.status(AnswerStatus.CHECKED);
    }
    public void save(){
        if (answerDao.get(originator.getId()).isPresent()) {
            AnswerRecord record = originator.create();
            records.add(record);
            answerDao.editAnswer(originator);
            logger.info("Save record of answer");
        }
    }
    public List<AnswerRecord> getRecords(){
        logger.info("Retrieve records of answer");
        return records;
    }
    public void cancel(){
        if (answerDao.get(originator.getId()).isPresent()) {
            if (!records.isEmpty() && records.size() != 1) {
                records.pop();
                originator.restore(records.lastElement());
            }

            answerDao.editAnswer(originator);
            logger.info("Restore record of answer");
        }

    }

    public void clearRecords() {
        while (!records.isEmpty()) {
            originator.restore(records.pop());
        }
        answerDao.editAnswer(originator);
    }
}
