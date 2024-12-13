package org.eventmanagmentsystem.models;

public abstract class User {
    private String userName;
    private String role;
    private String email;
    private int id;
    private int password;

    public User(String userName, String email, String role) {
        this.userName = userName;
        this.role = role;
        this.email = email;
        this.id = createID();
        this.password = createPassword();
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
    
    /* create id method */
    public int createID(){
        /* add implementation */
    }
    
    /* create password */
    public int createPassword(){
        /* get time per millisecond */
        long millisecond = System.currentTimeMillis();
        
        /* return last fives digits */
        return (int)(millisecond % 10_000);
    }

    /* method toString for print line in file */
    @Override
    public String toString(){
        return id + "," + userName + "," + email + "," + role + "," + password;
    }
}
