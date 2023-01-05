package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.AnswerDao;
import com.dteliukov.model.Answer;
import com.dteliukov.utils.IdGenerator;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Updates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class AnswerMongoDBDao implements AnswerDao {
    private static final Logger logger = LogManager.getLogger(AnswerMongoDBDao.class);

    private final Gson gson;

    public AnswerMongoDBDao() {
        gson = new Gson();
    }

    @Override
    public void addAnswer(Answer answer, Long taskId) {
        MongoClient connection = MongoDBConnection.getConnection();
            var answersCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection("answers");
            Document answerDoc = new Document("task_id", taskId)
                    .append("answer_id", IdGenerator.generateId())
                    .append("path", answer.getFilePath())
                    .append("student", new Document("lastname", answer.getStudent().getLastname())
                            .append("firstname", answer.getStudent().getFirstname())
                            .append("email", answer.getStudent().getEmail()))
                    .append("sent", answer.getSent())
                    .append("checked", answer.getChecked())
                    .append("comment", answer.getComment())
                    .append("mark", answer.getMark())
                    .append("ects_mark", answer.getECTSMark())
                    .append("status", answer.getStatus().name());
            answersCollection.insertOne(answerDoc);
            logger.info("Answer inserted into database: " + answer);

    }

    @Override
    public void editAnswer(Answer answer) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection("answers");
            Bson filter = eq("answer_id", answer.getId());
            Bson updates = Updates.combine(
                    Updates.set("path", answer.getFilePath()),
                    Updates.set("checked", answer.getChecked()),
                    Updates.set("comment", answer.getComment()),
                    Updates.set("status", answer.getStatus().name()),
                    Updates.set("ects_mark", answer.getECTSMark()),
                    Updates.set("mark", answer.getMark()));
            collection.updateOne(filter, updates);
            logger.info("Updated answer inserted into database: " + answer);
        }
    }

    @Override
    public void deleteAnswer(Long id) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection("answers");
            Bson filter = eq("answer_id", id);
            collection.deleteOne(filter);
            logger.info("Answer deleted successfully!");
        }
    }

    @Override
    public Collection<Answer> retrieveByTask(Long id) {
        Collection<Answer> answers = new LinkedList<>();
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection("answers")
                    .find(new Document("task_id", id));
            try (var cursor = collection.cursor()) {
                while (cursor.hasNext()) {
                    var answer = gson.fromJson(cursor.next().toJson(), Answer.class);
                    logger.info("Get answer: " + answer);
                    answers.add(answer);
                }
                logger.info("Get answers from mongodb database!");
            }
        }
        return answers;
    }

    @Override
    public Optional<Answer> get(Long id) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            FindIterable<Document> filter = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection("answers")
                    .find(new Document("answer_id", id));
            try (var cursor = filter.cursor()) {
                if (cursor.hasNext()) {
                    var answer = gson.fromJson(cursor.next().toJson(), Answer.class);
                    logger.info("Get answer: " + answer);
                    return Optional.of(answer);
                }
            }
        }
        logger.error("Do not get answer");
        return Optional.empty();
    }
}
