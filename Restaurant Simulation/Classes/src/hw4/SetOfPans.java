package hw4;

/**
 * The <code>SetOfPans</code> class represents a set of pans in the kitchen.
 * It encapsulates the total space and available space in the set of pans.
 */
public class SetOfPans {

    private final int totalSpace; // Total space in the set of pans
    private int availableSpace; // Available space in the set of pans

    /**
     * Constructs a new <code>SetOfPans</code> object with the given capacity and number of pans.
     *
     * @param capacity the capacity of each pan
     * @param pans     the number of pans in the set
     */
    public SetOfPans(int capacity, int pans) {
        this.totalSpace = capacity * pans;
        this.availableSpace = this.totalSpace;
    }

    /**
     * Gets the total space in the set of pans.
     *
     * @return the total space in the set of pans
     */
    public int getTotalSpace() {
        return this.totalSpace;
    }

    /**
     * Gets the available space in the set of pans.
     *
     * @return the available space in the set of pans
     */
    public int getAvailableSpace() {
        return this.availableSpace;
    }

    /**
     * Adds the specified number of portions to the pans, decreasing the available space.
     *
     * @param portions the number of portions to add to the pans
     */
    public void addToPans(int portions) {
        this.availableSpace -= portions;
    }

    /**
     * Removes the specified number of portions from the pans, increasing the available space.
     *
     * @param portions the number of portions to remove from the pans
     */
    public void removeFromPans(int portions) {
        this.availableSpace += portions;
    }
}
