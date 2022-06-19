package revature.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import revature.com.models.Account;
import revature.com.models.Customer;

public class BankingApp {

	
	private static Scanner scan = new Scanner(System.in);
	
	 
	
	
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		
		// Checking user's role. Three roles: Customer, Employee, and Administrator
		System.out.println("Welcome. Please enter your role! [customer, employee, administrator]");
		String user = scan.next();
		
		if(user.equalsIgnoreCase("customer")) {
			
			System.out.println("Welcome to the Bank!!");
			System.out.println("How many accounts would you like to apply for today?");
			
			int numOfAccounts = scan.nextInt();
			
			List<Account> accounts = new ArrayList<Account>();
			for(int i = 0; i<numOfAccounts; i++ ) {
				Account acc = new Account();
				accounts.add(acc);
			}
			
			System.out.println("Let's fill in your personal information!");
			System.out.println("What is your first name?");
			String first = scan.next();
			System.out.println("What is your last name?");
			String last = scan.next();
			System.out.println("What is your phone number?");
			String phoneN = scan.next();
			System.out.println("What is your eamil address?");
			String emailA = scan.next();
			System.out.println("Please choose a username");
			String userN = scan.next();
			System.out.println("Please choose a password");
			String pass = scan.next();
			
			Customer cust = new Customer(first, last, phoneN, emailA, userN, pass, accounts);
			
			// From here, these accounts will go under approval process.
			// Then, customer can do transactions
			boolean loggedIn = true;
			while(loggedIn) {
				
				System.out.println("Dear: "+ cust.getFirstName() +" "+cust.getLastname()+"\n"
						+ "Press 1 to withdraw from your Account(s)"+"\n"
						+ "Press 2 to deposit to your Account(s)"+"\n"
						+ "Press 3 to transfer between your Accounts"+"\n"
						+ "Press q to quit!");
				
				
				
				
				
			}
			 
			
			
		} else if(user.equalsIgnoreCase("employee")) {
			//
		}else if(user.equalsIgnoreCase("administrator")) {
			//
		} else {
			System.out.println("Invalid role!");
			System.exit(0);
		}
		
		
		
		
	}
	
}
