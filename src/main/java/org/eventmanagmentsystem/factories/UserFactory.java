package org.eventmanagmentsystem.factories;

import org.eventmanagmentsystem.models.*;

public class UserFactory {

    public static User createUser(int id, String username, String password, String email, String role) {
        return switch (role.toLowerCase()) {
            case "admin" -> new Admin(id, username,password, email, role);
            case "customer" -> new Customer(id, username,password, email, role);
            case "manager" -> new ProjectManager(id, username,password, email, role);
            case "serviceprovider" -> new ServiceProvider(id, username, password, email, role);
            case null, default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
