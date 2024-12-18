package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.interfaces.EmailService;
import org.eventmanagmentsystem.models.Event;

import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class SmtpEmailService implements EmailService {
    UserService userService = new UserService();

    // Function to send event details to customer, manager, and service provider
    @Override
    public void sendEventDetails(Event event, String customerEmail, String managerEmail, String serviceProviderEmail) {
        try {
            // Setup mail server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", SMTP_HOST);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // Get the default Session object
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                }
            });

            // Prepare the email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));

            // Prepare the email content
            String subject = "Event Details - Event ID: " + event.getEventId();
            String customerContent = generateCustomerEmailContent(event);
            String managerContent = generateManagerEmailContent(event);
            String serviceProviderContent = generateServiceProviderEmailContent(event);

            // Send email to the customer
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));
            message.setSubject(subject);
            message.setContent(customerContent, "text/html; charset=utf-8");
            Transport.send(message);

            // Send email to the manager
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(managerEmail));
            message.setContent(managerContent, "text/html; charset=utf-8");
            Transport.send(message);

            // Send email to the service provider
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(serviceProviderEmail));
            message.setContent(serviceProviderContent, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("Emails sent successfully to customer, manager, and service provider.");
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }

@Override
public String generateCustomerEmailContent(Event event) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return "<html><head><style>" +
            "body {font-family: 'Georgia', serif; background-color: #000; color: #fff; margin: 0; padding: 20px;}" +
            ".container {max-width: 600px; margin: auto; background: radial-gradient(circle, #b8860b, #000); padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.8);}" +
            "h2 {text-align: center; color: #ffd700; font-size: 36px; margin-bottom: 25px; text-shadow: 2px 2px 4px #000;}" +
            "table {width: 100%; margin-top: 25px; border-collapse: collapse; background: rgba(0, 0, 0, 0.6); border: 2px solid #b8860b;}" +
            "th, td {padding: 15px; text-align: left; color: #fff;}" +
            "th {background: linear-gradient(145deg, #000, #000); font-weight: bold; color: #ffd700; text-transform: uppercase; font-size: 18px; text-shadow: 1px 1px 3px #000;}" +
            "td {font-size: 16px; border-bottom: 1px solid #ffd700;}" +
            ".footer {text-align: center; font-size: 16px; color: #ffd700; margin-top: 35px; font-style: italic; text-shadow: 1px 1px 3px #000;}" +
            "</style></head><body>" +
            "<div class='container'>" +
            "<h2>Event Confirmation - Event ID: " + event.getEventId() + "</h2>" +
            "<table>" +
            formatRow("Customer Name", userService.getUserById(event.getCustomerId()).getUserName()) +
            formatRow("Manager Name", userService.getUserById(event.getManagerId()).getUserName()) +
            formatRow("Event Date", dateFormat.format(event.getEventDate())) +
            formatRow("Event Duration", event.getEventDuration() + " hours") +
            formatRow("Seats", String.valueOf(event.getSeats())) +
            formatRow("Cost", event.getCost() + " EGP") +
            formatRow("Service Provider Cost", event.getServiceProviderCost() + " EGP") +
            formatRow("Status", event.getStatus()) +
            formatRow("Total Cost", event.calculateTotalCost() + " EGP") +
            "</table>" +
            "<div class='footer'>Thank you for choosing our Event Management Service.</div>" +
            "</div></body></html>";
}



@Override
public String generateManagerEmailContent(Event event) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return "<html><head><style>" +
            "body {font-family: 'Georgia', serif; background-color: #000; color: #fff; margin: 0; padding: 20px;}" +
            ".container {max-width: 600px; margin: auto; background: radial-gradient(circle, #b8860b, #000); padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.8);}" +
            "h2 {text-align: center; color: #ffd700; font-size: 36px; margin-bottom: 25px; text-shadow: 2px 2px 4px #000;}" +
            "table {width: 100%; margin-top: 25px; border-collapse: collapse; background: rgba(0, 0, 0, 0.6); border: 2px solid #b8860b;}" +
            "th, td {padding: 15px; text-align: left; color: #fff;}" +
            "th {background: linear-gradient(145deg, #000, #000); font-weight: bold; color: #ffd700; text-transform: uppercase; font-size: 18px; text-shadow: 1px 1px 3px #000;}" +
            "td {font-size: 16px; border-bottom: 1px solid #ffd700;}" +
            ".footer {text-align: center; font-size: 16px; color: #ffd700; margin-top: 35px; font-style: italic; text-shadow: 1px 1px 3px #000;}" +
            "</style></head><body>" +
            "<div class='container'>" +
            "<h2>Event Details - Event ID: " + event.getEventId() + "</h2>" +
            "<table>" +
            formatRow("Customer Name", userService.getUserById(event.getCustomerId()).getUserName()) +
            formatRow("Manager Name", userService.getUserById(event.getManagerId()).getUserName()) +
            formatRow("Service Provider Name", userService.getUserById(event.getServiceProviderId()).getUserName()) +
            formatRow("Event Date", dateFormat.format(event.getEventDate())) +
            formatRow("Event Duration", event.getEventDuration() + " hours") +
            formatRow("Seats", String.valueOf(event.getSeats())) +
            formatRow("Cost", event.getCost() + " EGP") +
            formatRow("Service Provider Cost", event.getServiceProviderCost() + " EGP") +
            formatRow("Status", event.getStatus()) +
            formatRow("Total Cost", event.calculateTotalCost() + " EGP") +
            "</table>" +
            "<div class='footer'>Thank you for managing this event with us.</div>" +
            "</div></body></html>";
}

// Implementation of generateServiceProviderEmailContent from the interface
@Override
public String generateServiceProviderEmailContent(Event event) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return "<html><head><style>" +
            "body {font-family: 'Georgia', serif; background-color: #000; color: #fff; margin: 0; padding: 20px;}" +
            ".container {max-width: 600px; margin: auto; background: radial-gradient(circle, #b8860b, #000); padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.8);}" +
            "h2 {text-align: center; color: #ffd700; font-size: 36px; margin-bottom: 25px; text-shadow: 2px 2px 4px #000;}" +
            "table {width: 100%; margin-top: 25px; border-collapse: collapse; background: rgba(0, 0, 0, 0.6); border: 2px solid #b8860b;}" +
            "th, td {padding: 15px; text-align: left; color: #fff;}" +
            "th {background: linear-gradient(145deg, #000, #000); font-weight: bold; color: #ffd700; text-transform: uppercase; font-size: 18px; text-shadow: 1px 1px 3px #000;}" +
            "td {font-size: 16px; border-bottom: 1px solid #ffd700;}" +
            ".footer {text-align: center; font-size: 16px; color: #ffd700; margin-top: 35px; font-style: italic; text-shadow: 1px 1px 3px #000;}" +
            "</style></head><body>" +
            "<div class='container'>" +
            "<h2>Event Details - Event ID: " + event.getEventId() + "</h2>" +
            "<table>" +
            formatRow("Manager Name", userService.getUserById(event.getManagerId()).getUserName()) +
            formatRow("Service Provider Name", userService.getUserById(event.getServiceProviderId()).getUserName()) +
            formatRow("Event Date", dateFormat.format(event.getEventDate())) +
            formatRow("Event Duration", event.getEventDuration() + " hours") +
            formatRow("Seats", String.valueOf(event.getSeats())) +
            formatRow("Status", event.getStatus()) +
            "</table>" +
            "<div class='footer'>Thank you for providing services for this event.</div>" +
            "</div></body></html>";
}

private String formatRow(String label, String value) {
    return "<tr><th>" + label + "</th><td>" + value + "</td></tr>";
}



}
