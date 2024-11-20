package org.eventmanagmentsystem.utils;

public class ValidatorUtil implements org.eventmanagmentsystem.interfaces.Validator<String> {
    @Override
    public boolean validate(String input) {
        return input != null && !input.isEmpty();
    }


}
