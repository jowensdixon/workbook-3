package com.pluralsight;

// SearchInventory.java
import java.io.*;
import java.util.*;

public class SearchInventory {
    private static ArrayList<Product> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadInventoryFromFile("src/main/resources/inventory.csv");
        boolean running = true;

        while (running) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1- List all products");
            System.out.println("2- Lookup a product by its id");
            System.out.println("3- Find all products within a price range");
            System.out.println("4- Add a new product");
            System.out.println("5- Quit the application");
            System.out.print("Enter command: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    listAllProducts();
                    break;
                case "2":
                    lookupProductById();
                    break;
                case "3":
                    findProductsByPriceRange();
                    break;
                case "4":
                    addNewProduct();
                    break;
                case "5":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static void loadInventoryFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    inventory.add(new Product(id, name, price));
                }
            }
            inventory.sort(Comparator.comparing(Product::getName));
        } catch (IOException e) {
            System.out.println("Could not load inventory file. Starting with an empty list.");
        }
    }

    private static void listAllProducts() {
        System.out.println("\nInventory:");
        for (Product p : inventory) {
            System.out.printf("ID: %d | Name: %s | Price: $%.2f\n", p.getId(), p.getName(), p.getPrice());
        }
    }

    private static void lookupProductById() {
        System.out.print("Enter product ID to search: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (Product p : inventory) {
            if (p.getId() == id) {
                System.out.printf("Found: ID: %d | Name: %s | Price: $%.2f\n", p.getId(), p.getName(), p.getPrice());
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void findProductsByPriceRange() {
        System.out.print("Enter minimum price: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter maximum price: ");
        double max = Double.parseDouble(scanner.nextLine());

        System.out.println("\nProducts in range:");
        for (Product p : inventory) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                System.out.printf("ID: %d | Name: %s | Price: $%.2f\n", p.getId(), p.getName(), p.getPrice());
            }
        }
    }

    private static void addNewProduct() {
        System.out.print("Enter product ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = Double.parseDouble(scanner.nextLine());

        inventory.add(new Product(id, name, price));
        inventory.sort(Comparator.comparing(Product::getName));
        System.out.println("Product added successfully.");
    }
}

// Product.java
class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
