package com.pluralsight;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class payrollCalculator {



    public static void main(String[] args) {
        String fileName = "employees.csv"; // Make sure this file is in the project root or provide full path

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            System.out.printf("%-10s %-25s %-10s%n", "Emp ID", "Name", "Gross Pay");
            System.out.println("------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");

                if (tokens.length != 4) {
                    System.out.println("Invalid line format: " + line);
                    continue;
                }

                int employeeId = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee employee = new Employee(employeeId, name, hoursWorked, payRate);

                System.out.printf("%-10d %-25s $%-10.2f%n",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getGrossPay());
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());

        }
    }
}
