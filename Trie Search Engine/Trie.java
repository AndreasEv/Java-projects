package project231;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Trie {

    private static class TrieNode {
        private static final int SIZE = 26;
        TrieNode[] children;
        boolean isEndOfWord; // To mark the end of a word

        public TrieNode() {
            children = new TrieNode[SIZE]; // Automatically initialized to null
            isEndOfWord = false; // Initialize to false
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Calculate index for each character
            if (index < 0 || index >= TrieNode.SIZE) {
                throw new IllegalArgumentException("Invalid character in word: " + c);
            }
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true; // Mark the end of the word
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (index < 0 || index >= TrieNode.SIZE) {
                throw new IllegalArgumentException("Invalid character in word: " + c);
            }
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return current.isEndOfWord; // Ensure it’s a word, not just a prefix
    }

    public long calculateTrieMemory() {
        return calculateTrieNodeMemory(root);
    }
    
    private long calculateTrieNodeMemory(TrieNode node) {
        if (node == null) {
            return 4; // Null TrieNode pointer occupies 4 bytes
        }
    
        // Base memory for the current TrieNode
        long totalBytes = 1 + 4; // Memory for boolean and importance(we count it for both tries)
    
        // Add memory for the children array
        for (TrieNode child : node.children) {
            if (child == null) {
                totalBytes += 4; // Null child pointer occupies 4 bytes
            } else {
                totalBytes += 9; // Non-null child pointer occupies 9 bytes
                totalBytes += calculateTrieNodeMemory(child); // Recursively calculate child node's memory
            }
        }
    
        return totalBytes;
    }
    public static void main(String[] args) {
        Trie trie = new Trie();
        String filePath = "DiffLength.txt"; // Replace with your file path
    
        // Reading words from file and inserting them into the Trie
        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim().toLowerCase(); // Remove extra spaces and convert to lowercase
                if (!word.isEmpty()) {
                    trie.insert(word);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    
        System.out.println(trie.search("eschatologically"));
        System.out.println(trie.search("cookieXR"));
        long memoryUsage = trie.calculateTrieMemory();
        System.out.println("Total memory used by the Trie: " + memoryUsage + " bytes");
    
       
    }
    
}
