package project231;

import java.io.*;
import java.nio.file.*;

public class LowercaseFileModifier {
    public static void main(String[] args) {
        // Check if the user provided a file path as an argument
        if (args.length != 1) {
            System.out.println("Usage: java LowercaseFileModifier <file-path>");
            return;
        }

        // Get the file path from the command-line arguments
        String filePath = "smallText.txt";

        // Modify the file content to lowercase
        try {
            modifyFileToLowercase(filePath);
            System.out.println("File modified successfully: All content converted to lowercase.");
        } catch (IOException e) {
            System.err.println("An error occurred while modifying the file: " + e.getMessage());
        }
    }

    public static void modifyFileToLowercase(String filePath) throws IOException {
        // Read the file content into a String
        Path path = Paths.get(filePath);
        String content = Files.readString(path);

        // Convert the content to lowercase
        String lowerCaseContent = content.toLowerCase();

        // Write the modified content back to the same file
        Files.writeString(path, lowerCaseContent);
    }
}
