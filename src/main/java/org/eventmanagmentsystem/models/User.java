package org.eventmanagmentsystem.models;

public abstract class User {
    private String userName;
    private String role;
    private String email;
    private int id;

    public User(int id, String userName, String email, String role) {
        this.userName = userName;
        this.role = role;
        this.email = email;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }


    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}
