package org.eventmanagmentsystem.models;

public class Admin extends User {
    public Admin(String userName, String password) {
        super(userName, password, "Admin");
    }
}
