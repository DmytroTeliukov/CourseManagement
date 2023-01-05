package com.dteliukov.dao.aggregation;

import com.dteliukov.enums.ECTS;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AggregationFrameworkTest {

    static Logger logger = Logger.getLogger("org.mongodb.driver");
    static Dao usualDao, aggregationDao;

    @BeforeAll
    static void setUp() {
        logger.setLevel(Level.OFF);
        usualDao = new UsualDao();
        aggregationDao = new AggregationDao();
    }

    @Test
    @DisplayName("Get students with failed marks")
    void getStudentsWithFailedMarks() {
        doExperiment(usualDao, Dao::getStudentsWithFailedMarks);
        doExperiment(aggregationDao, Dao::getStudentsWithFailedMarks);
    }

    @Test
    @DisplayName("Group ects marks")
    void groupECTSMarks() {
        doExperiment(usualDao, Dao::countAnswersByECTSMark);
        doExperiment(aggregationDao, Dao::countAnswersByECTSMark);
    }

    @Test
    @DisplayName("Group statuses")
    void groupStatuses() {
        doExperiment(usualDao, Dao::countAnswersByStatus);
        doExperiment(aggregationDao, Dao::countAnswersByStatus);
    }

    @Test
    @DisplayName("Find answers which sent after deadline")
    void findСheckedAnswersWhichSentAfterDeadline() {
        var date = LocalDateTime.now().minusDays(122).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        doExperiment(usualDao, dao -> dao.getCheckedAnswerSentAfterDeadline(date));
        doExperiment(aggregationDao, dao -> dao.getCheckedAnswerSentAfterDeadline(date));
    }

    @Test
    @DisplayName("Get student which get mark C")
    void getStudentWhichGetMarkC() {
        var mark = ECTS.C;
        doExperiment(usualDao, dao -> dao.getAllStudentGotMark(mark));
        doExperiment(aggregationDao, dao -> dao.getAllStudentGotMark(mark));
    }

    public void doExperiment(Dao dao, Consumer<Dao> consumer) {
        var startTime = System.currentTimeMillis();
        consumer.accept(dao);
        var endTime = System.currentTimeMillis();
        System.out.println("Query (" + dao.getClass().getSimpleName() + "): " + ((endTime - startTime) /  1000.0));
    }

}
