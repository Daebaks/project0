package revature.com.utility;

import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.models.Account;
import revature.com.models.Customer;

public class UtilityMethods {

	// Deposit into a bank account
	public static void deposit(Customer c,int accountNum , double amount ) throws InvalidAmountOfMoneyException {
		
		
		if(amount<=0) {
			throw new InvalidAmountOfMoneyException("Please enter an amount greater than 0.00$");
		} else {
			//System.out.println("===START-DEBUGGER===");
			 
			for(Account a: c.getAcs()) {
				//System.out.println("===INSIDE-FOR===");
				if(a.getAccountNumber()==accountNum) {
					//System.out.println("===INSIDE-IF===");
					a.setBalance(a.getBalance()+amount);
				}
			}
		}
	}
	
	// Withdraw from a bank account
	public static void withdraw(Customer c, int accountNum , double amount ) 
		throws InvalidAmountOfMoneyException,	InsufficientBalanceException {
		
		
		if(amount<=0) {
			throw new InvalidAmountOfMoneyException("Please enter an amount greater than 0.00$");
		} else {
			 
			for(Account a: c.getAcs()) {
				if(a.getAccountNumber()==accountNum) {
					if(a.getBalance()<amount) {
						throw new InsufficientBalanceException("Sorry, you don't have enough fund in your account!");
					}else {
					a.setBalance(a.getBalance()-amount);
					}
				}
			}
		}
	}
	
	// Transfer between two accounts same customer
	public static void transfer(Customer c, int accountNumFrom , int accountNumTo,  double amount)
			throws InvalidAmountOfMoneyException,	InsufficientBalanceException {
		
		if(amount<=0) {
			throw new InvalidAmountOfMoneyException("Please enter an amount greater than 0.00$");
		}else {
			 
			for(Account a: c.getAcs()) {
				if(a.getAccountNumber()==accountNumFrom) {
					if(a.getBalance()<amount) {
						throw new InsufficientBalanceException("Sorry, you don't have enough fund in your account!");
					}else {
						a.setBalance(a.getBalance()-amount);
						for(Account a1: c.getAcs()) {
							if(a1.getAccountNumber()==accountNumTo) {
								a1.setBalance(a1.getBalance()+amount);
							}
						}
					}
				}
			}
		}
	}
	
	public static void printAccount(Customer c,int accountN) {
		for(Account a: c.getAcs()) {
			if(a.getAccountNumber()==accountN) {
				System.out.println("============Account Details============");
				System.out.println("Account# "+a.getAccountNumber());
				System.out.println("Balance# "+a.getBalance());
				System.out.println("==================================");
			}
		}
	}
	
	public static void printAllAccounts(Customer c) {
		System.out.println("==========================================");
		System.out.println("Customer: "+c.getFirstName()+" "+c.getLastname()+" has "+c.getAcs().size()+" accounts");
		int counter=1;
		for(Account temp: c.getAcs()) {
			System.out.println("Account "+counter+"\n"
					+"Account# "+temp.getAccountNumber()+"\n"
					+"Balance $ "+temp.getBalance()+"\n"
					+"==========================================");
			counter++;
		}
	}
	
}
