package com.dteliukov.dao.mongodb;

import com.dteliukov.dao.CourseDao;
import com.dteliukov.dao.schema.Collections;
import com.dteliukov.dao.schema.Columns;
import com.dteliukov.model.*;
import com.dteliukov.notification.Student;
import com.dteliukov.utils.IdGenerator;
import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class CourseMongoDBDao implements CourseDao {

    private static final Logger logger = LogManager.getLogger(CourseMongoDBDao.class);

    private final Gson gson;

    public CourseMongoDBDao() {
        gson = new Gson();
    }
    @Override
    public void createCourse(Course course) {
        try (var connection = MongoDBConnection.getConnection()) {
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
    }

    @Override
    public void addMaterial(Material material, Long courserId) {

    }

    @Override
    public void addTask(Task task, Long courseId) {

    }

    @Override
    public void deleteMaterial(Long id) {

    }

    @Override
    public void registerStudent(String email, Long courseId) {
        try (var connection = MongoDBConnection.getConnection()) {
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            var student = getUserByEmail(email, connection);
            Bson filter = eq("course_id", courseId);
            Bson updates = Updates.push("students", new Document("lastname", student.getLastname())
                    .append("firstname", student.getFirstname())
                    .append("email", student.getEmail()));
            coursesCollection.updateOne(filter, updates);;
        }
    }

    @Override
    public void removeStudent(String email, Long courseId) {
        try (var connection = MongoDBConnection.getConnection()) {
            var coursesCollection = connection
                    .getDatabase(MongoDBConnection.dbName).getCollection(Collections.courses);
            var student = getUserByEmail(email, connection);
            Bson filter = eq("course_id", courseId);
            Bson updates = Updates.pull("students", new Document("lastname", student.getLastname())
                    .append("firstname", student.getFirstname())
                    .append("email", student.getEmail()));
            coursesCollection.updateOne(filter, updates);;
        }
    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public void deleteTask(Long id) {

    }

    @Override
    public void editCourse(Course course) {

    }

    @Override
    public void deleteCourse(Long id) {
        try (var connection = MongoDBConnection.getConnection()) {
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
        try (var connection = MongoDBConnection.getConnection()) {
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
        }
        return courses;
    }

    @Override
    public Collection<Student> retrieveStudents(Long courseId) {
        Collection<Student> students = new LinkedList<>();
        try (var connection = MongoDBConnection.getConnection()) {
            var collection = connection
                    .getDatabase(MongoDBConnection.dbName)
                    .getCollection(Collections.courses);
            FindIterable<Document> filter = collection.find(new Document("course_id", courseId));
            try (var cursor = filter.cursor()) {
                if (cursor.hasNext()) {
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
        return null;
    }

    @Override
    public Optional<CourseDetail> getDetail(Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> getByName(String name) {
        try (var connection = MongoDBConnection.getConnection()) {
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
