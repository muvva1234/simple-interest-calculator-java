package com.personalprojects.simpleinterest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class SimpleInterest {
	// Input parameters
	public double principalAmount;
	public double rate;
	public double time;
	public double simpleInterestAmount;
	public double timeInYears;
	public double timeInMonths;
	public double timInDays;
	public double totalAmount;
	public static boolean Flag = false;

	// Constructor initializing the class with input parameters
	public SimpleInterest(double enterPrincipleAmount, double enterRate, double enterTime) {
		this.principalAmount = enterPrincipleAmount;
		this.rate = enterRate;
		this.time = enterTime;
		coreImplementation();
	}

	// Method to format a double value to two decimal places
	public double formatValue(double number) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(number));
	}

	// Common logic for interest calculation
	public double commonLogic() {
		double value = principalAmount / 100;
		return value;
	}

	// Method to calculate monthly interest
	public double getMonthlyIntrest() {
		double value = rate * commonLogic();
		return value / 12;
	}

	// Method to calculate interest in terms of years
	public double inTermsOfyears(double years) {
		return commonLogic();

	}

	// Method to calculate interest in terms of days
	public double inTermsOfDays() {
		double value = getMonthlyIntrest();
		return value / 31;
	}

	// Method to establish database connectivity and insert values
	public void databaseConnectivity() {
		Connection con = null;
		PreparedStatement ps = null;
		String Username = "root";
		String Password = "nanswe@123S";
		String URL = "jdbc:mysql://localhost:3306/interestAmountDetails";
		String sql = "insert into userData(principalAmount, rate, timeInYears, timeInMonths, timeIndays, simpleInterestAmount,totalAmount)values (?,?,?,?,?,?,?) ";
		try {
			// Load the JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// Establish database connection
			con = DriverManager.getConnection(URL, Username, Password);
			ps = con.prepareStatement(sql);
			// Set values for the prepared statement
			ps.setDouble(1, principalAmount);
			ps.setDouble(2, rate);
			ps.setDouble(3, timeInYears);
			ps.setDouble(4, timeInMonths);
			ps.setDouble(5, timInDays);
			ps.setDouble(6, simpleInterestAmount);
			ps.setDouble(7, totalAmount);
			// Execute the update
			int rows = ps.executeUpdate();
			System.out.println(rows + "rows effected");
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Core implementation of the interest calculator
	public void coreImplementation() {
		String times = null;
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("if you Entered the time in Years,Press 'Y' / in Months press 'M'/in Days Press 'D':");
			times = sc.nextLine();
			if (times.equalsIgnoreCase("D")) {
				timInDays = time;
				flag = false;
				simpleInterestAmount = formatValue(inTermsOfDays());
				totalAmount = principalAmount + simpleInterestAmount;

			} else if (times.equalsIgnoreCase("y")) {
				timeInYears = time;
				flag = false;
				simpleInterestAmount = (rate * inTermsOfyears(time));
				totalAmount = principalAmount + simpleInterestAmount;

			} else if (times.equalsIgnoreCase("M")) {
				timeInMonths = time;
				flag = false;
				simpleInterestAmount = formatValue((time * getMonthlyIntrest()));
				totalAmount = principalAmount + simpleInterestAmount;

			} else if (!times.equalsIgnoreCase("D") || !times.equalsIgnoreCase("y") || !times.equalsIgnoreCase("M")) {
				throw new Exception("Incorrect Entry");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Incorrect entry,Enter the correct one.");
		}

		if (flag) {
			Flag = true;
			System.out.print("Do You want to try again,Your answer is 'YES' press 'Y' otherwise 'N' :");
			String answer = sc.nextLine();
			if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("Y")) {
				coreImplementation();
			} else if (answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("N")) {
				System.out.println("thank you");
			}
		}

	}

	// Main method
	public static void main(String[] args) {
		// Creating an instance of SimpleInterest
		SimpleInterest SI = new SimpleInterest(25000, 3, 1);
		// Check if the calculation was successful before performing database connectivity
		if (Flag == false)
			SI.databaseConnectivity();
		else if (Flag)
			System.out.println("Have a nice day");
	}
}
