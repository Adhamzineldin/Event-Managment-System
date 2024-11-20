package org.eventmanagmentsystem.models;

public class Customer extends User{

    public Customer(String userName, String password) {
        super(userName, password, "Customer");
    }
}
