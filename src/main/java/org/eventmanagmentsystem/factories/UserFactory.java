package org.eventmanagmentsystem.factories;

import org.eventmanagmentsystem.models.*;

public class UserFactory {

    public static User createUser(int id, String username, String email, String role) {
        return switch (role) {
            case "admin" -> new Admin(username, email, role);
            case "user" -> new Customer(username, email, role);
            case "customer" -> new ProjectManager(username, email, role);
            case null, default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
