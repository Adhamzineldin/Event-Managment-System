package org.eventmanagmentsystem.utils;

public class ValidatorUtil {

    // Validates username (at least 3 characters, alphanumeric)
    public static boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9]{3,}$");
    }

    // Validates password (at least 8 characters, includes a number and a letter)
    public static boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}
