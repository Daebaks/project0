package revature.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
			
		// Checking user's role. Three roles: Customer, Employee, and Administrator
		System.out.println("Welcome. Please enter your role! [customer, employee, administrator - or q to exit the bank!]");
		String user = scan.next();
		
		if(user.equalsIgnoreCase("customer")) {
			
			System.out.println("==========Welcome to the Bank!!==========");
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
			// Then, customer can do the following after logging in 
			boolean customerLoggedIn = true;
			while(customerLoggedIn) {
				
				System.out.println("Dear: "+ cust.getFirstName() +" "+cust.getLastname()+"\n"
						+ "Press 1 to withdraw from your Account(s)"+"\n"
						+ "Press 2 to deposit to your Account(s)"+"\n"
						+ "Press 3 to transfer between your Accounts"+"\n"
						+ "Press q to quit!");
				
				String key = scan.next();
				
				switch (key) {
				case "1":
					
					System.out.println("Please enter account number you want to withdraw from!");
					System.out.println("You have "+cust.getAcs().size()+" accounts");
					int counterWithdrawal=1;
					for(Account temp: cust.getAcs()) {
						System.out.println("Account "+counterWithdrawal+"\n"
								+"Account# "+temp.getAccountNumber()+"\n"
								+"Balance $ "+temp.getBalance()
								+"==========================================");
						counterWithdrawal++;
					}
					
					int acNumWithdrawal = scan.nextInt();
					
					System.out.println("Please enter the amount to withdraw!");
					Double withdrawalMoneyAmount= scan.nextDouble();
					UtilityMethods.withdraw(acNumWithdrawal, withdrawalMoneyAmount );
					
					System.out.println("You withdrew "+withdrawalMoneyAmount+" from your account!");
					UtilityMethods.printAccount(acNumWithdrawal);
					
					
					break;
				case "2":
					
					System.out.println("Please enter account number you want to deposit to!");
					System.out.println("You have "+cust.getAcs().size()+" accounts");
					int counterDeposit=1;
					for(Account temp: cust.getAcs()) {
						System.out.println("Account "+counterDeposit+"\n"
								+"Account# "+temp.getAccountNumber()+"\n"
								+"Balance $ "+temp.getBalance()
								+"==========================================");
						counterDeposit++;
					}
					
					int acNumDeposit = scan.nextInt();
					
					System.out.println("Please enter the amount to deposit!");
					Double depositMoneyAmount= scan.nextDouble();
					UtilityMethods.withdraw(acNumDeposit, depositMoneyAmount );
					
					System.out.println("You depositted "+depositMoneyAmount+" to your account!");
					UtilityMethods.printAccount(acNumDeposit);
					
					break;
				case "3":
	
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
		} else {
			System.out.println("======Thank you for being a valued customer!======");
			mainLoggedIn = false;
		}
 }
		
		scan.close();
	}
	
	
}
