package com.dteliukov.model;

import com.dteliukov.enums.Role;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class User {
    @SerializedName("lastname")
    protected String lastname;
    @SerializedName("firstname")
    protected String firstname;
    @SerializedName("email")
    protected String email;
    @SerializedName("password")
    protected String password;
    @SerializedName("role")
    protected Role role;

    public User() {}

    public User(String lastname, String firstname, String email, String password, Role role) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public User firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User role(Role role) {
        this.role = role;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(lastname, user.lastname) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(email, user.email) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastname, firstname, email, password, role);
    }

    @Override
    public User clone() {
        return new User(lastname, firstname, email, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
