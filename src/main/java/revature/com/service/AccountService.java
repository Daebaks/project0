package revature.com.service;

import java.util.Iterator;
import java.util.List;

import revature.com.dao.AccountDao;
import revature.com.dao.AccountDaoInterface;
import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.exceptions.NoAccountsExistException;
import revature.com.exceptions.UsernameNotFoundException;
import revature.com.models.Account;

public class AccountService {

	private AccountDaoInterface adao = new AccountDao();
	
	public void viewAllAccounts() {
		List<Account> accList = adao.findAll();
		if(accList.isEmpty()) {
			throw new NoAccountsExistException("No accounts found!");
		}
		for(Account a: accList) {
			System.out.println(a);
		}
	}
	
	public void viewOwnerAccListById(int userId) {
		List<Account> accList = adao.findByOwner(userId);
		if(accList.isEmpty()) {
			throw new NoAccountsExistException("No accounts found!");
		}
		for(Account a: accList ) {
			System.out.println(a);
		}
		
	}
	
	//Withdraws amount from account with the id = accId
	public double withdraw(double amount, int accId) {
		
		Account a = adao.findById(accId);
		if(a==null) {
			throw new NoAccountsExistException("No such accounts with this id = "+accId);
		}
		if(amount <=0) {
			throw new InvalidAmountOfMoneyException("Amount to withdraw must be greater than 0 $");
		}
		if(amount>a.getBalance()) {
			throw new InsufficientBalanceException("Sorry, insufficient funds in your account");
		}
		
		//If all of the above pass, then balance will be updated.
		double newBalance = a.getBalance()-amount;
		
		adao.updateBalanceById(newBalance, accId);
		
		return newBalance;
		
	}
}
