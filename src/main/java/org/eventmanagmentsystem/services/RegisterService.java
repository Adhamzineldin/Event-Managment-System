package org.eventmanagmentsystem.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterService {

    private static final String FILE_PATH = "data/users.txt"; // Path to the text file containing user data
    private static final String ID_FILE_PATH = "data/last_user_id.txt"; // Path to store the last used user ID

    public boolean saveUser(String username, String email, String password) {
        int userId = getNextUserId();

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

    private int getNextUserId() {
        int lastUserId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            String lastId = reader.readLine();
            if (lastId != null) {
                lastUserId = Integer.parseInt(lastId);
            }
        } catch (IOException e) {
            // If the ID file does not exist or cannot be read, assume ID starts from 0
            e.printStackTrace();
        }

        // Increment the last ID to get the next user ID
        int nextUserId = lastUserId + 1;

        // Update the last user ID in the ID file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ID_FILE_PATH))) {
            writer.write(String.valueOf(nextUserId));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nextUserId;
    }
}
