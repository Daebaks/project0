package revature.com;

import java.util.InputMismatchException;
import java.util.Scanner;

import revature.com.models.Role;
import revature.com.models.User;
import revature.com.service.AccountService;
import revature.com.service.UserService;

public class BankingApp {

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		run();

	}

	public static void run() {

		boolean running = true;   //Keeping the main loop to run the program until user quits
		
		while(running) {
			
			
			try {
				
				System.out.println("Welcome to the Bank \n"
						+ "Please press 1 to register if you are a new customer \n"
						+ "Please press 2 to log-in if you are a current customer \n"
						+ "Please press 3 to exit the Bank");
				int entry = scan.nextInt();
				
				if(entry == 2) {
					
					//Logging-in for an existing user
					
				}else if (entry == 1) {
					
					//Registration for a new customer
					
					
				} else if(entry == 3) {
					
					running = false;   //To quit the program
					System.out.println("Thank you for using the Bank");
				} else {
					
					System.out.println("invalid input. Please try again");
				}
			} catch (InputMismatchException e) {
				
				System.out.println("invalid input. Please try again");
			} finally {
				
				scan.nextLine(); 
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
//		 registering template
//		AccountService as = new AccountService();
//		as.viewAllAccounts();
//		System.out.println("Enter username");
//		String username = scan.next();
//		
//		System.out.println("Enter password");
//		String password = scan.next();
//		
//		User u = new User(username, password, Role.Customer, null);
//		UserService us = new UserService();
//		us.register(u);

	}
}
