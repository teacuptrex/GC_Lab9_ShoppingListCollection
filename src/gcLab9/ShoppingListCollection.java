package gcLab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ShoppingListCollection {
	/*
	 * Task: Make a shopping list application which uses collections to store your
	 * items. 
	 * What will the application do? 
	 * 
	 * ● Display a list of at least 8 item
	 * names and prices. 
	 * 
	 * ● Ask the user to enter an item name 
	 * 
	 * 		○ If that item exists,
	 * display that item and price and add that item and its price to the user’s
	 * order. 
	 * 
	 * 		○ If that item doesn’t exist, display an error and re-prompt the user.
	 * (Display the menu again if you’d like.) 
	 * 
	 * ● Ask if they want to add another.
	 * Repeat if they do. (User can enter an item more than once; we’re not taking
	 * quantity at this point.) 
	 * 
	 * ● When they’re done adding items, display a list of
	 * all items ordered with prices in columns. 
	 * 
	 * ● Display the average price of
	 * items ordered. Build Specifications 
	 * 
	 * ● Use a Map to keep track of the menu of
	 * items. It should have a String key (for item name) and Double value (for item
	 * price). 
	 * 
	 * ● Use parallel ArrayLists (one of strings, one of doubles) to store
	 * the items ordered and their prices. 
	 * 
	 * ● Write 3 methods to find 1) the average cost of the items ordered and the indexes of the 2) highest and 3) lowest cost items.
	 */
	public static ArrayList<String> itemNames = new ArrayList<>();
	public static ArrayList<Double> itemPrices = new ArrayList<>();
	public static Map<String, Double> menuItems = new HashMap<>();
	public static ArrayList<Integer> itemQuantities = new ArrayList<>();
	public static ArrayList<String> menuIndex = new ArrayList<>();

	public static void main(String[] args) {
		// keep scanner elements in main so tests can be created for other methods
		Scanner scn = new Scanner(System.in);
		String itemName;
		String yesOrNo = "y";
		int itemQuantity = 0;
		int itemNumber;
		
		menuItems.put("apple", 0.99);
		menuItems.put("banana", 0.59);
		menuItems.put("cauliflower", 1.59);
		menuItems.put("dragonfruit", 2.19);
		menuItems.put("elderberry", 1.79);
		menuItems.put("fig", 2.09);
		menuItems.put("grapefruit", 1.99);
		menuItems.put("honeydew", 3.49);
		
		createNumberedMenu();
		
		while(validYes(yesOrNo)) {
			printMenu(menuItems);
			System.out.println("\r\nWhat item would you like to order? ");
			if(scn.hasNextInt()) {
				
				itemNumber = scn.nextInt() - 1;
				itemName = menuIndex.get(itemNumber);
				scn.nextLine();
				
			} else {
				
				itemName = scn.nextLine();	
				
			}
			
			try {
				if(menuItems.containsKey(itemName.toLowerCase())) {
					System.out.println("The item " + itemName + " ($" + menuItems.get(itemName) + ") will be added to the order\r\n");
					
					System.out.println("How many " + itemName + "s would you like? ");
					itemQuantity = scn.nextInt();
					
					addToQuantity(itemName,itemQuantity);
					addToOrder(itemName, menuItems.get(itemName));
					scn.nextLine();
					
					System.out.println("Would you like to order anything else? (y/n) ");
					yesOrNo = scn.nextLine();
					
				} else {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				System.out.println("Invalid Input, please try again.");
			}

			
			
		}
		
		System.out.printf("%-14s %-10s %1s\n","Item","Quantity","Price Per Each");
		
		
		Set<String> itemNamesSet = new HashSet<>(itemNames);

		for(String entry : itemNamesSet) {
			System.out.printf("%-14s %-10s $%1s\n",entry,itemQuantities.get(itemNames.indexOf(entry)),itemPrices.get(itemNames.indexOf(entry)));
		}
		
		System.out.println("\r\nAverage price per item in order: $" + averageCost(itemPrices));
		System.out.println("\r\nMost expensive item ordered: " + itemNames.get(highestCostIndex(itemPrices)));
		System.out.println("Least expensive item ordered: " + itemNames.get(lowestCostIndex(itemPrices)));
		scn.close();
	}
	
	public static void printMenu(Map<String, Double> menuItems) {
		System.out.println("Welcome to Guenther's Market!");
		System.out.printf("%-14s %1s %10s\n","Item","Price","Enter to Order:");
		System.out.println("=================================================");
		
		for(Map.Entry<String,Double> entry : menuItems.entrySet()) {
			System.out.printf("%-14s $%1s %10s\n",entry.getKey(),entry.getValue(),menuIndex.indexOf(entry.getKey())+1);
		}
	}
	
	public static void addToOrder(String itemName, double itemPrice) {
		
		itemNames.add(itemName);
		itemPrices.add(itemPrice);
				
	}
	
	public static void addToQuantity(String itemName, int itemQuantity) {
		if(itemNames.contains(itemName.toLowerCase())) {
	
			//itemQuantities.set(itemNames.indexOf(itemName.toLowerCase()), (itemQuantities.get(itemNames.indexOf(itemName.toLowerCase()))+itemQuantity));
			itemQuantities.add(itemQuantity);
			itemQuantities.set(itemNames.lastIndexOf(itemName.toLowerCase()), (itemQuantities.get(itemNames.lastIndexOf(itemName.toLowerCase()))+itemQuantity));
			
		} else {
			itemQuantities.add(itemQuantity);		
		}

				
	}
	
	public static boolean validYes(String y) {
		Set<String> validY = new HashSet<>();
		validY.add("yes");
		validY.add("y");
		validY.add("yeah");
		validY.add("yea");
		validY.add("ok");
		validY.add("okay");
		validY.add("1");
		
		return validY.contains(y.toLowerCase());
				
		}
	
	public static double averageCost(ArrayList<Double> itemPrices) {
		double sum = 0.0;
		
		for(int i = 0; i < itemPrices.size(); i++) {
			sum += itemPrices.get(i);
		}
		
		return (sum/itemPrices.size());
	}
	
	public static int highestCostIndex(ArrayList<Double> itemPrices) {
		int highest = 0;
		double price = itemPrices.get(0);
		
		for(int i = 0; i < itemPrices.size(); i++) {
			if(price < itemPrices.get(i)) {
				price = itemPrices.get(i);
				highest = i;
			} 
		}
		
		return highest;
	}
	
	public static int lowestCostIndex(ArrayList<Double> itemPrices) {
		int lowest = 0;
		double price = itemPrices.get(0);
		
		for(int i = 0; i < itemPrices.size(); i++) {
			if(price > itemPrices.get(i)) {
				price = itemPrices.get(i);
				lowest = i;
			} 
		}
		
		return lowest;
	}
	
	public static boolean validNo(String n) {
		Set<String> validN = new HashSet<>();
		validN.add("no");
		validN.add("n");
		validN.add("nope");
		validN.add("nah");
		validN.add("0");
		
		return validN.contains(n.toLowerCase());
				
		}
	
	public static double totalCost(ArrayList<Double> itemPrices) {
		double sum = 0.0;
		
		for(int i = 0; i < itemPrices.size(); i++) {
			sum += (itemPrices.get(i) * itemQuantities.get(i));
		}
		
		return sum;
	}
	
	public static void createNumberedMenu() {
		for (Map.Entry<String,Double> entry : menuItems.entrySet()) {
			menuIndex.add(entry.getKey());
		}
	}
}



