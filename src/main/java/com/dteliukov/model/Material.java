package com.dteliukov.model;

import com.dteliukov.notification.CourseItem;

import java.util.Objects;

public class Material extends CourseItem {

    private Long id;
    private String name;
    private String path;

    public Material(Long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Objects.equals(name, material.name) && Objects.equals(path, material.path);
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
