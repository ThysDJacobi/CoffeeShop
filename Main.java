/**
 * Café Application
 * The purpose of the application is to create a coffee order for a cafe. It begins by reading the inventory that
 * the store currently has, then the user creates a coffee if the store has the inventory for it. It then prints the
 * order to a LogFile, and this file says the times it was created and what the order contained and how much it cost.
 * @author Thys Jacobi
 * 6/30/2022
 * CS160L-02
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    /**
     * The main method runs the application and asks the user what they want the program to do.
     * Depending on their input, it uses a switch case to run different methods.
     */
    public static void main(String[] args) {
        ArrayList<String> Item = new ArrayList<>();
        ArrayList<Double> Price = new ArrayList<>();
        ArrayList<String> Temp2 = new ArrayList<>();
        String line = "yes" ;
        Scanner userOrders = new Scanner(System.in);
        Stack<String > stack = new Stack<String>();
        Scanner CafeApplication = new Scanner(System.in);
        System.out.println("Cafe Application Running...");
        int input = 0;
        int[] inventory = new int[7];
        while(input != -1){
            System.out.println("Press 1 : Read Inventory");
            System.out.println("Press 2 : Create Coffee Order");
            System.out.println("Press 3 : Update Inventory");
            System.out.println("Press 4 : Update log file");
            System.out.println("Press 5 : Exit Application");
            switch (CafeApplication.nextLine()){
                case "1":
                    inventory = inventoryReader();
                    break;
                case "2":
                    if(inventory[0] == 0){
                        System.out.println("Out of Coffee. Visit us later.");
                    }
                    else{
                        do
                        {
                            Temp2 = CreateOrder();
                            Item.add(Temp2.get(0));
                            Price.add(Double.valueOf(Temp2.get(1)));
                            System.out.println("Do you want to add another coffee to this order? -yes or no");
                        }while (!(line = userOrders.nextLine()).equals("no"));

                        stack.push(PrintOrder(Item, Price));
                    }
                    break;
                case "3":
                    inventory = inventoryReader();
                    inventoryWriter(inventory);
                    break;
                case "4":
                    logWriter(stack);
                    break;
                case "5":
                    inventory = inventoryReader();
                    inventoryWriter(inventory);
                    stack.push(PrintOrder(Item, Price));
                    logWriter(stack);
                    input = -1;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again");
            }
        }
        System.out.println("Coffee order created. Select toppings for the first coffee:");
        try{
            FileWriter myWriter = new FileWriter("Order.csv");
            while(!stack.empty()) {
                myWriter.append(stack.pop());
                myWriter.close();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    /**
     * The logWriter method takes the stack as the input and the creates the LogFile document
     * which will contain the order with the time, contents of the order, and the price
     * @param stack Stack containing the contents of the order, as well as the price
     */
    public static void logWriter(Stack<String> stack) {
        while(!stack.empty()) {
            try {
                FileWriter myWriter = new FileWriter("LogFile.txt", true);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                myWriter.write("\n\nWriting orders from stack " + formatter.format(date));
                myWriter.write(stack.pop() + "\n");
                myWriter.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * The inventoryWriter method takes in the array of values, and the array is the inventory of each item.
     * It then overwrites the inventory document and rewrites the new values of the inventory
     * @param inv array of integers, that contains the number of each item.
     */
    public static void inventoryWriter(int[] inv) {
        int i = 0;
        try {
            FileWriter myWriter = new FileWriter("Inventory.txt", false);
            myWriter.write("Black Coffee = " + inv[i] + "\n");
            myWriter.write("Mocha = " + inv[i+1] + "\n");
            myWriter.write("Milk = " + inv[i+2] + "\n");
            myWriter.write("HotWater = " + inv[i+3] + "\n");
            myWriter.write("Espresso = " + inv[i+4] + "\n");
            myWriter.write("Sugar = " + inv[i+5] + "\n");
            myWriter.write("WhippedCream = " + inv[i+6] + "\n");
            System.out.println("Successfully updated the inventory");
            myWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * The inventoryReader method reads the inventory text file. It prints the contents of the file, and takes
     * the values of each item and creates an array of these values.
     * @return inv[]
     */
    public static int[] inventoryReader() {
        int[] inv = new int[7];
        int item;
        int i = 0;
        try{
            File myWriter = new File("Inventory.txt");
            Scanner myReader = new Scanner(myWriter);
            while(myReader.hasNextLine()){ //printing inventory
                String data = myReader.nextLine();
                System.out.println(data);
                // get quantities from file
                item = Integer.valueOf(data.substring(data.indexOf("=") + 2));
                inv[i] = item;
                i++;
            }
            myReader.close();

        }catch (Exception e){
            e.getStackTrace();
        }
        return inv;
    }

    /**
     * The printOrder method takes the ArrayList of the Items and the Prices. With these values
     * the method prints the items and prints out the price of each item, and calculates the total cost.
     * @param Item String of orders
     * @param Price Double values of the prices of the coffee
     * @return String
     */
    public static String PrintOrder(ArrayList<String> Item, ArrayList<Double> Price){
        double cost = 0;
        StringBuilder stb = new StringBuilder();
        stb.append("RECEIPT" + "\n");
        for(int i = 0; i < Item.size(); i++){
            stb.append("Item " + (i+1) + ": " + Item.get(i) + " | Cost: ");
            String price = String.format("%.2f", Price.get(i));
            stb.append(price + "\n");
            cost += Price.get(i);
        }
        stb.append("TOTAL COST OF ORDER: ");
        String costS = String.format("%.2f", cost);
        stb.append(costS);
        return stb.toString();

    }
    /**
     * The createOrder method firsts asks the user if the coffee is a black coffee or a mocha. Once the user answers,
     * it asks for different toppings, and if a topping is selected it removes 1 from the inventory. Once the user
     * is done creating the coffee the new values are sent to the inventoryWriter to update the inventory, and the order
     * is pushed to a stack to be read by the LogFile
     * @return order
     */
    public static ArrayList<String> CreateOrder() {
        ArrayList<String> order = new ArrayList<String>();
        Scanner scnr = new Scanner(System.in);
        int choice = 0;
        int coffeeType;
        int[] inventory;
        inventory = inventoryReader();
        System.out.println("Select Coffee type");
        System.out.println("1. Black Coffee");
        System.out.println("2. Mocha");
        coffeeType = scnr.nextInt();
        if(coffeeType == 1){
            Coffee coffee1 = new BasicCoffee();
            while (choice != 6){
                System.out.println("Select additions to the coffee: ");
                System.out.println("1. Milk");
                System.out.println("2. Sugar");
                System.out.println("3. Espresso");
                System.out.println("4. Hot Water");
                System.out.println("5. Whipped Cream");
                System.out.println("6. Finished");
                choice = scnr.nextInt();
                switch (choice) {
                    case 1:
                        if(inventory[2] != 0) {
                            coffee1 = new Milk(coffee1);
                            inventory[2] = inventory[2] - 1;
                        }
                        else {
                            System.out.println("Out of milk. Try a different topping");
                        }
                        break;
                    case 2:
                        if(inventory[5] != 0) {
                            coffee1 = new Sugar(coffee1);
                            inventory[5] = inventory[5] - 1;
                        }
                        else {
                            System.out.println("Out of sugar. Try a different topping");
                        }
                        break;
                    case 3:
                        if(inventory[4] != 0) {
                            coffee1 = new Espresso(coffee1);
                            inventory[4] = inventory[4] - 1;
                        }
                        else {
                            System.out.println("Out of espresso. Try a different topping");
                        }
                        break;
                    case 4:
                        if(inventory[3] != 0) {
                            coffee1 = new HotWater(coffee1);
                            inventory[3] = inventory[3] - 1;
                        }
                        else {
                            System.out.println("Out of hot water. Try a different topping");
                        }
                        break;
                    case 5:
                        if(inventory[6] != 0) {
                            coffee1 = new WhippedCream(coffee1);
                            inventory[6] = inventory[6] - 1;
                        }
                        else {
                            System.out.println("Out of whipped cream. Try a different topping");
                        }
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            }
            inventory[0] = inventory[0] - 1;
            inventoryWriter(inventory);
            order.add(coffee1.printCoffee());
            order.add(coffee1.Cost().toString());
        }
        else if(coffeeType == 2){
            Coffee coffee2 = new Mocha();
            while (choice != 6){
                System.out.println("Select additions to the coffee: ");
                System.out.println("1. Milk");
                System.out.println("2. Sugar");
                System.out.println("3. Espresso");
                System.out.println("4. Hot Water");
                System.out.println("5. Whipped Cream");
                System.out.println("6. Finished");
                choice = scnr.nextInt();
                switch (choice) {
                    case 1:
                        if(inventory[2] != 0) {
                            coffee2 = new Milk(coffee2);
                            inventory[2] = inventory[2] - 1;
                        }
                        else {
                            System.out.println("Out of milk. Try a different topping");
                        }
                        break;
                    case 2:
                        if(inventory[5] != 0) {
                            coffee2 = new Sugar(coffee2);
                            inventory[5] = inventory[5] - 1;
                        }
                        else {
                            System.out.println("Out of sugar. Try a different topping");
                        }
                        break;
                    case 3:
                        if(inventory[4] != 0) {
                            coffee2 = new Espresso(coffee2);
                            inventory[4] = inventory[4] - 1;
                        }
                        else {
                            System.out.println("Out of espresso. Try a different topping");
                        }
                        break;
                    case 4:
                        if(inventory[3] != 0) {
                            coffee2 = new HotWater(coffee2);
                            inventory[3] = inventory[3] - 1;
                        }
                        else {
                            System.out.println("Out of hot water. Try a different topping");
                        }
                        break;
                    case 5:
                        if(inventory[6] != 0) {
                            coffee2 = new WhippedCream(coffee2);
                            inventory[6] = inventory[6] - 1;
                        }
                        else {
                            System.out.println("Out of whipped cream. Try a different topping");
                        }
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            }
            inventory[1] = inventory[1] - 1;
            inventoryWriter(inventory);
            order.add(coffee2.printCoffee());
            order.add(coffee2.Cost().toString());
        }

        return order;
    }

}