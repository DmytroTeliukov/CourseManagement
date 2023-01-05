package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.CourseDao;
import com.dteliukov.dao.schema.Collections;
import com.dteliukov.dao.schema.Columns;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;
import com.dteliukov.utils.IdGenerator;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.MongoClient;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.client.model.Filters.*;

public class CourseMongoDBDao implements CourseDao {

    private static final Logger logger = LogManager.getLogger(CourseMongoDBDao.class);

    private final Gson gson;

    public CourseMongoDBDao() {
        gson = new Gson();
    }
    @Override
    public void createCourse(Course course) {
        MongoClient connection = MongoDBConnection.getConnection();
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            Document courseDoc = new Document("course_id", IdGenerator.generateId())
                    .append("name", course.getName())
                    .append("teacher", new Document("lastname", course.getTeacher().getLastname())
                    .append("firstname", course.getTeacher().getFirstname())
                    .append("email", course.getTeacher().getEmail()))
                    .append("materials", List.of())
                    .append("tasks", List.of())
                    .append("students", List.of());
            coursesCollection.insertOne(courseDoc);

    }

    @Override
    public void addMaterial(Material material, Long courserId) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            Bson filter = eq("course_id", courserId);
            Bson updates = Updates.push("materials", new Document("id", IdGenerator.generateId())
                    .append("name", material.getName())
                    .append("path", material.getPath()));
            coursesCollection.updateOne(filter, updates);;
        }
    }

    @Override
    public void addTask(Task task, Long courseId) {
        MongoClient connection = MongoDBConnection.getConnection();
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            Bson filter = eq("course_id", courseId);
            Document document = new Document("task_id", IdGenerator.generateId())
                    .append("theme", task.getTheme())
                    .append("description", task.getDescription())
                    .append("created", task.getCreated())
                    .append("deadline", task.getDeadline());
            Bson updates = Updates.push("tasks", document);
            coursesCollection.updateOne(filter, updates);

    }

    @Override
    public void deleteMaterial(Long id) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            Bson filter = eq("materials.id", id);
            var update = Updates.pull("materials", eq("id", id));
            coursesCollection.updateOne(filter, update);
        }
    }

    @Override
    public void registerStudent(String email, Long courseId) {
        MongoClient connection = MongoDBConnection.getConnection();
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            var student = getUserByEmail(email, connection);
            Bson filter = eq("course_id", courseId);
            Bson updates = Updates.push("students", new Document("lastname", student.getLastname())
                    .append("firstname", student.getFirstname())
                    .append("email", student.getEmail()));
            coursesCollection.updateOne(filter, updates);;

    }

    @Override
    public void removeStudent(String email, Long courseId) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            Bson filter = eq("course_id", courseId);
            var update = Updates.pull("students", eq("email", email));
            coursesCollection.updateOne(filter, update);;
        }
    }

    @Override
    public void editTask(Task task) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses);

            UpdateOptions options = new UpdateOptions()
                    .arrayFilters(List.of(eq("tasks.task_id", task.getId())));

            Bson filter = eq("tasks.task_id", task.getId());
            Bson updates = Updates.combine(
                    Updates.set("tasks.$[tasks].theme", task.getTheme()),
                    Updates.set("tasks.$[tasks].description", task.getDescription()),
                    Updates.set("tasks.$[tasks].deadline", task.getDeadline()));
            collection.updateOne(filter,
                    updates,
                    options);
            logger.info("Updated task inserted into database: " + task);
        }
    }

    @Override
    public void deleteTask(Long id) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            var answersCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection("answers");
            Bson filter = eq("tasks.task_id", id);
            var update = Updates.pull("tasks", eq("task_id", id));
            coursesCollection.updateOne(filter, update);
            answersCollection.deleteMany(new Document("task_id", id));
        }
    }

    @Override
    public void editCourse(Course course) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses);
            Bson filter = eq("course_id", course.getId());
            Bson updates = Updates.combine(
                    Updates.set("name", course.getName()));
            collection.updateOne(filter, updates);
            logger.info("Updated course inserted into database: " + course);
        }
    }

    @Override
    public void deleteCourse(Long id) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection.getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses);
            Bson filter = eq("course_id", id);
            collection.deleteOne(filter);
            logger.info("Course deleted successfully!");
        }
    }

    @Override
    public Collection<Course> retrieveCourses() {
        Collection<Course> courses = new LinkedList<>();
        MongoClient connection = MongoDBConnection.getConnection();
            var collection = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses).find();
            try (var cursor = collection.cursor()) {
                while (cursor.hasNext()) {
                    var course = gson.fromJson(cursor.next().toJson(), Course.class);
                    logger.info("Get course: " + course);
                    courses.add(course);
                }
                logger.info("Get courses from mongodb database!");
            }

        return courses;
    }

    @Override
    public Collection<Student> retrieveStudents(Long courseId) {
        Collection<Student> students = new LinkedList<>();
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses);
            FindIterable<Document> filter = collection.find(new Document("course_id", courseId));
            try (var cursor = filter.cursor()) {
                while (cursor.hasNext()) {
                    List<Document> list = (List<Document>) cursor.next().get("students");
                    for (Document o : list) {
                        var student = gson.fromJson(o.toJson(), Student.class);
                        logger.info("Get student: " + student);
                        students.add(student);
                    }
                }
                logger.info("Get students from mongodb database!");
            }
        }
        return students;
    }

    @Override
    public Collection<Course> retrieveCoursesByStudentEmail(String email) {
        Collection<Course> courses = new LinkedList<>();
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            var collection = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses).find();
            var filter = collection.filter(new Document("students.email", email));
            try (var cursor = filter.cursor()) {
                while (cursor.hasNext()) {
                    var course = gson.fromJson(cursor.next().toJson(), Course.class);
                    logger.info("Get course of student: " + course);
                    courses.add(course);
                }
                logger.info("Get courses of student from mongodb database!");
            }
        }
        return courses;
    }

    @Override
    public Optional<CourseDetail> getDetail(Long courseId) {
        List<Material> materials = new LinkedList<>();
        List<Task> tasks = new LinkedList<>();
        CourseDetail detail = new CourseDetail();
        MongoClient connection = MongoDBConnection.getConnection();
            FindIterable<Document> filter = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses)
                    .find(new Document("course_id", courseId));
            try (var cursor = filter.cursor()) {
                if (cursor.hasNext()) {
                    var result = cursor.next();
                    var course = gson.fromJson(result.toJson(), Course.class);
                    List<Document> materialDocList = (List<Document>) result.get("materials");
                    for (Document o : materialDocList) {
                        var material = gson.fromJson(o.toJson(), Material.class);
                        logger.info("Get material: " + material);
                        materials.add(material);
                    }
                    List<Document> tasksDocList = (List<Document>) result.get("tasks");
                    for (Document o : tasksDocList) {
                        var task = gson.fromJson(o.toJson(), Task.class);
                        logger.info("Get task: " + task);
                        tasks.add(task);
                    }
                    detail.course(course)
                            .materials(materials)
                            .tasks(tasks);
                    logger.info("Get course: " + detail);
                    return Optional.of(detail);
                }
            }

        logger.error("Do not get course");
        return Optional.empty();
    }

    @Override
    public Optional<Course> getByName(String name) {
        try (MongoClient connection = MongoDBConnection.getConnection()) {
            FindIterable<Document> filter = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses)
                    .find(new Document("name", name));
            try (var cursor = filter.cursor()) {
                if (cursor.hasNext()) {
                    var course = gson.fromJson(cursor.next().toJson(), Course.class);
                    logger.info("Get course: " + course);
                    return Optional.of(course);
                }
            }
        }
        logger.error("Do not get course");
        return Optional.empty();
    }

    private User getUserByEmail(String email, MongoClient connection) {
        FindIterable<Document> filter = connection
                .getDatabase(MongoDBConnection.dbName)
                .getCollection(Collections.users)
                .find(new Document(Columns.email, email));
        try (var cursor = filter.cursor()) {
            if (cursor.hasNext()) {
                var user = gson.fromJson(cursor.next().toJson(), User.class);
                logger.info("Get user profile : " + user);
                return user;
            }
        }
        return new User();
    }

}
