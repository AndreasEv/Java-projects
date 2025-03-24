package hw4;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Kitchen class represents a kitchen where orders are processed.
 */
public class Kitchen {

    private Grill grill; // The grill for cooking skewers
    private ArrayList<Customer> customers; // The list of customers waiting for their orders
    private SetOfPans pans; // The set of pans for cooking fries and other items

    /**
     * Constructs a Kitchen object with the given parameters.
     * 
     * @param length               The length of the grill.
     * @param coalTime             The time it takes for the coals to heat up.
     * @param numOfPans            The number of pans available in the kitchen.
     * @param capacity             The capacity of each pan.
     * @param pittaLength          The length of a pitta bread.
     * @param porkSkewerLength     The length of a pork skewer.
     * @param sheftalaSkewerLength The length of a sheftala skewer.
     */
    public Kitchen(int length, int coalTime, int numOfPans, int capacity, int pittaLength, int porkSkewerLength,
            int sheftalaSkewerLength) {
        grill = new Grill(length, coalTime);
        customers = new ArrayList<>();
        this.pans = new SetOfPans(capacity, numOfPans);
    }

    /**
     * Adds a customer to the kitchen.
     * 
     * @param customer The customer to be added.
     */
    public void addToKitchen(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Finds the customer with the quickest order based on the given time index.
     * 
     * @param indexOfTime The time index to consider for finding the quickest order.
     * @return The customer with the quickest order, or null if there are no
     *         available customers.
     */
    private Customer findQuickestOrder(int indexOfTime) {

        if (customers.isEmpty())
            return null;

        ArrayList<Customer> availableCustomers = new ArrayList<>();
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if (grill.getLength() < customer.getOrder().getOrderLength()) {
                customers.remove(customer);
                i--;
            } else if (customer.getOrder().getTorder() <= indexOfTime)
                availableCustomers.add(customer);
        }

        if (availableCustomers.isEmpty())
            return null;

        int quickestOrder = availableCustomers.get(0).getOrder().getOrderTime();
        Customer quickestOrderCustomer = customers.get(0);

        for (int k = 1; k < availableCustomers.size(); k++)
            if (customers.get(k).getOrder().getOrderTime() < quickestOrder) {
                quickestOrder = availableCustomers.get(k).getOrder().getOrderTime();
                quickestOrderCustomer = availableCustomers.get(k);
            } else if (customers.get(k).getOrder().getOrderTime() == quickestOrder)
                if (quickestOrderCustomer.getOrder().getTreq() > availableCustomers.get(k).getOrder().getTreq()) {
                    quickestOrder = availableCustomers.get(k).getOrder().getOrderTime();
                    quickestOrderCustomer = availableCustomers.get(k);
                }

        return quickestOrderCustomer;
    }

    /**
     * Handles orders based on the specified algorithm.
     * 
     * @param algorithm The algorithm to be used for handling orders.
     */
    public void handleOrders(int algorithm) {
        switch (algorithm) {
            case 1:
                handleOrdersAlgorithmOne();
                break;
            case 2:
                handleOrdersAlgorithmTwo();
                break;
            case 3:
                handleOrdersAlgorithmThree();
                break;
            default:
                System.out.println("Wrong Algorithm given. Give 1, 2 or 3 in the arguments");
                System.exit(0);
        }
    }

