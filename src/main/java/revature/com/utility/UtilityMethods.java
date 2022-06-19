package revature.com.utility;

import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.models.Account;
import revature.com.models.Customer;

public class UtilityMethods {

	// Deposit into a bank account
	public static void deposit(int accountNum , double amount ) throws InvalidAmountOfMoneyException {
		if(amount<=0) {
			throw new InvalidAmountOfMoneyException("Please enter an amount greater than 0.00$");
		}
		Customer c = new Customer();
		for(Account a: c.getAcs()) {
			if(a.getAccountNumber()==accountNum) {
				a.setBalance(a.getBalance()+amount);
			}
		}
	}
	
	// Withdraw from a bank account
	public static void withdraw(int accountNum , double amount ) 
		throws InvalidAmountOfMoneyException,	InsufficientBalanceException {
		if(amount<=0) {
			throw new InvalidAmountOfMoneyException("Please enter an amount greater than 0.00$");
		}
		
		Customer c = new Customer();
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
