package com.dteliukov.notification;

import com.dteliukov.enums.Role;
import com.dteliukov.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Student extends User implements Subscriber {
    private static final Logger logger = LogManager.getLogger(Student.class);

    public Student() {
        super();
    }

    public Student(String lastname, String firstname, String email, String password, Role role) {
        super(lastname, firstname, email, password, role);
    }
    @Override
    public void update(String notification) {
        logger.info("Hey, " + lastname + " " + firstname + ", " + notification);
    }

    public Student clone() {
        return new Student(lastname, firstname, email, password, role);
    }
}
