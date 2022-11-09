package com.dteliukov.dao.mysql;

import com.dteliukov.dao.AnswerDao;
import com.dteliukov.dao.mysql.tables.*;
import com.dteliukov.enums.AnswerStatus;
import com.dteliukov.model.Answer;
import com.dteliukov.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;


public class AnswerMySqlDao implements AnswerDao {

    private static final Logger logger = LogManager.getLogger(AnswerMySqlDao.class);
    @Override
    public void addAnswer(Answer answer, Long taskId) {
        String createAnswerScript = createAnswerScript();
        logger.info("Create answer sql script: " + createAnswerScript);
        try (Connection connection = MySqlConnection.getConnection()){
            long statusId = getAnswerStatusId(answer.getStatus().name(), connection);
            long studentId = getUserId(answer.getStudent().getEmail(), connection);
            try(PreparedStatement preparedStatement = connection.prepareStatement(createAnswerScript)) {
                preparedStatement.setString(1, answer.getFilePath());
                preparedStatement.setObject(2, answer.getMark(), Types.INTEGER);
                preparedStatement.setString(3, answer.getECTSMark());
                preparedStatement.setString(4, answer.getSent());
                preparedStatement.setString(5, answer.getChecked());
                preparedStatement.setString(6, answer.getComment());
                preparedStatement.setLong(7, taskId);
                preparedStatement.setLong(8, studentId);
                preparedStatement.setLong(9, statusId);
                preparedStatement.executeUpdate();
                logger.info("Answer saved");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void editAnswer(Answer answer) {
        String editAnswerScript = editAnswerScript();
        logger.info("Edit answer sql script: " + editAnswerScript);
        try (Connection connection = MySqlConnection.getConnection()){
            long statusId = getAnswerStatusId(answer.getStatus().name(), connection);
            try(PreparedStatement preparedStatement = connection.prepareStatement(editAnswerScript)) {
                preparedStatement.setString(1, answer.getFilePath());
                preparedStatement.setLong(2, answer.getMark());
                preparedStatement.setString(3, answer.getECTSMark());
                preparedStatement.setString(4, answer.getChecked());
                preparedStatement.setString(5, answer.getComment());
                preparedStatement.setLong(6, statusId);
                preparedStatement.setLong(7, answer.getId());
                preparedStatement.executeUpdate();
                logger.info("Answer updated");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAnswer(Long id) {
        String deleteAnswerScript = deleteAnswerScript();
        logger.info("Delete answer sql script: " + deleteAnswerScript);
        try (Connection connection = MySqlConnection.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteAnswerScript)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                logger.info("Answer deleted");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Answer> retrieveByTask(Long id) {
        String retrieveAnswerByTaskScript = retrieveAnswerByTaskScript();
        Collection<Answer> answers = new LinkedList<>();
        logger.info("Gel all answers for task sql script: " + retrieveAnswerByTaskScript);
        try (Connection connection = MySqlConnection.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveAnswerByTaskScript)){
                preparedStatement.setLong(1, id);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Answer answer = new Answer()
                                .id(resultSet.getLong(AnswerTable.ID.name()))
                                .filePath(resultSet.getString(AnswerTable.PATH.name()))
                                .mark(resultSet.getInt(AnswerTable.MARK.name()))
                                .ectsMark(resultSet.getString(AnswerTable.ECTS.name()))
                                .sent(resultSet.getString(AnswerTable.SENT.name()))
                                .checked(resultSet.getString(AnswerTable.CHECKED.name()))
                                .comment(resultSet.getString(AnswerTable.COMMENT.name()))
                                .student(getUserById(resultSet.getLong(AnswerTable.USER_ID.name()), connection))
                                .status(AnswerStatus.valueOf(resultSet.getString(AnswerTable.NAME.name())));
                        logger.info("Got answer: " + answer);
                        answers.add(answer.clone());
                    }
                }
                logger.info("Got all answers");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public Optional<Answer> get(Long id) {
        String getAnswerScript = getAnswerScript();
        logger.info("Gel answer sql script: " + getAnswerScript);
        try (Connection connection = MySqlConnection.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(getAnswerScript)){
                preparedStatement.setLong(1, id);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Answer answer = new Answer()
                                .id(id)
                                .filePath(resultSet.getString(AnswerTable.PATH.name()))
                                .mark(resultSet.getInt(AnswerTable.MARK.name()))
                                .ectsMark(resultSet.getString(AnswerTable.ECTS.name()))
                                .sent(resultSet.getString(AnswerTable.SENT.name()))
                                .checked(resultSet.getString(AnswerTable.CHECKED.name()))
                                .comment(resultSet.getString(AnswerTable.COMMENT.name()))
                                .student(getUserById(resultSet.getLong(AnswerTable.USER_ID.name()), connection))
                                .status(AnswerStatus.valueOf(resultSet.getString(AnswerTable.NAME.name())));
                        logger.info("Got answer");
                        return Optional.of(answer);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User getUserById(Long id, Connection connection) {
        String getUserByIdScript = getUserByIdScript();
        logger.info("Get student by id sql script: " + getUserByIdScript);
        try(PreparedStatement preparedStatement = connection.prepareStatement(getUserByIdScript)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    User student = new User()
                            .lastname(resultSet.getString(UserTable.LASTNAME.name()))
                            .firstname(resultSet.getString(UserTable.FIRSTNAME.name()))
                            .email(resultSet.getString(UserTable.EMAIL.name()));
                    logger.info("Got student!");
                    return student;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return new User();
    }

    private Long getAnswerStatusId(String name, Connection connection) {
        long id = 0L;
        String getAnswerStatusIdScript = getAnswerStatusIdScript();
        logger.info("Get answer status id by name sql script: " + getAnswerStatusIdScript);
        try(PreparedStatement preparedStatement = connection.prepareStatement(getAnswerStatusIdScript)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    id = resultSet.getLong(AnswerStatusTable.ID.name());
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    private Long getUserId(String email, Connection connection) {
        long id = 0L;
        String getUserIdScript = getUserIdScript();
        logger.info("Get user by id sql script: " + getUserIdScript);
        try(PreparedStatement preparedStatement = connection.prepareStatement(getUserIdScript)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    id = resultSet.getLong(AnswerStatusTable.ID.name());
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return id;
    }
    private String getUserIdScript() {
        return "select id from `user` where email = ?";
    }

    private String getUserByIdScript() {
        return "select lastname, firstname, email from `user` where id = ?";
    }

    private String getAnswerScript() {
        return "select `answer`.id, `answer`.`path`, `answer`.mark, `answer`.ects, `answer`.sent, " +
                "`answer`.checked, `answer`.comment, `answer`.task_id, `answer`.user_id, `answer_status`.`name` " +
                "from `answer` inner join `answer_status` on `answer`.answer_status_id = `answer_status`.id " +
                " where `answer`.id = ?;";
    }

    private String getAnswerStatusIdScript() {
        return "select id from `answer_status` where `name` = ?;";
    }

    private String createAnswerScript() {
        return "insert into `answer`(`path`, mark, ects, sent, checked, comment, task_id, " +
                "user_id, answer_status_id) values (?,?,?,?,?,?,?,?,?);";
    }

    private String editAnswerScript() {
        return "update `answer` set `path` = ?, `mark` = ?, `ects` = ?, `checked` = ?, `comment` = ?," +
                " `answer_status_id` = ? where id = ?;";
    }

    private String deleteAnswerScript() {
        return "delete from `answer` where id = ?";
    }

    private String retrieveAnswerByTaskScript() {
        return "select `answer`.id, `answer`.`path`, `answer`.mark, `answer`.ects, `answer`.sent, " +
                "`answer`.checked, `answer`.comment, `answer`.task_id, `answer`.user_id, `answer_status`.`name` " +
                "from `answer` inner join `answer_status` on `answer`.answer_status_id = `answer_status`.id" +
                " where task_id = ?;";
    }

}
