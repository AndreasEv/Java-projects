package project231;

public class MinHeap {
    private WordWithImportance[] heap; // Array to store heap elements
    private int size; // Current size of the heap
    private final int capacity; // Maximum capacity of the heap

    public static class WordWithImportance {
        String word;
        int importance;

        public WordWithImportance(String word, int importance) {
            this.word = word;
            this.importance = importance;
        }
    }

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new WordWithImportance[capacity + 1]; // We use 1-based indexing for simplicity
        this.size = 0;
    }

    // Helper method to check if the heap is empty
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Helper method to check if the heap is full
    public boolean isFull() {
        return this.size == this.capacity;
    }

    public boolean contains(String word) {
        for (int i = 1; i <= size; i++) { // Start from index 1 to size (1-based indexing)
            if (heap[i].word.equals(word)) {
                return true; // Word found in the heap
            }
        }
        return false; // Word not found
    }
    // Insert a new element into the heap
    public void insert(String word, int importance) {
        if(contains(word) || importance == 0)
            return;
        if (isFull()) {
            // Replace the root if the new word has a higher importance
            if (importance > heap[1].importance) {
                heap[1] = new WordWithImportance(word, importance);
                heapifyDown(1); // Reheapify after replacing the root
            }
            return; // If the heap is full, stop further insertion
        }

        // Create a new WordWithImportance object
        WordWithImportance newWord = new WordWithImportance(word, importance);

        // Increment size and place the new element at the end
        size++;
        heap[size] = newWord;

        // Heapify up to restore the min-heap property
        heapifyUp(size);
    }

    // Heapify up to maintain the min-heap property
    private void heapifyUp(int index) {
        while (index > 1 && heap[index].importance < heap[index / 2].importance) {
            // Swap with parent if the current node is smaller
            swap(index, index / 2);
            index = index / 2;
        }
    }

    // Delete the minimum element (the root) and return it
    public WordWithImportance deleteMin() {
        if (isEmpty()) {
            System.out.println("Heap is empty!");
            return null;
        }

        // The root contains the minimum element
        WordWithImportance minElement = heap[1];

        // Replace the root with the last element
        heap[1] = heap[size];
        size--;

        // Heapify down to restore the min-heap property
        heapifyDown(1);

        return minElement;
    }

    // Heapify down to maintain the min-heap property
    private void heapifyDown(int index) {
        int smallest = index;
        int leftChild = 2 * index;
        int rightChild = 2 * index + 1;

        // Check if the left child exists and is smaller than the current node
        if (leftChild <= size && heap[leftChild].importance < heap[smallest].importance) {
            smallest = leftChild;
        }

        // Check if the right child exists and is smaller than the current node
        if (rightChild <= size && heap[rightChild].importance < heap[smallest].importance) {
            smallest = rightChild;
        }

        // If the smallest is not the current node, swap and continue heapifying down
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    // Swap two elements in the heap
    private void swap(int i, int j) {
        WordWithImportance temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Get all elements in the heap (for demonstration or testing purposes)
    public WordWithImportance[] getElements() {
        WordWithImportance[] result = new WordWithImportance[size];
        System.arraycopy(heap, 1, result, 0, size); // Copy the elements (skip index 0 as it's unused)
        return result;
    }

    // Get the current size of the heap
    public int getSize() {
        return size;
    }
}
