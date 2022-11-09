package com.dteliukov.model;


import java.util.Objects;

public class Course {
    private Long id;
    private User teacher;
    private String name;

    public Course(Long id, User teacher, String name) {
        this.id = id;
        this.teacher = teacher;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public User getTeacher() {
        return teacher;
    }

    public String getName() {
        return name;
    }

    public Course id(Long id) {
        this.id = id;
        return this;
    }

    public Course teacher(User teacher) {
        this.teacher = teacher;
        return this;
    }

    public Course name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(teacher, course.teacher) && Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, name);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", name='" + name + '\'' +
                '}';
    }
}
