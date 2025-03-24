package project231;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestClass {

    public static void main(String[] args) {
        RobinHoodTrie trie = new RobinHoodTrie();

        // File paths (update as needed for your system)
        String dictionaryPath = "DiffWords.txt";
        String importancePath = "importance.txt";

        // Insert words from dictionary.txt into the trie
        try (Scanner scanner = new Scanner(new FileReader(dictionaryPath))) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                trie.insert(word);
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        }

        // Extract and process words from importance.txt
        try (FileReader reader = new FileReader(importancePath);
             Scanner scanner = new Scanner(reader)) {
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append(" ");
            }
            String extractedWords = wordEdit.extractWords(content.toString());
            try (Scanner wordScanner = new Scanner(extractedWords)) {
                while (wordScanner.hasNext()) {
                    String word = wordScanner.next();
                    trie.increaseImportance(word);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading importance file: " + e.getMessage());
        }

        // Get user input for suggestions
        try (Scanner userInputScanner = new Scanner(System.in)) {
            System.out.print("\nSearch: ");
            String input = userInputScanner.nextLine();

            System.out.print("Enter the number of suggestions (k): ");
            int k = userInputScanner.nextInt();

            MinHeap suggestions = trie.suggestWords(input, k);

            System.out.println("\nTop Suggestions for \"" + input + "\":");
            for (MinHeap.WordWithImportance suggestion : suggestions.getElements()) {
                if (suggestion != null) {
                    System.out.println("Word: " + suggestion.word + ", Importance: " + suggestion.importance);
                }
            }
        }

        // // Testing calculateTotalBytes
        // System.out.println("\nTesting calculateTotalBytes:");
        // long totalBytes = trie.calculateTotalBytes(trie);

        // System.out.println("Total bytes in trie: " + totalBytes);
        // System.out.println("Total bytes in count: " + trie.count);
    }
}
