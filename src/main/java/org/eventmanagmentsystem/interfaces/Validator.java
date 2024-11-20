package org.eventmanagmentsystem.interfaces;

public interface Validator<T> {
    boolean validate(T input);
}
