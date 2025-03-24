package hw4;

/**
 * Order class represents an order in the system.
 * It encapsulates various attributes of an order, such as order number, order
 * time,
 * required time, number of pittas, number of chicken, number of sheftales,
 * number of mix, and number of potatoes.
 */
public class Order {
    private int num; // Order number
    private int torder; // Order time
    private int treq; // Requested time
    private int npp; // Number of pork
    private int npc; // Number of chicken
    private int nps; // Number of sheftales
    private int npm; // Number of mix
    private int npf; // Number of fries
    private static int pittaLength;
    private static int souvlakiSkewerLength;
    private static int sheftalaSkewerLength;
    private int orderTime;
    private int timeDelivered;

    /**
     * Constructor to initialize a new Order object with the given parameters.
     * 
     * @param num    Order number
     * @param torder Order time
     * @param treq   Requesetd time
     * @param npp    Number of pork
     * @param npc    Number of chicken
     * @param nps    Number of sheftales
     * @param npm    Number of mix
     * @param npf    Number of fries
     */
    public Order(int num, int torder, int treq, int npp, int npc, int nps, int npm, int npf) {
        this.num = num;
        this.torder = torder;
        this.treq = treq;
        this.npp = npp;
        this.npc = npc;
        this.nps = nps;
        this.npm = npm;
        this.npf = npf;
    }

    /**
     * Constructs a new Order object with the same attributes as the provided Order
     * object.
     * 
     * @param order The Order object from which to copy the attributes.
     */
    public Order(Order order) {
        this.num = order.num;
        this.torder = order.torder;
        this.treq = order.treq;
        this.npp = order.npp;
        this.npc = order.npc;
        this.nps = order.nps;
        this.npm = order.npm;
        this.npf = order.npf;
        this.orderTime = order.orderTime;
    }

    /**
     * Getter for the order number.
     * 
     * @return The order number.
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Getter for the order time.
     * 
     * @return The order time.
     */
    public int getTorder() {
        return torder;
    }

    /**
     * Getter for the requested time.
     * 
     * @return The requested time.
     */
    public int getTreq() {
        return treq;
    }

    /**
     * Getter for the number of pork pittas.
     * 
     * @return The number of pork pittas.
     */
    public int getNpp() {
        return npp;
    }

    /**
     * Getter for the number of chicken pittas.
     * 
     * @return The number of chicken pittas.
     */
    public int getNpc() {
        return npc;
    }

    /**
     * Getter for the number of sheftala pittas.
     * 
     * @return The number of sheftala pittas.
     */
    public int getNps() {
        return nps;
    }

    /**
     * Getter for the number of mix pittas.
     * 
     * @return The number of mix pittas.
     */
    public int getNpm() {
        return npm;
    }

    /**
     * Getter for the number of fries.
     * 
     * @return The number of fries.
     */
    public int getNpf() {
        return npf;
    }

    /**
     * Method to calculate the total length of the order.
     * 
     * @return The total length of the order.
     */
    public int getOrderLength() {
        return pittaLength * (npp + npc + nps + npm) + 2 * (souvlakiSkewerLength * (npc + npp)
                + sheftalaSkewerLength * nps) + npm * souvlakiSkewerLength + npm * sheftalaSkewerLength;
    }

    /**
     * Getter for the pitta length.
     * 
     * @return The length of a pitta.
     */
    public static int getPittaLength() {
        return pittaLength;
    }

    /**
     * Retrieves the length of the souvlaki skewer.
     * 
     * @return The length of the souvlaki skewer.
     */
    public static int getSouvlakiSkewerLength() {
        return souvlakiSkewerLength;
    }

    /**
     * Retrieves the length of the sheftala skewer.
     * 
     * @return The length of the sheftala skewer.
     */
    public static int getSheftalaSkewerLength() {
        return sheftalaSkewerLength;
    }

    /**
     * Setter for the pitta length.
     * 
     * @param length The length of a pitta to set.
     */
    public static void setPittaLength(int length) {
        pittaLength = length;
    }

    /**
     * Setter for the souvlaki skewer length.
     * 
     * @param length The length of a souvlaki skewer to set.
     */
    public static void setSouvlakiSkewerLength(int length) {
        souvlakiSkewerLength = length;
    }

    /**
     * Setter for the sheftala skewer length.
     * 
     * @param length The length of a sheftala skewer to set.
     */
    public static void setSheftalaSkewerLength(int length) {
        sheftalaSkewerLength = length;
    }

    /**
     * Setter for the order number.
     * 
     * @param num The order number to set.
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Setter for the order time.
     * 
     * @param orderTime The order time to set.
     */
    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * Returns the order time.
     * 
     * @return The order time.
     */
    public int getOrderTime() {
        return this.orderTime;
    }

    /**
     * Sets the time when the order is delivered.
     * 
     * @param timeNow The current time.
     */
    public void setTimeDelivered(int timeNow) {
        this.timeDelivered = timeNow + orderTime;
    }

    // Getter method to get the delivery time
    public int getTimeDelivered() {
        return timeDelivered;
    }

    /**
     * Method to calculate the weight of the order.
     * 
     * @param tnow The current time.
     * @return The weight of the order.
     */
    public int getWeight(int tnow) {
        if (orderTime != 0)
            return (orderTime - (treq - tnow)) / orderTime;

        return 0;
    }

    /**
     * Method to represent the order as a string.
     * 
     * @return A string representation of the order.
     */
    public String toString() {
        return this.num + "\t" + this.torder + "\t\t" + this.treq + "\t\t" + this.npp + "\t" + this.npc + "\t"
                + this.nps + "\t" + this.npm + "\t" + this.npf;
    }

}