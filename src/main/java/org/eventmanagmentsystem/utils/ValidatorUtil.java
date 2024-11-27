package org.eventmanagmentsystem.utils;

import java.util.regex.Pattern;

public class ValidatorUtil {

    public String registrationValidator(String username, String email, String password, String confirmPassword) {
        if (!RegistrationValidator.isNonEmpty(username, email, password, confirmPassword)) {
            return "All fields are required";
        } else if (!RegistrationValidator.isValidEmail(email)) {
            return "Invalid email address";
        }  else if (!RegistrationValidator.isValidPassword(password, 8)) {
            return "Password must be at least 8 characters long";
        } else if (!RegistrationValidator.passwordsMatch(password, confirmPassword)){
            return "Passwords do not match";
        } else {
            return "Valid";
        }
    }

}

class RegistrationValidator {

    int passwordLength = 8;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    static boolean isNonEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    static boolean isValidPassword(String password, int length) {
        return password.length() >= length;
    }

    static boolean passwordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
