package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.models.Event;
import org.eventmanagmentsystem.models.User;

import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class SmtpEmailService {

    private static final String SMTP_HOST = "smtp.yourmailserver.com";
    private static final String SMTP_PORT = "587";
    private static final String FROM_EMAIL = "your-email@domain.com";
    private static final String FROM_PASSWORD = "your-email-password";

    public void sendEventDetails(Event event, String customerEmail, String managerEmail, String serviceProviderEmail) {
        try {
            String subject = "Event Details - Event ID: " + event.getEventId();
            String customerContent = generateCustomerEmailContent(event);
            String managerContent = generateManagerEmailContent(event);
            String serviceProviderContent = generateServiceProviderEmailContent(event);

            sendEmail(customerEmail, subject, customerContent);
            sendEmail(managerEmail, subject, managerContent);
            sendEmail(serviceProviderEmail, subject, serviceProviderContent);

            System.out.println("Emails successfully sent.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String recipientEmail, String subject, String content) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=utf-8");
        Transport.send(message);
    }

    public String generateCustomerEmailContent(Event event) {
        return generateEmailTemplate(
                "Event Confirmation - Event ID: " + event.getEventId(),
                new String[][]{
                        {"Customer Name", "John Doe"},
                        {"Event Date", new SimpleDateFormat("yyyy-MM-dd").format(event.getEventDate())},
                        {"Total Cost", "$" + event.calculateTotalCost()}
                });
    }

    public String generateManagerEmailContent(Event event) {
        return generateEmailTemplate(
                "Event Details - Event ID: " + event.getEventId(),
                new String[][]{
                        {"Manager Name", "Jane Manager"},
                        {"Event Date", new SimpleDateFormat("yyyy-MM-dd").format(event.getEventDate())},
                        {"Total Cost", "$" + event.calculateTotalCost()}
                });
    }

    public String generateServiceProviderEmailContent(Event event) {
        return generateEmailTemplate(
                "Event Details - Event ID: " + event.getEventId(),
                new String[][]{
                        {"Service Provider Name", "Alice Provider"},
                        {"Event Date", new SimpleDateFormat("yyyy-MM-dd").format(event.getEventDate())},
                        {"Service Provider Cost", "$" + event.getServiceProviderCost()}
                });
    }

    private String generateEmailTemplate(String title, String[][] rows) {
        StringBuilder tableContent = new StringBuilder();
        for (String[] row : rows) {
            tableContent.append("<tr><th>").append(row[0]).append("</th><td>").append(row[1]).append("</td></tr>");
        }
        return "<html><head><style>"
                + "body {font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;}"
                + ".container {background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1);}"
                + "h2 {text-align: center; color: #333333;}"
                + "table {width: 100%; border-collapse: collapse; margin: 20px 0;}"
                + "th, td {padding: 10px; text-align: left; border-bottom: 1px solid #dddddd;}"
                + "th {background-color: #f4f4f4; font-weight: bold;}"
                + "</style></head><body>"
                + "<div class='container'>"
                + "<h2>" + title + "</h2>"
                + "<table>" + tableContent + "</table>"
                + "<p style='text-align: center;'>Thank you for using our Event Management Service.</p>"
                + "</div></body></html>";
    }
}
