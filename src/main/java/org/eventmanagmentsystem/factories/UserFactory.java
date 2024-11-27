package org.eventmanagmentsystem.factories;

import org.eventmanagmentsystem.models.Admin;
import org.eventmanagmentsystem.models.Customer;
import org.eventmanagmentsystem.models.ProjectManager;
import org.eventmanagmentsystem.models.User;

public class UserFactory {

    public static User createUser(int id, String username, String email, String role) {
        return switch (role) {
            case "admin" -> new Admin(id, username, email, role);
            case "user" -> new Customer(id, username, email, role);
            case "customer" -> new ProjectManager(id, username, email, role);
            case null, default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
