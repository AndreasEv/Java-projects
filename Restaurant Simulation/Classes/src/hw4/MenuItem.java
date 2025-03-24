package hw4;

/**
 * The <code>MenuItem</code> interface represents a menu item that can be ordered.
 */
public interface MenuItem {

    /**
     * Gets the remaining cooking time of the menu item.
     *
     * @return the remaining cooking time of the menu item
     */
    int getCookingTimeLeft();

    /**
     * Decreases the remaining cooking time of the menu item by one.
     */
    void decreaseCookingTimeLeft();

}
