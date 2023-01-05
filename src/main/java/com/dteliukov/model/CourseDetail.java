package com.dteliukov.model;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CourseDetail {
    private Course course;
    @SerializedName(value = "materials")
    private List<Material> materials;
    @SerializedName(value = "tasks")
    private List<Task> tasks;

    public CourseDetail() {}
    public CourseDetail(Course course, List<Material> materials, List<Task> tasks) {
        this.course = course;
        this.materials = materials;
        this.tasks = tasks;
    }

    public Course getCourse() {
        return course;
    }

    public CourseDetail course(Course course) {
        this.course = course;
        return this;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public CourseDetail materials(List<Material> materials) {
        this.materials = materials;
        return this;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public CourseDetail tasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CourseDetail addMaterial(Material material) {
        if (materials == null) {
            materials = new LinkedList<>();
        }
        materials.add(material);
        return this;
    }

    public CourseDetail addTask(Task task) {
        if (tasks == null) {
            tasks = new LinkedList<>();
        }
        tasks.add(task);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDetail that = (CourseDetail) o;
        return Objects.equals(course, that.course) &&
                Objects.equals(materials, that.materials) &&
                Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, materials, tasks);
    }

    @Override
    public String toString() {
        return "CourseDetail{" +
                "course=" + course +
                ", materials=" + materials +
                ", tasks=" + tasks +
                '}';
    }
}