    /**
     * Handles orders using algorithm one.
     */
    public void handleOrdersAlgorithmOne() {

        int indexOfOrder = 0;
        int startingCookingTime = 0;
        boolean endOfOrders = false;
        ArrayList<Fries> fries = new ArrayList<>();

        // the coals start heating up from 17:30
        if (grill.getCoalTime() > 30)
            startingCookingTime = grill.getCoalTime() - 30;

        for (int k = startingCookingTime; k <= 360; k++) {
            Order order = customers.get(indexOfOrder).getOrder();
            int portions = order.getNpf();

            while (k >= order.getTorder() && !endOfOrders) {
                if (order.getOrderLength() < grill.getLength()) {
                    if (order.getOrderLength() <= grill.getAvailableLength()) {

                        grill.addOrder(order);
                        if (k + customers.get(indexOfOrder).getOrder().getOrderTime() < 360) {
                            OrderDelivery.addDeviationOfOrder(customers.get(indexOfOrder).getOrder().getTreq()
                                    - (k + customers.get(indexOfOrder).getOrder().getOrderTime()));
                            OrderDelivery.addOrder(order);
                            order.setTimeDelivered(k);
                        }
                        customers.get(indexOfOrder).isSatisfied(k + order.getOrderTime());
                        if (customers.get(indexOfOrder).getSatisfaction())
                            OrderDelivery.addHappyCustomer();
                        fries.add(new Fries(portions));
                        pans.addToPans(portions);

                        if (indexOfOrder + 1 < customers.size()) {
                            indexOfOrder++;
                            order = customers.get(indexOfOrder).getOrder();
                        } else if (indexOfOrder + 1 == customers.size()) {
                            endOfOrders = true;
                        }
                    } else
                        break;
                } else {
                    if (indexOfOrder + 1 == customers.size()) {
                        endOfOrders = true;
                        break;
                    }
                    indexOfOrder++;
                    order = customers.get(indexOfOrder).getOrder();
                }
            }

            removeFromGrill();
            removeFromPans(fries);
        }
    }

    /**
     * Handles orders using algorithm two.
     */
    public void handleOrdersAlgorithmTwo() {
        int startingCookingTime = 0;
        boolean endOfOrders = false;
        ArrayList<Fries> fries = new ArrayList<>();

        // the coals start heating up from 17:30
        if (grill.getCoalTime() > 30)
            startingCookingTime = grill.getCoalTime() - 30;

        for (int k = startingCookingTime; k <= 360; k++) {
            Customer currentCustomer = null;
            int portions = 0;

            if (customers.isEmpty())
                endOfOrders = true;

            try {
                currentCustomer = findQuickestOrder(k);
                portions = currentCustomer.getOrder().getNpf();
            } catch (NullPointerException e) {
                // When a NullPointerException is being caught do nothing and move on
            }

            while (!endOfOrders && currentCustomer != null
                    && grill.getAvailableLength() >= currentCustomer.getOrder().getOrderLength()
                    && pans.getAvailableSpace() >= currentCustomer.getOrder().getNpf()) {
                portions = currentCustomer.getOrder().getNpf();
                grill.addOrder(currentCustomer.getOrder());
                if (k + currentCustomer.getOrder().getOrderTime() < 360) {
                    OrderDelivery.addDeviationOfOrder(
                            currentCustomer.getOrder().getTreq() - (k + currentCustomer.getOrder().getOrderTime()));
                    OrderDelivery.addOrder(currentCustomer.getOrder());
                    currentCustomer.getOrder().setTimeDelivered(k);
                }
                pans.addToPans(portions);
                fries.add(new Fries(portions));
                currentCustomer.isSatisfied(k + currentCustomer.getOrder().getOrderTime());
                if (currentCustomer.getSatisfaction())
                    OrderDelivery.addHappyCustomer();
                customers.remove(currentCustomer);
                currentCustomer = findQuickestOrder(k);
            }

            removeFromGrill();
            removeFromPans(fries);
        }
    }

