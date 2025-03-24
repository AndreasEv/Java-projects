package hw4;

/**
 * The <code>Pitta</code> class represents a menu item for pitta.
 * It implements the <code>GrillMenuItem</code> interface.
 */
public class Pitta implements GrillMenuItem {

    private int length;
    private int cookingTimeLeft;

    /**
     * Constructs a new <code>Pitta</code> object with the given length.
     *
     * @param length the length of the pitta
     */
    public Pitta(int length) {
        this.length = length;
        this.cookingTimeLeft = 5;
    }

    /**
     * Gets the remaining cooking time of the pitta.
     *
     * @return the remaining cooking time of the pitta
     */
    @Override
    public int getCookingTimeLeft() {
        return this.cookingTimeLeft;
    }

    /**
     * Gets the length of the pitta.
     *
     * @return the length of the pitta
     */
    @Override
    public int getLength() {
        return this.length;
    }

    /**
     * Decreases the remaining cooking time of the pitta by one.
     */
    @Override
    public void decreaseCookingTimeLeft() {
        this.cookingTimeLeft--;
    }

}
