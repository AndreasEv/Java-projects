package hw4;

/**
 * The Fries class represents a menu item for fries.
 * It implements the MenuItem interface.
 */
public class Fries implements MenuItem {

    private int portions;
    private int cookingTimeLeft;

    /**
     * Constructs a new Fries object with the given number of portions.
     *
     * @param portions the number of portions of fries
     */
    public Fries(int portions) {
        this.portions = portions;
        this.cookingTimeLeft = 20;
    }

    /**
     * Gets the number of portions of fries.
     *
     * @return the number of portions of fries
     */
    public int getPortions() {
        return this.portions;
    }

    /**
     * Sets the number of portions of fries.
     *
     * @param portions the new number of portions of fries
     */
    public void setPortions(int portions){
        this.portions = portions;
    }

    /**
     * Returns the cooking time left per portion of fries.
     *
     * @return the cooking time left per portion of fries
     */
    @Override
    public int getCookingTimeLeft() {
        return this.cookingTimeLeft;
    }

    /**
     * Decreases the cooking time left per portion of fries by one.
     */
    @Override
    public void decreaseCookingTimeLeft() {
        this.cookingTimeLeft--;
    }

}