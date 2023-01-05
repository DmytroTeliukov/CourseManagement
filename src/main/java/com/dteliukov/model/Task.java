package com.dteliukov.model;

import com.dteliukov.notification.CourseItem;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Task extends CourseItem {
    @SerializedName("task_id")
    private Long id;
    @SerializedName("theme")
    private String theme;
    @SerializedName("description")
    private String description;
    @SerializedName("created")
    private String created;
    @SerializedName("deadline")
    private String deadline;

    public Task() {}
    public Task(Long id, String theme, String description, String created, String deadline) {
        this.id = id;
        this.theme = theme;
        this.description = description;
        this.created = created;
        this.deadline = deadline;
    }

    public Task id(Long id) {
        this.id = id;
        return this;
    }

    public Task theme(String theme) {
        this.theme = theme;
        return this;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public Task created(String created) {
        this.created = created;
        return this;
    }

    public Task deadline(String deadline) {
        this.deadline = deadline;
        return this;
    }


    public Long getId() {
        return id;
    }

    public String getTheme() {
        return theme;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated() {
        return created;
    }

    public String getDeadline() {
        return deadline;
    }


    public Task clone() {
        return new Task(id, theme, description, created, deadline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(theme, task.theme) &&
                Objects.equals(description, task.description) &&
                Objects.equals(created, task.created) &&
                Objects.equals(deadline, task.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theme, description, created, deadline);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", description='" + description + '\'' +
                ", created='" + created + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}
