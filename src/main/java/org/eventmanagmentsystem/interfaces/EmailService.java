package org.eventmanagmentsystem.interfaces;

import org.eventmanagmentsystem.models.Event;

public interface EmailService {
    String SMTP_HOST = "smtp.gmail.com";
    String FROM_EMAIL = "Adhams.botmail@gmail.com";
    String SMTP_USERNAME = "Adhams.botmail@gmail.com";
    String SMTP_PASSWORD = "dxqx flmj ymlx srzx";

    void sendEventDetails(Event event, String customerEmail, String managerEmail, String serviceProviderEmail);

    String generateCustomerEmailContent(Event event);
    String generateManagerEmailContent(Event event);
    String generateServiceProviderEmailContent(Event event);
}
