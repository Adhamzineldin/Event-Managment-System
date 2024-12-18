package org.eventmanagmentsystem.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RegisterService {

    private static final String FILE_PATH = "data/users.txt"; // Path to the text file containing user data
    private static final String ID_FILE_PATH = "data/last_user_id.txt"; // Path to store the last used user ID

    public boolean saveUser(String username, String email, String password) {
        int userId = generateUniqueId();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Save the new user in the format: "id,username,password,email,role"
            String newUserRecord = String.format("%d,%s,%s,%s,%s%n", userId, username, password, email, "customer");
            writer.write(newUserRecord);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

     private int generateUniqueId() {
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomPart = new Random().nextInt(100); // 2-digit random number (0-99)

        // Combine the date and random parts, ensuring it's smaller than Integer.MAX_VALUE
        String uniqueIdString = datePart.substring(2) + String.format("%02d", randomPart); // Get the last 6 digits of the date and add the random part
        return Integer.parseInt(uniqueIdString); // Convert to integer
    }
}
