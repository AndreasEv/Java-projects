package hw4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * OrderGenerator class generates orders and writes them to a file.
 */
public class OrderGenerator {

    // private static final Random random = new Random();

    /**
     * Main method to generate orders and write them to a file.
     * 
     * @param args Command-line arguments: <num_orders>
     */
    public static void main(String[] args) {
        // Check if the correct number of arguments is provided
        if (args.length != 1) {
            System.err.println("Usage: java OrderGenerator <num_orders>");
            return;
        }

        int numOrders = 0;

        // Parse the number of orders from command line arguments
        try {
            numOrders = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("<numOrders> shall be an integer number");
            System.exit(0);
        }

        // Generate orders
        Order[] orders = new Order[numOrders];
        try {
            orders = generateOrders(numOrders);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort orders based on delivery time
        sortOrders(orders);

        // Write orders to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt"))) {
            writer.write(String.valueOf(orders.length));
            writer.newLine();
            for (Order order : orders) {
                writer.write(order.toString());
                writer.newLine();
            }
            System.out.println("Orders written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing orders to file: " + e.getMessage());
        }
    }

    /**
     * Generates orders based on the specified number.
     * 
     * @param numberOfOrders The number of orders to generate.
     * @return An array of generated orders.
     * @throws IOException If an I/O error occurs.
     */
    private static Order[] generateOrders(int numberOfOrders) throws IOException {
        Random randomTime = new Random();

        // Generate number of pittes per order
        int[] numOfPittesPerOrder = generatePittes(numberOfOrders);

        // Calculate total number of pittes
        int totalNumberOfPittes = calculateTotalPittes(numOfPittesPerOrder);

        // Generate number of patates per pitta
        int[] patatesPerPitta = generatePotatoes(totalNumberOfPittes);

        Order[] orders = new Order[numberOfOrders]; // create array to store orders
        int patatesIndex = 0;
        for (int i = 0; i < numberOfOrders; i++) { // iterate through the Orders array
            double gaussianRandomTime;
            int tOrder;
            do { // generate value according to gaussian distrubution
                gaussianRandomTime = 180 + (60 * randomTime.nextGaussian());
                tOrder = (int) Math.round(gaussianRandomTime);
            } while (tOrder < 0 || tOrder > 300); // ensure that the generated time is between 18:00 and 23:00

            int numOfPittes = numOfPittesPerOrder[i]; // get number of pittes for order i
            int tReq = 0;
            if (numOfPittes <= 10)
                tReq = tOrder + 30 + randomTime.nextInt(360 - tOrder - 30); // 30 mins after tOrder, up to 24:00
            else
                tReq = tOrder + 60 + randomTime.nextInt(360 - tOrder - 60); // 60 mins after tOrder, up to 24:00
            // Generate order details
            Random randomDetails = new Random();
            int npp = (int) (randomDetails.nextInt(numOfPittes + 1)); // xoirino in range [0, numOfPittes]
            int npc = randomDetails.nextInt(numOfPittes - npp + 1); // kotopoulo in range [0, numOfPittes - npp]
            int nps = randomDetails.nextInt(numOfPittes - npp - npc + 1); // seftalies in range [0, numOfPittes - npp -
                                                                          // npc]
            int npm = numOfPittes - npp - npc - nps;
            int npf = 0;
            for (int j = 0; j < numOfPittes; j++, patatesIndex++)
                npf += patatesPerPitta[patatesIndex];

            orders[i] = new Order(0, tOrder, tReq, npp, npc, nps, npm, npf);
        }

        sortOrders(orders);

        return orders;
    }

    /**
     * Generates the number of potatoes for each pitta based on a predefined distribution.
     * 
     * @param totalNumberOfPittes The total number of pittes.
     * @return An array containing the number of potatoes for each pitta.
     */
    private static int[] generatePotatoes(int totalNumberOfPittes) {
        Random random = new Random();
        int[] patatesPerPitta = new int[totalNumberOfPittes];

        int oneServing = (int) (0.60 * totalNumberOfPittes); // 60% has one portion per pitta
        int noServings = (int) (0.35 * totalNumberOfPittes); // 35% no potatoes

        Arrays.fill(patatesPerPitta, 0, noServings, 0); // Fill no servings
        Arrays.fill(patatesPerPitta, noServings, noServings + oneServing, 1); // Fill one serving
        Arrays.fill(patatesPerPitta, noServings + oneServing, totalNumberOfPittes, 2); // Fill two servings

        // Shuffle array to make it more realistic
        for (int i = patatesPerPitta.length - 1; i > 0; i--) {
            int index = i == 0 ? 0 : random.nextInt(i + 1);
            // swap values
            int a = patatesPerPitta[index];
            patatesPerPitta[index] = patatesPerPitta[i];
            patatesPerPitta[i] = a;
        }

        return patatesPerPitta;
    }

    /**
     * Sorts the orders array based on delivery time.
     * 
     * @param orders The array of orders to be sorted.
     */
    private static void sortOrders(Order[] orders) {
        int n = orders.length;
        for (int i = 1; i < n; ++i) {
            Order key = orders[i];
            int j = i - 1;

            // Move elements of orders[0..i-1], that are greater than key, to one position
            // ahead
            // of their current position
            while (j >= 0 && (orders[j].getTorder() > key.getTorder() ||
                    (orders[j].getTorder() == key.getTorder() && orders[j].getTreq() > key.getTreq()))) {
                orders[j + 1] = orders[j];
                orders[j + 1].setNum(j + 2); // Set 'num' field starting from 2
                j = j - 1;
            }
            orders[j + 1] = key;
            orders[j + 1].setNum(j + 2); // Set 'num' field starting from 2
        }
        // Set 'num' field for the first order
        orders[0].setNum(1);
    }

    /**
     * Generates the number of pittes for each order based on a predefined distribution.
     * 
     * @param numberOfOrders The number of orders for which to generate pittes.
     * @return An array containing the number of pittes for each order.
     */
    private static int[] generatePittes(int numberOfOrders) {
        Random random = new Random();
        int[] numOfPittes = new int[numberOfOrders];

        int[] limits = { 20, 55, 65, 85, 100 }; // Cumulative percentages for 1, 2, 3, 4, and 5-20 pittes
        int[] pittaTypes = { 1, 2, 3, 4, 5 }; // Corresponding pitta types

        for (int i = 0; i < numberOfOrders; i++) { // for every order
            int rand = random.nextInt(100); // generate random number
            for (int j = 0; j < limits.length; j++) {
                if (rand < limits[j]) { // find limit
                    // if j == 4 we need to generate random number between 5 and 20
                    numOfPittes[i] = pittaTypes[j] + (j == 4 ? random.nextInt(16) : 0); // For 5-20 pittes
                    break;
                }
            }
        }

        return numOfPittes;
    }

    /**
     * Calculates the total number of pittes based on the number of pittes per order.
     * 
     * @param numOfPittesPerOrder An array containing the number of pittes for each order.
     * @return The total number of pittes.
     */
    private static int calculateTotalPittes(int[] numOfPittesPerOrder) {
        int total = 0;
        for (int pittesPerOrder : numOfPittesPerOrder)
            total += pittesPerOrder;

        return total;
    }
}