package com.dteliukov.notification;

import com.dteliukov.enums.Role;
import com.dteliukov.model.User;

public class Student extends User implements Subscriber {

    public Student() {
        super();
    }

    public Student(String lastname, String firstname, String email, String password, Role role) {
        super(lastname, firstname, email, password, role);
    }
    @Override
    public void update(String notification) {

    }

    public Student clone() {
        return new Student(lastname, firstname, email, password, role);
    }
}
