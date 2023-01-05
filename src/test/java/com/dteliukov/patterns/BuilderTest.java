package com.dteliukov.patterns;

import com.dteliukov.enums.Role;
import com.dteliukov.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BuilderTest {
    @Test
    @DisplayName("create object and build it")
    void createObjectByDefaultConstructorAndBuildIt() {
        CourseDetail courseDetail = new CourseDetail();
        User teacher = new User(null, null, "email@gmail.com", null, null)
                .lastname("testLastname")
                .firstname("testFirstname")
                .role(Role.TEACHER)
                .password("teacher");
        Course course = new Course(1L, teacher, "testCourse");
        courseDetail.course(course);

        courseDetail.addMaterial(new Material(1L, "firstMaterial", "C:/1"));
        courseDetail.addMaterial(new Material(2L, "secondMaterial", "C:/2"));
        courseDetail.addMaterial(new Material(3L, "thirdMaterial", "C:/3"));
        courseDetail.addMaterial(new Material(4L, "fourthMaterial", "C:/4"));

        courseDetail.addTask(new Task(1L, "firstTheme", "firstDescription",
                LocalDateTime.now().toString(), LocalDateTime.now().toString()));
        courseDetail.addTask(new Task(2L, "secondTheme", "secondDescription",
                LocalDateTime.now().toString(), LocalDateTime.now().toString()));

        System.out.println(courseDetail);

        Assertions.assertNotNull(courseDetail.getCourse());
        Assertions.assertNotNull(courseDetail.getMaterials());
        Assertions.assertNotNull(courseDetail.getTasks());
        Assertions.assertEquals(4, courseDetail.getMaterials().size());
        Assertions.assertEquals(2, courseDetail.getTasks().size());
    }
}
