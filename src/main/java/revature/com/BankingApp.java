package revature.com;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.models.Account;
import revature.com.models.Customer;
import revature.com.utility.UtilityMethods;

public class BankingApp {

	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		
		boolean mainLoggedIn = true;
		while(mainLoggedIn) {
			
		// Checking user's role. Three roles: Customer, Employee, and Administrator. This will be implemented
		// depending on the type of credentials/IDs
		System.out.println("Welcome. Please enter your role! [customer, employee, administrator - or q to exit the bank!]");
		String user = scan.next();
		
		/*
		 * Based on the credentials provided, user will log-in under the appropriate role. 
		 * If no account exists, customers will have a chance to register to open new account(s)
		 * */
		
		if(user.equalsIgnoreCase("customer")) {
			
			//==========================================
			/*
			 * For current customers, users can log-in using their credentials, otherwise,
			 * new customers will have the chance to open new account(s)
			 * */
			//==========================================
			
			
			//if a customer is a current account(s) holder, customer will skip the registration part which starts underneath.
			//==========Start new customers registration===========
			System.out.println("==========Welcome to the Bank!!==========");
			System.out.println("How many new accounts would you like to apply for today?");
			int numOfAccounts = scan.nextInt();
			
			//Creating new accounts. New customers
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
			//==========End new customers registration============
			
			
			
			// For a successful customer log-in/ or after registration
			// Customer can do the following after logging-in 
			boolean customerLoggedIn = true;
			while(customerLoggedIn) {
				
				System.out.println("Dear: "+ cust.getFirstName() +" "+cust.getLastname()+"\n"
						+ "Press 1 to withdraw from your Account(s)"+"\n"
						+ "Press 2 to deposit to your Account(s)"+"\n"
						+ "Press 3 to transfer between your Accounts"+"\n"
						+ "Press q to Log-out!");
//				if(cust.getAcs().size()!=0) {
//					UtilityMethods.printAllAccounts(cust);           
//				}
				String key = scan.next();
				
				switch (key) {
				
				case "1":  			//Withdraw money
					
					UtilityMethods.printAllAccounts(cust);
					
					System.out.println("Please enter account number you want to withdraw from!");
					int acNumWithdrawal = scan.nextInt();
					
					boolean isValidInputWithdrawal = false;
					while (!isValidInputWithdrawal) {
						try {
							System.out.println("Please enter the amount to withdraw!");
							Double withdrawalMoneyAmount= scan.nextDouble();
							UtilityMethods.withdraw(cust,acNumWithdrawal, withdrawalMoneyAmount );
							System.out.println("You withdrew "+withdrawalMoneyAmount+" from your account!");
							UtilityMethods.printAccount(cust,acNumWithdrawal);
							
							isValidInputWithdrawal = true;
						} catch (InvalidAmountOfMoneyException e) {
							System.out.println(e.getMessage());
						}  catch (InsufficientBalanceException e) {
							System.out.println(e.getMessage());
						} catch (InputMismatchException e) {
							System.out.println("Input a valid amount of money!");
						}   finally {
							scan.nextLine(); 
						}
					}
					
					
					
					break;
				case "2":  			//Deposit money
					
					 UtilityMethods.printAllAccounts(cust);
					
					System.out.println("Please enter account number you want to deposit to!");
					int acNumDeposit = scan.nextInt();
					
					
					boolean isValidInputDeposit = false;
					while (!isValidInputDeposit) {
						try {
							System.out.println("Please enter the amount to deposit!");
							Double depositMoneyAmount= scan.nextDouble();
 							
							UtilityMethods.deposit(cust,acNumDeposit, depositMoneyAmount);
							System.out.println("You depositted "+depositMoneyAmount+" to your account!");
							UtilityMethods.printAccount(cust,acNumDeposit);
							
							isValidInputDeposit = true;
						} catch (InvalidAmountOfMoneyException e) {
							System.out.println(e.getMessage());
						} catch (InputMismatchException e) {
							System.out.println("Input a valid amount of money!");
						}  finally {
							scan.nextLine();
						}
					}
					
					break;
				case "3":
					// Transfer money between accounts
					break;
				case "q":
					System.out.println("======Thank you for being a valued customer!======");
					customerLoggedIn = false;
					break;
				default:
					System.out.println("invalid Input, try again!");
					break;
				}
				
			}
			 
			
			
		} else if(user.equalsIgnoreCase("employee")) {
			//
		}else if(user.equalsIgnoreCase("administrator")) {
			//
		} else if(user.equalsIgnoreCase("q")){
			System.out.println("======Thank you for using the banking app!======");
			mainLoggedIn = false;
		} else if(user.equalsIgnoreCase("r")){
			// Register
		} 
		else {
			System.out.println("======User doesn't exist. Try again or register by pressing -r- ======");
			scan.nextLine();
		}
 }
		
		scan.close();
	}
	
	
}
