package com.dteliukov.review;

import com.dteliukov.enums.ECTS;
import com.dteliukov.model.Answer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Stack;

public class Review {
    private static final Logger logger = LogManager.getLogger(Review.class);
    private final Stack<AnswerRecord> records = new Stack<>();
    private final Answer originator;

    public Review(Answer originator){
        this.originator = originator;
    }

    public void setMark(int mark){
        logger.info("Set mark: " + mark);
        originator.mark(mark);
        originator.ectsMark(ECTS.getECTSMark(mark).name());
    }
    public void setComment(String comment){
        logger.info("Set comment: \"" + comment + "\"");
        originator.comment(comment);
    }
    public AnswerRecord save(){
        logger.info("Save record of answer");
        AnswerRecord record = this.originator.create();
        records.add(record);
        return record;
    }
    public List<AnswerRecord> getRecords(){
        logger.info("Retrieve records of answer");
        return records;
    }
    public void cancel(AnswerRecord record){
        logger.info("Restore record of answer");
        originator.restore(record);
    }

    public void clearRecords() {
        records.clear();
    }
}
