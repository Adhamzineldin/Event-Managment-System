package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.interfaces.EmailService;
import org.eventmanagmentsystem.models.Event;

import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class SmtpEmailService implements EmailService {

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

    // Implementation of generateCustomerEmailContent from the interface
    @Override
    public String generateCustomerEmailContent(Event event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "<html><head><style>" +
                "body {font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 20px;}" +
                ".container {max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);}" +
                "h2 {text-align: center; color: #333;}" +
                "table {width: 100%; margin-top: 20px; border-collapse: collapse;}" +
                "table, th, td {border: 1px solid #ddd;}" +
                "th, td {padding: 12px; text-align: left;}" +
                "th {background-color: #f2f2f2;}" +
                ".footer {text-align: center; font-size: 12px; color: #777; margin-top: 20px;}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<h2>Event Details - Event ID: " + event.getEventId() + "</h2>" +
                "<table>" +
                "<tr><th>Customer ID</th><td>" + event.getCustomerId() + "</td></tr>" +
                "<tr><th>Manager ID</th><td>" + event.getManagerId() + "</td></tr>" +
                "<tr><th>Event Date</th><td>" + dateFormat.format(event.getEventDate()) + "</td></tr>" +
                "<tr><th>Event Duration</th><td>" + event.getEventDuration() + " hours</td></tr>" +
                "<tr><th>Seats</th><td>" + event.getSeats() + "</td></tr>" +
                "<tr><th>Cost</th><td>" + event.getCost() + " EGP</td></tr>" +
                "<tr><th>Service Provider Cost</th><td>" + event.getServiceProviderCost() + " EGP</td></tr>" +
                "<tr><th>Status</th><td>" + event.getStatus() + "</td></tr>" +
                "<tr><th>Total Cost</th><td>" + event.calculateTotalCost() + " EGP</td></tr>" +
                "</table>" +
                "<div class='footer'>Thank you for using our Event Management Service.</div>" +
                "</div></body></html>";
    }

    // Implementation of generateManagerEmailContent from the interface
    @Override
    public String generateManagerEmailContent(Event event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "<html><head><style>" +
                "body {font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 20px;}" +
                ".container {max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);}" +
                "h2 {text-align: center; color: #333;}" +
                "table {width: 100%; margin-top: 20px; border-collapse: collapse;}" +
                "table, th, td {border: 1px solid #ddd;}" +
                "th, td {padding: 12px; text-align: left;}" +
                "th {background-color: #f2f2f2;}" +
                ".footer {text-align: center; font-size: 12px; color: #777; margin-top: 20px;}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<h2>Event Details - Event ID: " + event.getEventId() + "</h2>" +
                "<table>" +
                "<tr><th>Customer ID</th><td>" + event.getCustomerId() + "</td></tr>" +
                "<tr><th>Manager ID</th><td>" + event.getManagerId() + "</td></tr>" +
                "<tr><th>Service Provider ID</th><td>" + event.getServiceProviderId() + "</td></tr>" +
                "<tr><th>Event Date</th><td>" + dateFormat.format(event.getEventDate()) + "</td></tr>" +
                "<tr><th>Event Duration</th><td>" + event.getEventDuration() + " hours</td></tr>" +
                "<tr><th>Seats</th><td>" + event.getSeats() + "</td></tr>" +
                "<tr><th>Cost</th><td>" + event.getCost() + " EGP</td></tr>" +
                "<tr><th>Service Provider Cost</th><td>" + event.getServiceProviderCost() + " EGP</td></tr>" +
                "<tr><th>Status</th><td>" + event.getStatus() + "</td></tr>" +
                "<tr><th>Total Cost</th><td>" + event.calculateTotalCost() + " EGP</td></tr>" +
                "</table>" +
                "<div class='footer'>Thank you for managing this event with us.</div>" +
                "</div></body></html>";
    }

    // Implementation of generateServiceProviderEmailContent from the interface
    @Override
    public String generateServiceProviderEmailContent(Event event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "<html><head><style>" +
                "body {font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 20px;}" +
                ".container {max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);}" +
                "h2 {text-align: center; color: #333;}" +
                "table {width: 100%; margin-top: 20px; border-collapse: collapse;}" +
                "table, th, td {border: 1px solid #ddd;}" +
                "th, td {padding: 12px; text-align: left;}" +
                "th {background-color: #f2f2f2;}" +
                ".footer {text-align: center; font-size: 12px; color: #777; margin-top: 20px;}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<h2>Event Details - Event ID: " + event.getEventId() + "</h2>" +
                "<table>" +
                "<tr><th>Customer ID</th><td>" + event.getCustomerId() + "</td></tr>" +
                "<tr><th>Manager ID</th><td>" + event.getManagerId() + "</td></tr>" +
                "<tr><th>Service Provider ID</th><td>" + event.getServiceProviderId() + "</td></tr>" +
                "<tr><th>Event Date</th><td>" + dateFormat.format(event.getEventDate()) + "</td></tr>" +
                "<tr><th>Event Duration</th><td>" + event.getEventDuration() + " hours</td></tr>" +
                "<tr><th>Seats</th><td>" + event.getSeats() + "</td></tr>" +
                "<tr><th>Status</th><td>" + event.getStatus() + "</td></tr>" +
                "</table>" +
                "<div class='footer'>Thank you for providing services for this event.</div>" +
                "</div></body></html>";
    }
}
