package revature.com;

import java.util.InputMismatchException;
import java.util.Scanner;

import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.exceptions.NoAccountsExistException;
import revature.com.exceptions.UsernameAlreadyExistsException;
import revature.com.exceptions.UsernameNotFoundException;
import revature.com.exceptions.WrongPasswordException;
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

		System.out.println("Welcome to the Bank \n");

		UserService us = new UserService();
		AccountService as = new AccountService();

		
		
		boolean mainRunning = true; // Keeping the main loop to run the program until user quits

		while (mainRunning) {

			try {
				/*
				 * Main menu
				 */
				System.out.println("==Main Menu== \n\n"
						+ "Please press 1 to register if you are a new customer \n"
						+ "Please press 2 to log-in if you are a current customer \n"
						+ "Please press 3 to exit the Bank");
				int mainEntry = scan.nextInt();

				/*
				 * Logging-in menu
				 */
				if (mainEntry == 2) {

					// Logging-in for an existing user
					boolean loggedInRunning = true;
					while (loggedInRunning) {
						System.out.println("==Logging-in Menu== \n\n"
								+ "Please press 1 to enter your credentials \n"
								+ "Please press 2 to go back to the main menu \n");
								
						
						try {
							
							int loggedEntry = scan.nextInt();
							
							if(loggedEntry==1) {
								//logging-in
								try {
									System.out.println("Enter username");
									String username = scan.next();

									System.out.println("Enter password");
									String password = scan.next();


									User loggedInUser = us.login(username, password);

									Role role = loggedInUser.getRole();
									// Handle roles here and then create each role's menu/portal
									switch (role) {
									/*
									 * Customer menu
									 */
									case Customer:
										boolean cutomerLoggedIn=true;
										while(cutomerLoggedIn) {
											System.out.println("==Customer Menu== \n\n"
													+ "Welcome: "+loggedInUser.getUsername()
													+ "\n\nPlease press 1 to view your account(s) \n"
													+ "Please press 2 to withdraw from your account \n"
													+ "Please press 3 to deposit to your account \n"
													+ "Please press 4 transfer between your accounts \n"
													+ "Please press 5 to go back to the previous menu \n");
											
											try {
												
												int customerEntry = scan.nextInt();
												if(customerEntry==1) {
													//Print account owned by the customer
													System.out.println("======Your Account(s)======");
													try {
														as.viewOwnerAccListById(loggedInUser.getId());
													} catch (NoAccountsExistException e) {
														System.out.println(e.getMessage());
													}
													System.out.println("===========================\n");
													
												} else if(customerEntry==2) {
													//Withdraw from account
													try {
														
														System.out.println("Please select account id to withdraw from");
														int withdrawAccId = scan.nextInt();
														System.out.println("Please specify the amount of money to withdraw");
														double withdrawAmount = scan.nextDouble();
														
														
														double newBalance = as.withdraw(withdrawAmount, withdrawAccId, loggedInUser.getId() );
														System.out.println("Withdraw was successful. New balance is: "+newBalance+"\n");
														
													} catch (NoAccountsExistException e) {
														System.out.println(e.getMessage());
													}catch (InvalidAmountOfMoneyException e) {
														System.out.println(e.getMessage());
													}catch (InsufficientBalanceException e) {
														System.out.println(e.getMessage());
													}catch (InputMismatchException e) {
														System.out.println("Invalid input. Please try again \n");
														scan.nextLine();
													}
													
													
												} else if  (customerEntry==3) {
													//Deposit to an account
													
												}else if  (customerEntry==4) {
													//Transfer between accounts
													
												}else if  (customerEntry==5) {
													System.out.println("Thank you. See you again\n");
													cutomerLoggedIn = false;
												} else {
													System.out.println("Invalid input. Please try again\n");
												}
												
												
											} catch (InputMismatchException e) {
												System.out.println("Invalid input. Please try again\n");
												scan.nextLine();
											} 
											
											
											
											
											
											
										}
										
										
										break;
									case Employee:

										break;
									case Admin:

										break;
									default:
										break;
									}
									

								} catch (UsernameNotFoundException e) {
									System.out.println(e.getMessage());
									
								} catch (WrongPasswordException e) {
									System.out.println(e.getMessage());
								}

								
								
								
							}else if(loggedEntry==2) {
								loggedInRunning = false;
							}  else {
								System.out.println("Invalid input. Please try again\n");
							}
							
						} catch (InputMismatchException e) {
							System.out.println("Invalid input. Please try again\n");
							scan.nextLine();
						} 
					 
						
						
						
						
						
						
						
					}

				}

				/*
				 * New user registration menu
				 */
				else if (mainEntry == 1) {

					// Registration for a new customer
					boolean registrationRunning = true;
					while (registrationRunning) {

						try {
							System.out.println("Enter username");
							String username = scan.next();

							System.out.println("Enter password");
							String password = scan.next();

							User u = new User(username, password, Role.Customer, null);
							us.register(u);
							System.out.println("Welcome new customer. Use your username: " + username + " to log-in\n");
							registrationRunning = false; // New user registered
						} catch (UsernameAlreadyExistsException e) {
							System.out.println(e.getMessage());
						}

					}

				} else if (mainEntry == 3) {

					mainRunning = false; // To quit the program
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
