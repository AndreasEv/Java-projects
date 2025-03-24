package hw4;

import java.util.ArrayList;

/**
 * Grill class represents a grill with a certain length.
 */
public class Grill {

    private final int coalTime;
    private final int length; // Length of the grill
    private int availableLength;
    private ArrayList<GrillMenuItem> grillItems;

    /**
     * Constructor to initialize the grill with a given length.
     * 
     * @param length The length of the grill
     */
    public Grill(int length, int coalTime) {
        this.length = length;
        this.availableLength = length;
        this.coalTime = coalTime;
        grillItems = new ArrayList<GrillMenuItem>();
    }

    /**
     * Decreases the available space in the grill by the length of an order.
     * 
     * @param length The length of the order to be added to the grill.
     */
    public void addOrder(Order order) {

        int maxTime = 0;

        if (order.getNps() != 0 || order.getNpm() != 0)
            maxTime = 25;

        for (int i = 0; i < order.getNpp() + order.getNpc() + order.getNpm() + order.getNps(); i++) {
            grillItems.add(new Pitta(Order.getPittaLength())); // Add pittas first because they have the lowest cooking
                                                               // time
        }
        for (int i = 0; i < order.getNpc() * 2; i++) {

            Skewer skewer = new Skewer.ChickenSkewer(Order.getSouvlakiSkewerLength());
            if (maxTime < skewer.getCookingTimeLeft())
                maxTime = skewer.getCookingTimeLeft();

            grillItems.add(skewer); // Add chiken skewers

        }
        for (int i = 0; i < order.getNpp() * 2 + order.getNpm(); i++) {

            Skewer skewer = new Skewer.PorkSkewer(Order.getSouvlakiSkewerLength());
            if (maxTime < skewer.getCookingTimeLeft())
                maxTime = skewer.getCookingTimeLeft();

            grillItems.add(skewer); // Add chiken skewers

        }
        for (int i = 0; i < order.getNps() * 2 + order.getNpm(); i++) {
            grillItems.add(new Skewer.SheftalaSkewer(Order.getSheftalaSkewerLength())); // Add sheftala skewers
        }

        if (maxTime < 20 && order.getNpf() != 0)
            maxTime = 20;

        order.setOrderTime(maxTime);
        this.availableLength -= order.getOrderLength(); // Update the grill's available length
    }

    public void removeItem(GrillMenuItem item) {
        this.availableLength += item.getLength(); // Remove grill item
    }

    /**
     * Returns the current available space in the grill.
     * 
     * @return The current available space in the grill
     */
    public int getAvailableLength() {
        return this.availableLength;
    }

    /**
     * Retrieves the time required for coal to heat up.
     *
     * @return The time required for coal to heat up.
     */
    public int getCoalTime() {
        return this.coalTime;
    }

    /**
     * Retrieves the length of the grill.
     *
     * @return The length of the grill.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Retrieves the list of grill menu items.
     *
     * @return The list of grill menu items.
     */
    public ArrayList<GrillMenuItem> getGrillItems() {
        return grillItems; // Get the ArrayList
    }

}
