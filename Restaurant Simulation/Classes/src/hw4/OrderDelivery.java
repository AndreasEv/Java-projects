package hw4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * OrderDelivery class manages the delivery of orders and handles various kitchen operations.
 */
public class OrderDelivery {

    private static ArrayList<Order> deliveredOrders = new ArrayList<>();
    private static ArrayList<Integer> deviations = new ArrayList<>();
    private static int happyCustomers;

    /**
     * Adds the deviation of an order to the list of deviations.
     * 
     * @param num The deviation of the order.
     */
    public static void addDeviationOfOrder(int num) {
        deviations.add(Math.abs(num));
    }

    /**
     * Increases the count of happy customers.
     */
    public static void addHappyCustomer() {
        happyCustomers++;
    }

    /**
     * Adds an order to the list of delivered orders.
     * 
     * @param order The order to be added.
     */
    public static void addOrder(Order order){
        deliveredOrders.add(order);
    }

    /**
     * Main method to handle the delivery of orders and kitchen operations.
     * 
     * @param args Command-line arguments: grillLength coalTime numOfPans panCapacity souvlakiSkewerLength sheftalaSkewerLength pittaLength algorithm
     */
    public static void main(String[] args) {

        if (args.length != 8) {
            System.out.println("Invalid number of arguments provided. Please provide 8 arguments.");
            return;
        }

        int grillLength = 0;
        int coalTime = 0;
        int numOfPans = 0;
        int panCapacity = 0;
        int souvlakiSkewerLength = 0;
        int sheftalaSkewerLength = 0;
        int pittaLength = 0;
        int algorithm = 0;

        try {
            grillLength = Integer.parseInt(args[0]);
            coalTime = Integer.parseInt(args[1]);
            numOfPans = Integer.parseInt(args[2]);
            panCapacity = Integer.parseInt(args[3]);
            souvlakiSkewerLength = Integer.parseInt(args[4]);
            sheftalaSkewerLength = Integer.parseInt(args[5]);
            pittaLength = Integer.parseInt(args[6]);
            algorithm = Integer.parseInt(args[7]);
        } catch (NumberFormatException e) {
            System.out.println("Arguments must be integer numbers");
            System.exit(0);
        }

        Kitchen kitchen = new Kitchen(grillLength, coalTime, numOfPans, panCapacity, pittaLength, souvlakiSkewerLength,
                sheftalaSkewerLength);

        try {
            // Open the file
            BufferedReader reader = new BufferedReader(new FileReader("orders200.txt"));

            // Read lines from the file
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip processing the first line
                }

                // Split the line into an array using regular expression as delimiter
                String[] parts = line.split("\\s+"); // "\\s+" matches one or more whitespace characters
                int[] orderParts = new int[parts.length];

                Order.setPittaLength(pittaLength);
                Order.setSouvlakiSkewerLength(souvlakiSkewerLength);
                Order.setSheftalaSkewerLength(sheftalaSkewerLength);

                for (int k = 0; k < parts.length; k++)
                    orderParts[k] = Integer.parseInt(parts[k]);

                Order order = new Order(
                        orderParts[0], // num
                        orderParts[1], // torder
                        orderParts[2], // treq
                        orderParts[3], // npp
                        orderParts[4], // npc
                        orderParts[5], // nps
                        orderParts[6], // npm
                        orderParts[7] // npf
                );

                kitchen.addToKitchen(new Customer(order));
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            // Handle any potential IO exceptions
            e.printStackTrace();
        }

        kitchen.handleOrders(algorithm);
        double averageDeviation = 0;
        for (int k = 0; k < deviations.size(); ++k) {
            averageDeviation += deviations.get(k);
        }

        averageDeviation /= deviations.size();

        averageDeviation = Double.parseDouble(String.format("%.2f", averageDeviation));

        try {
            BufferedReader reader=new BufferedReader(new FileReader("orders200.txt"));
            BufferedWriter writer=new BufferedWriter(new FileWriter("deliveriesAL3_200.txt"));
            writer.write(reader.readLine() + "   " + averageDeviation + "   " + happyCustomers+"\n");
            for (Order order : deliveredOrders){
                int numOfPittas=order.getNpc()+order.getNpm()+order.getNpp()+order.getNps();
                writer.write(order.getNum() + "   " + order.getTorder() + "    " + (order.getTimeDelivered() - order.getTreq())  +"    " + numOfPittas +"    "+ order.getNpf()+"\n") ;
            }

            reader.close();
            writer.close();
        }catch(Exception e){
           
        }

    }
}