    /**
     * Handles orders using algorithm three.
     */
    private void handleOrdersAlgorithmThree() {
        int startingCookingTime = 0;
        boolean endOfOrders = false;
        ArrayList<Fries> fries = new ArrayList<>();

        // the coals start heating up from 17:30
        if (grill.getCoalTime() > 30)
            startingCookingTime = grill.getCoalTime() - 30;

        for (int k = startingCookingTime; k <= 360; k++) {
            Customer currentCustomer = null;
            int portions = 0;

            if (customers.isEmpty())
                endOfOrders = true;

            try {
                currentCustomer = findLargestOrder(k);
                portions = currentCustomer.getOrder().getNpf();
            } catch (NullPointerException e) {
                // When a NullPointerException is being caught do nothing
            }

            while (!endOfOrders && currentCustomer != null
                    && grill.getAvailableLength() >= currentCustomer.getOrder().getOrderLength()
                    && pans.getAvailableSpace() >= currentCustomer.getOrder().getNpf()) {
                portions = currentCustomer.getOrder().getNpf();
                grill.addOrder(currentCustomer.getOrder());
                if (k + currentCustomer.getOrder().getOrderTime() < 360) {
                    OrderDelivery.addDeviationOfOrder(
                            currentCustomer.getOrder().getTreq() - (k + currentCustomer.getOrder().getOrderTime()));
                    OrderDelivery.addOrder(currentCustomer.getOrder());
                    currentCustomer.getOrder().setTimeDelivered(k);
                }
                pans.addToPans(portions);
                fries.add(new Fries(portions));
                currentCustomer.isSatisfied(k + currentCustomer.getOrder().getOrderTime());
                if (currentCustomer.getSatisfaction())
                    OrderDelivery.addHappyCustomer();
                customers.remove(currentCustomer);
                currentCustomer = findLargestOrder(k);
            }

            removeFromGrill();
            removeFromPans(fries);
        }
    }

    /**
     * Removes finished items from the grill.
     */
    private void removeFromGrill() {
        ArrayList<GrillMenuItem> grillItems = grill.getGrillItems();
        ArrayList<GrillMenuItem> itemsToRemove = new ArrayList<>();

        Iterator<GrillMenuItem> iterator = grillItems.iterator();
        while (iterator.hasNext()) {
            GrillMenuItem item = iterator.next();
            if (item.getCookingTimeLeft() == 0) {
                itemsToRemove.add(item); // Collect items to remove
                iterator.remove(); // Safely remove the item from the list
            } else {
                item.decreaseCookingTimeLeft();
            }
        }

        // Remove items from the list after the iteration is complete
        for (GrillMenuItem item : itemsToRemove)
            grill.removeItem(item);
    }

    /**
     * Removes finished fries from the pans.
     * 
     * @param fries The list of fries to remove from the pans.
     */
    private void removeFromPans(ArrayList<Fries> fries) {
        for (int i = 0; i < fries.size(); i++) {
            if (fries.get(i).getCookingTimeLeft() == 0) {
                pans.removeFromPans(fries.get(i).getPortions());
                fries.remove(i--);

            } else
                fries.get(i).decreaseCookingTimeLeft();
        }
    }

    /**
     * Finds the customer with the largest order at a given time.
     * 
     * @param time The current time.
     * @return The customer with the largest order at the given time, or null if no
     *         such customer is found.
     */

    private Customer findLargestOrder(int time) {

        if (customers.isEmpty())
            return null;

        ArrayList<Customer> availableCustomers = new ArrayList<>();

        for (Customer customer : this.customers)
            if (time >= customer.getOrder().getTorder())
                availableCustomers.add(customer);

        if (availableCustomers.isEmpty())
            return null;

        Customer largestOrder = availableCustomers.get(0);

        for (int i = 1; i < availableCustomers.size(); i++) {
            Customer nextCustomer = availableCustomers.get(i);
            if (nextCustomer.getOrder().getWeight(time) > largestOrder.getOrder().getWeight(time)) {
                largestOrder = nextCustomer;
            } else if (nextCustomer.getOrder().getWeight(time) == largestOrder.getOrder().getWeight(time)) {
                if (nextCustomer.getOrder().getTreq() < largestOrder.getOrder().getTreq())
                    largestOrder = nextCustomer;
            }
        }

        return largestOrder;
    }
}
