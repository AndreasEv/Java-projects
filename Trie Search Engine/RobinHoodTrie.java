package project231;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RobinHoodTrie {

    protected static int countNodes=0;
    TrieNode root;

    public RobinHoodTrie() {
        root = new TrieNode();
    }

    // Inner class: Element
    public static class Element {

        private char key;
        private int probeLength;
        private TrieNode node;

        public Element(char key, int probeLength) {
            this.key = key;
            this.probeLength = probeLength;
        }

        public int getProbeLength() {
            return this.probeLength;
        }

        public TrieNode getNode() {
            return this.node;
        }

        public void setNode(TrieNode node) {
            this.node = node;
        }

        public char getKey() {
            return this.key;
        }

        public void setProbeLength(int value) {
            this.probeLength = value;
        }
    }

    // Inner class: RobinHoodHashing
    public static class RobinHoodHashing {

        private Element[] table; // Array for storing Elements
        private int capacity; // Current capacity of the table
        private int size; // Number of elements in the table
        private int maxProbeLength; // Maximum probe length of any element

        // Constructor - initializes the table with capacity 5
        public RobinHoodHashing() {
            this.capacity = 5; // Initial capacity
            this.table = new Element[capacity]; // Initialize array
            this.size = 0; // Start with 0 elements
            this.maxProbeLength = 0; // Initially, no probe length
        }

        // Hash function
        public int hash(char key) {
            return Math.abs(key) % capacity;
        }

        // Insert method
        public void insert(char key) {

            if (key>='A' && key<='Z')
                key = (char)(key + 32);
                
            if (this.search(key))
                return; // Avoid duplicates

            if (key < 'a' || key > 'z') {
                System.err.println("Key: '" + key + "' isn't a letter");
                return;
            }

            if (size + 1 >= capacity * 0.9)
                rehash(); // Rehash when 90% full

            int index = hash(key);

            Element newElement = new Element(key, 0);

            while (table[index] != null) {
                if (newElement.probeLength > table[index].probeLength) {
                    table[index].probeLength++;
                    Element temp = newElement;
                    newElement = table[index];
                    table[index] = temp;
                } else {
                    newElement.probeLength++;
                }
                index = (index + 1) % this.capacity; // Use table.length instead of capacity
            }

            table[index] = newElement;
            size++; // Increase the size of inserted elements

            if (maxProbeLength < newElement.getProbeLength())
                maxProbeLength = newElement.getProbeLength();
        }

        public boolean search(char key) {
            int index = hash(key);
            for (int k = 0; k <= maxProbeLength; k++) {
                if (table[index] != null && key == table[index].getKey()) {
                    return true;
                }
                index = (index + 1) % this.capacity; // Use table.length for bounds
            }
            return false;
        }

        public Element getElement(char key) {
            int index = hash(key);
            for (int k = 0; k <= maxProbeLength; k++) {
                if (table[index] != null && key == table[index].getKey()) {
                    return table[index];
                }
                index = (index + 1) % this.capacity;
            }
            return null;
        }

        // Rehash method
        private void rehash() {
            int newCapacity = (capacity == 5) ? 11 : (capacity == 11) ? 19 : 29;

            Element[] oldTable = table;
            table = new Element[newCapacity];
            capacity = newCapacity;
            size = 0;
            maxProbeLength = 0;

            for (Element oldElement : oldTable) {
                if (oldElement != null) {
                    Element newElement = new Element(oldElement.getKey(), 0);
                    newElement.setNode(oldElement.getNode());
                    insertElementDirectly(newElement);
                }
            }
        }

        // helper methd for rehash only
        private void insertElementDirectly(Element element) {
            int index = hash(element.getKey());

            while (table[index] != null) {
                if (element.getProbeLength() > table[index].getProbeLength()) {
                    element.setProbeLength(element.getProbeLength() + 1);
                    Element temp = element;
                    element = table[index];
                    table[index] = temp;
                } else {
                    element.setProbeLength(element.getProbeLength() + 1);
                }
                index = (index + 1) % capacity;
            }

            table[index] = element;
            size++;
            maxProbeLength = Math.max(maxProbeLength, element.getProbeLength());
        }

        public Element[] getTable() {
            return table;
        }

        public void printHash() {
            System.out.print("[ ");
            for (int i = 0; i < capacity; i++) {
                if (table[i] == null) {
                    System.out.print("(_ , _) , ");
                } else {
                    System.out.print("(" + table[i].getKey() + " , " + table[i].getProbeLength() + ") , ");
                }
            }
            System.out.println("]");
        }
    }

    // Inner class: TrieNode
    public static class TrieNode {

        private RobinHoodHashing hash;
        private boolean isEndOfWord;
        private int importance;

        public TrieNode() {
            hash = new RobinHoodHashing();
            isEndOfWord = false;
            importance = 0;
        }

        public int getImportance(){
            return this.importance;
        }
    }

    public void increaseImportance(String key) {
        if (!search(key)) {
            // System.out.println("Word not found in trie for importance: " + key); // Debug
            // log
            return;
        }

        TrieNode node = getWord(key);
        if (node != null) {
            node.importance++;
            // System.out.println("Increased importance for: " + key + " to " +
            // node.importance); // Debug log
        }
    }

    // Insert a string into the trie
    public void insert(String key) {
        if (key == null || key.isEmpty() || search(key)) {
            return; // No need to insert null, empty, or already existing key
        }
    
        TrieNode current = this.root;
    
        for (char c : key.toCharArray()) {
            // Ensure the character is inserted into the hash
            if (current.hash == null) {
                current.hash = new RobinHoodHashing(); // Initialize hash if not already done
            }
            current.hash.insert(c); // Insert character into the hash
    
            // Retrieve the corresponding Element
            Element element = current.hash.getElement(c);
            if (element == null) {
                element = new Element(c, 0); // Create new Element if it doesn't exist
                current.hash.insert(c); // Add the new Element to the hash
            }
    
            // Create a new TrieNode for this Element if necessary
            if (element.getNode() == null) {
                element.setNode(new TrieNode());
            }
    
            // Move to the next TrieNode
            current = element.getNode();
        }
    
        // Mark the current node as the end of a word
        current.isEndOfWord = true;
    }
    

    public TrieNode getWord(String key) {
        TrieNode current = this.root;

        for (int k = 0; k < key.length(); k++) {
            Element element = current.hash.getElement(key.charAt(k));
            if (element == null || element.getNode() == null) {
                return null; // Word doesn't exist
            }
            current = element.getNode();
        }

        return current.isEndOfWord ? current : null;
    }

    // Search for a string in the trie
    public boolean search(String key) {

        if (key == null || key.isEmpty())
            return false;

        // System.out.print("Searching " + key + " : ");

        TrieNode current = this.root;

        for (char c : key.toCharArray()) {
            Element element = current.hash.getElement(c);

            if (element == null || element.getNode() == null) {
                return false; // Character not found or node is not initialized
            }

            current = element.getNode();
        }

        return current.isEndOfWord;
    }

    public MinHeap suggestWords(String input, int k) {
        MinHeap heap = new MinHeap(k);
        findSuggestions(this.root, "", input, heap);
        return heap;
    }

    private void findSuggestions(TrieNode node, String currentWord, String input, MinHeap heap) {
        if (node == null) {
            return;
        }

        // First: Words that match the prefix
        if (node.isEndOfWord && isPrefixMatch(currentWord, input)) {
            heap.insert(currentWord, node.importance);
        }

        // Second: Words that have the same length with small differences
        if (node.isEndOfWord && isSameLengthWithDifference(currentWord, input)) {
            heap.insert(currentWord, node.importance);
        }

        // Third: Words that have a different length with small differences
        if (node.isEndOfWord && isDifferentLengthMatch(currentWord, input)) {
            heap.insert(currentWord, node.importance);
        }

        // Recursively find suggestions in child nodes
        for (Element element : node.hash.getTable()) {
            if (element != null) {
                findSuggestions(element.getNode(), currentWord + element.getKey(), input, heap);
            }
        }
    }

    private boolean isPrefixMatch(String word, String input) {
        boolean match = word.startsWith(input);
        return match;
    }

    private boolean isSameLengthWithDifference(String word, String input) {
        if (word.length() != input.length()) {
            return false;
        }

        int diffCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != input.charAt(i)) {
                diffCount++;
            }
        }

        return diffCount <= 2; // Only allow up to 2 differences
    }

    public boolean containsAllLetters(String word, String input) {
        int[] charCountWord = new int[26]; // Assuming only lowercase letters
        int[] charCountInput = new int[26]; // Assuming only lowercase letters
    
        // Count characters in the word
        for (char c : word.toCharArray()) {
            charCountWord[c - 'a']++;
        }
    
        // Count characters in the input
        for (char c : input.toCharArray()) {
            charCountInput[c - 'a']++;
        }
    
        // Check if every character in the input is in the word
        for (int i = 0; i < 26; i++) {
            if (charCountInput[i] > 0 && charCountWord[i] < charCountInput[i]) {
                return false; // The word does not contain enough of this character
            }
        }
    
        return true;
    }
    

    private boolean isDifferentLengthMatch(String word, String input) {
        int lengthDiff = (word.length() - input.length());
    
        // Ensure length difference is within allowed bounds (1 to 2)
        if (lengthDiff > 2 || lengthDiff < -1) {
            return false;
        }
    
        // Check for partial match when one contains the other
        if (containsAllLetters(input, word)) {
            return true;
        }
    
        // Use a two-pointer approach to allow approximate matches
        int i = 0, j = 0, diffCount = 0;
    
        while (i < word.length() && j < input.length()) {
            if (word.charAt(i) == input.charAt(j)) {
                // Characters match, move both pointers
                i++;
                j++;
            } else {
                // Characters don't match, count the difference
                diffCount++;
                if (diffCount > 2) {
                    return false; // Exceeding allowed differences
                }
    
                // Skip a character from the longer string
                if (word.length() > input.length()) {
                    i++;
                } else if (word.length() < input.length()) {
                    j++;
                } else {
                    i++;
                    j++;
                }
            }
        }
    
        // Account for any remaining characters in the longer string
        diffCount += Math.abs(word.length() - i) + Math.abs(input.length() - j);
    
        // Allow up to 2 differences
        return diffCount <= 2;
    }
   
    public long calculateTotalBytes(RobinHoodTrie trie) {
        if (trie == null || trie.root == null) {
            return 0;
        }
        return calculateTrieNodeMemory(trie.root);
    }
    
    private long calculateTrieNodeMemory(TrieNode node) {
        countNodes++;
        if (node == null) {
            return 4; // Null TrieNode occupies 4 bytes
        }
    
        // Base size for TrieNode: isEndOfWord (1 byte), importance (4 bytes)
        long totalBytes = 1 + 4;
    
        // Add the memory size of the RobinHoodHashing
        RobinHoodHashing hash = node.hash;
        if (hash != null) {
            totalBytes += 12; // Base size of RobinHoodHashing
            
            // Calculate memory for the table (array of Element)
            for (Element element : hash.table) {
                if (element == null) {
                    totalBytes += 4; // Null Element occupies 4 bytes
                } else {
                    totalBytes += 10; // Non-null Element occupies 10 bytes
    
                    // Check the TrieNode inside the Element
                    if (element.node == null) {
                        totalBytes += 4; // Null TrieNode in Element occupies 4 bytes
                    } else {
                        totalBytes += 9; // Non-null TrieNode in Element occupies 9 bytes
                        totalBytes += calculateTrieNodeMemory(element.node); // Recursively calculate memory for child TrieNode
                    }
                }
            }
        }
    
        return totalBytes;
    }

    public static void main(String[] args) {
        RobinHoodTrie trie = new RobinHoodTrie();

        // File paths (update as needed for your system)
        String dictionaryPath = "dictionary.txt";
        String importancePath = "importance.txt";

        // Insert words from dictionary.txt into the trie
        try (Scanner scanner = new Scanner(new FileReader(dictionaryPath))) {
            while (scanner.hasNext()) {
                String word = scanner.next().trim();
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
                System.out.println(extractedWords);
            }
        } catch (IOException e) {
            System.err.println("Error reading importance file: " + e.getMessage());
        }


        // Print the importance of all words from the dictionary
        System.out.println("\nWord Importances:");
        try (Scanner scanner = new Scanner(new FileReader(dictionaryPath))) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                TrieNode node = trie.getWord(word);
                int importance = (node != null) ? node.importance : 0;
                System.out.println("Word: " + word + ", Importance: " + importance);
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        }

        // Get suggestions
        String input = "pattix";
        int k = 7;
        MinHeap suggestions = trie.suggestWords(input, k);

        System.out.println("\nTop Suggestions for \"" + input + "\":");
        for (MinHeap.WordWithImportance suggestion : suggestions.getElements()) {
            if (suggestion != null) {
                System.out.println("Word: " + suggestion.word + ", Importance: " + suggestion.importance);
            }
        }
        // System.out.println("\nTesting calculateTotalCellsHash:");
        // long totalCells = RobinHoodTrie.calculateTotalCells??Hash(trie);
        // System.out.println("Total cells in trie: " + totalCells);
    }


    public void printShortWords(int maxLength) {
        findAndPrintWords(this.root, "", maxLength);
    }
    
    private void findAndPrintWords(TrieNode node, String currentWord, int maxLength) {
        if (node == null) {
            return;
        }
    
        // If the current word is a valid end of a word and its length is within the limit, print it
        if (node.isEndOfWord && currentWord.length() <= maxLength) {
            System.out.println(currentWord);
        }
    
        // Traverse all children
        for (Element element : node.hash.getTable()) {
            if (element != null) {
                findAndPrintWords(element.getNode(), currentWord + element.getKey(), maxLength);
            }
        }
    }

    public void printImportantWords(int minImportance) {
        findAndPrintImportantWords(this.root, "", minImportance);
    }
    
    private void findAndPrintImportantWords(TrieNode node, String currentWord, int minImportance) {
        if (node == null) {
            return;
        }
    
        // If the current word is a valid end of a word and its importance meets the criteria, print it
        if (node.isEndOfWord && node.importance >= minImportance) {
            System.out.println(currentWord + " (Importance: " + node.importance + ")");
        }
    
        // Traverse all children
        for (Element element : node.hash.getTable()) {
            if (element != null) {
                findAndPrintImportantWords(element.getNode(), currentWord + element.getKey(), minImportance);
            }
        }
    }
}