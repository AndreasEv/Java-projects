package hw4;

/**
 * The Skewer abstract class represents a skewer item that can be grilled.
 * It implements the GrillMenuItem interface.
 */
public abstract class Skewer implements GrillMenuItem {

    protected final int skewerLength; // Length of the skewer
    protected int cookingTimeLeft; // Remaining cooking time for the skewer

    /**
     * Constructs a new Skewer object with the given length.
     *
     * @param length the length of the skewer
     */
    private Skewer(int length) {
        this.skewerLength = length;
    }

    /**
     * Decreases the remaining cooking time of the skewer by one.
     */
    public void decreaseCookingTimeLeft() {
        this.cookingTimeLeft--;
    }

    /**
     * Gets the remaining cooking time of the skewer.
     *
     * @return the remaining cooking time of the skewer
     */
    @Override
    public int getCookingTimeLeft() {
        return this.cookingTimeLeft;
    }

    /**
     * Gets the length of the skewer.
     *
     * @return the length of the skewer
     */
    public int getLength() {
        return this.skewerLength;
    }

    /**
     * The PorkSkewer class represents a pork skewer item.
     */
    public static class PorkSkewer extends Skewer {

        /**
         * Constructs a new PorkSkewer object with the given length.
         *
         * @param length the length of the pork skewer
         */
        public PorkSkewer(int length) {
            super(length);
            this.cookingTimeLeft = (int) Math.floor(Math.random() * (25 - 20 + 1) + 20);
        }

    }

    /**
     * The ChickenSkewer class represents a chicken skewer item.
     */
    public static class ChickenSkewer extends Skewer {

        /**
         * Constructs a new <code>ChickenSkewer</code> object with the given length.
         *
         * @param length the length of the chicken skewer
         */
        public ChickenSkewer(int length) {
            super(length);
            this.cookingTimeLeft = (int) Math.floor(Math.random() * (20 - 15 + 1) + 15);
        }

    }

    /**
     * The SheftalaSkewer class represents a sheftala skewer item.
     */
    public static class SheftalaSkewer extends Skewer {

        /**
         * Constructs a new SheftalaSkewer object with the given length.
         *
         * @param length the length of the sheftala skewer
         */
        public SheftalaSkewer(int length) {
            super(length);
            this.cookingTimeLeft = 25;
        }

    }
}
