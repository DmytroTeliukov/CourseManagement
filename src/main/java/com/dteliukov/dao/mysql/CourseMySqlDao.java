package com.dteliukov.dao.mysql;

import com.dteliukov.dao.CourseDao;
import com.dteliukov.dao.mysql.tables.*;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class CourseMySqlDao implements CourseDao {

    private static final Logger logger = LogManager.getLogger(CourseMySqlDao.class);

    @Override
    public void createCourse(Course course) {
        String createCourseScript = createCourseScript();
        logger.info("Create course sql script: " + createCourseScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            long teacherId = getTeacherId(course.getTeacher().getEmail(), connection);
            try(PreparedStatement preparedStatement = connection.prepareStatement(createCourseScript)) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setLong(2, teacherId);
                preparedStatement.executeUpdate();
                logger.info("Course saved!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void editTeacherByEmail(String email, Long courseId) {
        String editTeacherScript = editTeacherScript();
        logger.info("Edit teacher sql script: " + editTeacherScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            long id = getTeacherId(email, connection);
            try(PreparedStatement preparedStatement = connection.prepareStatement(editTeacherScript)) {
                preparedStatement.setLong(1, id);
                preparedStatement.setLong(2, courseId);
                preparedStatement.executeUpdate();
                logger.info("Teacher changed!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addMaterial(Material material, Long courserId) {
        String addMaterialScript = addMaterialScript();
        logger.info("Add material sql script: " + addMaterialScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(addMaterialScript)) {
                preparedStatement.setString(1, material.getName());
                preparedStatement.setString(2, material.getPath());
                preparedStatement.setLong(3, courserId);
                preparedStatement.executeUpdate();
                logger.info("Material is saved!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void addTask(Task task, Long courseId) {
        String addTaskScript = addTaskScript();
        logger.info("Add task sql script: " + addTaskScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(addTaskScript)) {
                preparedStatement.setString(1, task.getTheme());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setString(3, task.getCreated());
                preparedStatement.setString(4, task.getDeadline());
                preparedStatement.setLong(5, courseId);
                preparedStatement.executeUpdate();
                logger.info("Task saved!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMaterial(Long id) {
        String deleteMaterialScript = deleteMaterialScript();
        logger.info("Delete material sql script: " + deleteMaterialScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteMaterialScript)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                logger.info("Material deleted!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void registryStudent(String email, Long courseId) {
        String addCourseScript = addGroupScript();
        logger.info("Add student sql script: " + addCourseScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            long studentId = getUserId(email, connection);
            try (PreparedStatement preparedStatement = connection.prepareStatement(addCourseScript)) {
                preparedStatement.setLong(1, studentId);
                preparedStatement.setLong(2, courseId);
                preparedStatement.executeUpdate();
                logger.info("Student added!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudent(String email, Long courseId) {
        String removeStudentScript = removeStudentScript();
        logger.info("Delete student sql script: " + removeStudentScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            long studentId = getUserId(email, connection);
            try (PreparedStatement preparedStatement = connection.prepareStatement(removeStudentScript)) {
                preparedStatement.setLong(1, studentId);
                preparedStatement.setLong(2, courseId);
                preparedStatement.executeUpdate();
                logger.info("Student deleted!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void editTask(Task task) {
        String editTaskScript = editTaskScript();
        logger.info("Edit task sql script: " + editTaskScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(editTaskScript)) {
                preparedStatement.setString(1, task.getTheme());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setString(3, task.getDeadline());
                preparedStatement.setLong(4, task.getId());
                preparedStatement.executeUpdate();
                logger.info("Task updated!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(Long id) {
        String deleteTaskScript = deleteTaskScript();
        logger.info("Delete task sql script: " + deleteTaskScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteTaskScript)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                logger.info("Task deleted!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void editCourse(Course course) {
        String editCourseScript = editCourseScript();
        logger.info("Edit course sql script: " + editCourseScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(editCourseScript)) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setLong(2, course.getId());
                preparedStatement.executeUpdate();
                logger.info("Course updated!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCourse(Long id) {
        String deleteCourseScript = deleteCourseScript();
        logger.info("Delete course sql script: " + deleteCourseScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteCourseScript)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                logger.info("Course deleted!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Course> retrieveCourses() {
        String getCoursesScript = getCoursesScript();
        Collection<Course> courses = new LinkedList<>();
        logger.info("Get all courses sql script: " + getCoursesScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getCoursesScript)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()) {
                        courses.add(new Course(resultSet.getLong(CourseTable.ID.name()),
                            getUserById(resultSet.getLong(CourseTable.USER_ID.name())),
                            resultSet.getString(CourseTable.NAME.name())));
                    }
                    logger.info("Got all courses!");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public Collection<Student> retrieveStudents(Long courseId) {
        String getStudentsScript = getStudentsScript();
        Collection<Student> students = new LinkedList<>();
        logger.info("Get students in course sql script: " + getStudentsScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getStudentsScript)){
                preparedStatement.setLong(1, courseId);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()) {
                        Student student = (Student) new Student()
                                .lastname(resultSet.getString(UserTable.LASTNAME.name()))
                                .firstname(resultSet.getString(UserTable.FIRSTNAME.name()))
                                .email(resultSet.getString(UserTable.EMAIL.name()));
                        students.add(student.clone());
                        logger.info("Get student from course: " + student);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Optional<CourseDetail> getDetail(Long courseId) {
        String getCourseScript = getCourseScript();
        CourseDetail courseDetail = new CourseDetail();
        logger.info("Get course sql script: " + getCourseScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(getCourseScript)){
                preparedStatement.setLong(1, courseId);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User teacher = getUserById(resultSet.getLong(CourseTable.USER_ID.name()));
                        Course course = new Course(courseId,
                                teacher,
                                resultSet.getString(CourseTable.NAME.name()));
                        List<Material> materials = getMaterialsByCourseId(courseId, connection);
                        List<Task> tasks = getTasksByCourseId(courseId, connection);
                        courseDetail.course(course).tasks(tasks).materials(materials);
                        logger.info("Got course sql script!");
                        return Optional.of(courseDetail);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> getByName(String name) {
        String getCourseByNameScript = getCourseByNameScript();
        logger.info("Get course by name sql script: " + getCourseByNameScript);
        try(Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getCourseByNameScript)) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        long id = resultSet.getLong(CourseTable.ID.name());
                        User teacher = getUserById(resultSet.getLong(CourseTable.USER_ID.name()));
                        logger.info("Got course by name!");
                        return Optional.of(new Course(id, teacher, name));
                    }
                }
             }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Long getTeacherId(String email, Connection connection) {
        long id = 0L;
        String getTeacherIdScript = getTeacherIdScript();
        logger.info("Get teacher id sql script: " + getTeacherIdScript);
        try(PreparedStatement preparedStatement = connection.prepareStatement(getTeacherIdScript)) {
            preparedStatement.setString(1, email);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getLong(UserTable.ID.name());
                }
            }
            logger.info("Teacher id = " + id);
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
    private String getStudentsScript() {
        return """
                select `user`.`lastname`, `user`.`firstname`, `user`.`email` from `user`\s
                inner join  `student-course` on `student-course`.`user_id` = `user`.`id`
                where `student-course`.`course_id` = ?;""";
    }

    private String getMaterialsByCourseId() {
        return "select id, `name`, `path` from material where course_id = ?;";
    }

    private String getTasksByCourseId() {
        return "select id, theme, `description`, created, deadline from task where course_id = ?;";
    }

    public User getUserById(Long id) {
        String getUserScript = getUserByIdScript();
        logger.info("Get teacher sql script: " + getUserScript);
        try (Connection connection = MySqlConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUserScript)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User()
                                .lastname(resultSet.getString(UserTable.LASTNAME.name()))
                                .firstname(resultSet.getString(UserTable.FIRSTNAME.name()))
                                .email(resultSet.getString(UserTable.EMAIL.name()));
                        logger.info("Get user profile : " + user.toString());
                        return user.clone();
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.error("User profile not found!");
        return new User();
    }


    private List<Material> getMaterialsByCourseId(Long id, Connection connection) {
        List<Material> materials = new LinkedList<>();
        String getMaterialsByCourseIdScript = getMaterialsByCourseId();
        logger.info("Get materials from course sql script: " + getMaterialsByCourseIdScript);
        try(PreparedStatement preparedStatement = connection.prepareStatement(getMaterialsByCourseIdScript)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    materials.add(new Material(resultSet.getLong(MaterialTable.ID.name()),
                            resultSet.getString(MaterialTable.NAME.name()),
                            resultSet.getString(MaterialTable.PATH.name())));
                }
                logger.info("Got materials from course!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return materials;
    }

    private List<Task> getTasksByCourseId(Long id, Connection connection) {
        List<Task> tasks = new LinkedList<>();
        String getTasksByCourseIdScript = getTasksByCourseId();
        logger.info("Get tasks from course sql script: " + getTasksByCourseIdScript);
        try(PreparedStatement preparedStatement = connection.prepareStatement(getTasksByCourseIdScript)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = new Task()
                            .id(resultSet.getLong(TaskTable.ID.name()))
                            .theme(resultSet.getString(TaskTable.THEME.name()))
                            .description(resultSet.getString(TaskTable.DESCRIPTION.name()))
                            .created(resultSet.getString(TaskTable.CREATED.name()))
                            .deadline(resultSet.getString(TaskTable.DEADLINE.name()));
                    logger.info("Got task from course: " + task);
                    tasks.add(task.clone());
                }
                logger.info("Got tasks from course!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return tasks;
    }

    public String addGroupScript() {
        return "insert into `student-course`(user_id, course_id) values (?,?)";
    }
    public String removeStudentScript() {
        return "delete from `student-course` where user_id = ? and course_id = ?;";
    }
    private String addMaterialScript() {
        return "insert into `material`(name, path, course_id) values(?,?,?)";
    }

    private String addTaskScript() {
        return "insert into `task`(theme, description, created, deadline, course_id) values(?,?,?,?,?)";
    }

    private String getTeacherIdScript() {
        return "select id from `user` where email = ?;";
    }

    private String createCourseScript() {
        return "insert into `course`(name, user_id) values(?,?);";
    }

    private String getUserByIdScript() {
        return "select lastname, firstname, email from `user` where id = ?";
    }

    private String getCoursesScript() {
        return "select id, name, user_id from `course`";
    }

    private String getCourseScript() {
        return "select name, user_id from `course` where id = ?;";
    }
    private String getCourseByNameScript() {
        return "select id, user_id from `course` where name = ?;";
    }
    private String editTeacherScript() {
        return "update `course` set user_id = ? where id = ?;";
    }
    private String editCourseScript() {
        return "update `course` set name = ? where id = ?;";
    }

    private String editTaskScript() {
        return "update `task` set theme = ?, description = ?, deadline = ? where id = ?;";
    }


    private String deleteMaterialScript() {
        return "delete from `material` where id = ?;";
    }

    private String deleteTaskScript() {
        return "delete from `task` where id = ?;";
    }

    private String deleteCourseScript() {
        return "delete from `course` where id = ?;";
    }
}
