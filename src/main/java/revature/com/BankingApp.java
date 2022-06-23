package revature.com;

import java.util.InputMismatchException;
import java.util.Scanner;

import revature.com.exceptions.UsernameAlreadyExistsException;
import revature.com.models.Role;
import revature.com.models.User;
import revature.com.service.AccountService;
import revature.com.service.UserService;
import revature.com.utility.UtilityMethods;

public class BankingApp {

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		 run();
//		AccountService as = new AccountService();
//		as.viewAllAccounts();

	}

	public static void run() {

		System.out.println("Welcome to the Bank");
		
		boolean mainRunning = true;   //Keeping the main loop to run the program until user quits
		
		while(mainRunning) {
			
			
			try {
				
				System.out.println("Please press 1 to register if you are a new customer \n"
						+ "Please press 2 to log-in if you are a current customer \n"
						+ "Please press 3 to exit the Bank");
				int entry = scan.nextInt();
				
				if(entry == 2) {
					
					//Logging-in for an existing user
					boolean loggedInRunning = true;
					while(loggedInRunning) {
						
						
						
					}
					
					
				}else if (entry == 1) {
					
					//Registration for a new customer
					boolean registrationRunning = true;
					while(registrationRunning) {
						
						try {
							System.out.println("Enter username");
							String username = scan.next();
							UtilityMethods.validateUsername(username);
							
							System.out.println("Enter password");
							String password = scan.next();
							
							User u = new User(username, password, Role.Customer, null);
							UserService us = new UserService();
							us.register(u);
						} catch (UsernameAlreadyExistsException e) {
							System.out.println(e.getMessage()); 
						} finally {
							scan.nextLine();
						}
						
						
					}
					
					
				} else if(entry == 3) {
					
					mainRunning = false;   //To quit the program
					System.out.println("\nThank you for using the Bank");
				} else {
					
					System.out.println("invalid input. Please try again \n");
				}
			} catch (InputMismatchException e) {
				
				System.out.println("invalid input. Please try again \n");
			} finally {
				
				scan.nextLine(); 
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
//		AccountService as = new AccountService();
//		as.viewAllAccounts();


	}
}
