package hw4;

/**
 * The Customer class represents a customer in the restaurant.
 * Each customer has an associated order and a satisfaction status.
 */
public class Customer {
    private Order order;
    private boolean satisfied;

    /**
     * Constructs a new Customer object with the given order.
     *
     * @param order the order associated with the customer
     */
    public Customer(Order order) {
        this.order = order;
    }

    /**
     * Constructs a new Customer object by copying another customer's order.
     *
     * @param customer the customer whose order is to be copied
     */
    public Customer(Customer customer) {
        this.order = customer.getOrder();
    }

    /**
     * Sets the satisfaction status of the customer based on the preparation time.
     *
     * @param prepTime the preparation time for the customer's order
     */
    public void isSatisfied(int prepTime) {
        this.satisfied = prepTime <= order.getTreq();
    }

    /**
     * Gets the order associated with the customer.
     *
     * @return the order associated with the customer
     */
    public Order getOrder() {
        return this.order;
    }

    /**
     * Gets the satisfaction status of the customer.
     *
     * @return true if the customer is satisfied, false otherwise
     */
    public boolean getSatisfaction() {
        return this.satisfied;
    }
}