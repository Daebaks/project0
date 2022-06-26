package revature.com.service;

import java.util.List;

import org.apache.log4j.Logger;

import revature.com.dao.AccountDao;
import revature.com.dao.AccountDaoInterface;
import revature.com.dao.UserDao;
import revature.com.dao.UserDaoInterface;
import revature.com.exceptions.AccountNotActiveException;
import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.exceptions.NoAccountsExistException;
import revature.com.models.Account;
import revature.com.models.User;

public class AccountService {

	private AccountDaoInterface adao = new AccountDao();
	private UserDaoInterface udao = new UserDao();

	// Log4j
	Logger logger = Logger.getLogger(AccountService.class);

	public void viewAllActiveAccounts() {
		List<Account> accList = adao.findAll();
		if (accList.isEmpty()) {
			throw new NoAccountsExistException("No accounts found!");
		}
		for (Account a : accList) {
			if (a.isActive()) {
				System.out.println(a);
			}
		}
	}

	
	public void viewAllDisabledAccounts() {
		List<Account> accList = adao.findAll();
		if (accList.isEmpty()) {
			throw new NoAccountsExistException("No accounts found!");
		}
		for (Account a : accList) {
			if (!a.isActive()) {
				System.out.println(a);
			}
		}
	}

	public void viewAllAccounts() {

		logger.info("Fetching accounts...");

		List<Account> accList = adao.findAll();
		if (accList.isEmpty()) {
			throw new NoAccountsExistException("No accounts found!");
		}
		for (Account a : accList) {
			System.out.println(a);
		}
	}

	
	public void viewOwnerAccListById(int userId) {
		List<Account> accList = adao.findByOwner(userId);
		if (accList.isEmpty()) {
			throw new NoAccountsExistException("No accounts found!");
		}
		for (Account a : accList) {
			System.out.println(a);
		}

	}

	public void viewAccById(int id) {
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		System.out.println(a);
	}
	public void approveAcById(int id) {
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		adao.alterActivityById(id, true);
		System.out.println("Account activated/approved");
	}

	public void denyAcById(int id) {
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		adao.alterActivityById(id, false);
		System.out.println("Account disabled/denied");
	}

	// Withdraws amount from account using account id
	public double withdraw(double amount, int id, int custId) {

		Account a = adao.findById(id);

		// Get the user ID to match it
		User u = udao.findById(custId);

		if (u != null) {
			if (u.getId() != a.getUsers_a_id()) {
				throw new NoAccountsExistException("No such accounts with this id = " + id); // this is for the user's
																								// Account list only not
																								// all accounts in the
																								// bank
			}
		}
		if (adao.getStatusById(id) == false) {
			throw new AccountNotActiveException("your account(s) are inactive. Call an admin/employee");
		}
		if (amount <= 0) {
			throw new InvalidAmountOfMoneyException("Amount to withdraw must be greater than 0 $");
		}
		if (amount > a.getBalance()) {
			throw new InsufficientBalanceException("Sorry, insufficient funds in your account");
		}

		// If all of the above pass, then balance will be updated.
		double newBalance = a.getBalance() - amount;

		adao.updateBalanceById(newBalance, id);

		return newBalance;

	}

	// Deposit amount to account using id
	public double deposit(double amount, int id, int custId) {

		Account a = adao.findById(id);

		// Get the user ID to match it
		User u = udao.findById(custId);

		if (u != null) {
			if (u.getId() != a.getUsers_a_id()) {
				throw new NoAccountsExistException("No such accounts with this id = " + id); // this is for the user's
																								// Account list only not
																								// all accounts in the
																								// bank
			}
		}
		if (adao.getStatusById(id) == false) {
			throw new AccountNotActiveException("your account(s) are inactive. Call an admin/employee");
		}
		if (amount <= 0) {
			throw new InvalidAmountOfMoneyException("Amount to deposit must be greater than 0 $");
		}

		// If all of the above pass, then balance will be updated.
		double newBalance = a.getBalance() + amount;

		adao.updateBalanceById(newBalance, id);

		return newBalance;

	}

	public void transfer(double amount, int accFrom, int accTo, int custId) {

		Account aFrom = adao.findById(accFrom);
		Account aTo = adao.findById(accTo);

		User u = udao.findById(custId);
		if (u != null) {
			if (u.getId() != aFrom.getUsers_a_id() || u.getId() != aTo.getUsers_a_id()) {
				throw new NoAccountsExistException("No such account(s) with with the provided id(s)"); // this is for
																										// the user's
																										// Account list
																										// only not all
																										// accounts in
																										// the bank
			}
		}
		if (adao.getStatusById(accFrom) == false || adao.getStatusById(accTo) == false) {
			throw new AccountNotActiveException("your account(s) are inactive. Call an admin/employee");
		}
		if (amount <= 0) {
			throw new InvalidAmountOfMoneyException("Amount to transfer must be greater than 0 $");
		}
		if (amount > aFrom.getBalance()) {
			throw new InsufficientBalanceException("Sorry, insufficient funds in your account");
		}

		// If all of the above pass, then balances in both accounts will be updated.
		// TRANSFER FROM
		double newBalanceFrom = aFrom.getBalance() - amount;
		adao.updateBalanceById(newBalanceFrom, accFrom);
		// TO
		double newBalanceTo = aTo.getBalance() + amount;
		adao.updateBalanceById(newBalanceTo, accTo);

	}

	public void editBalance(int id, double newBalance) {
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		
		adao.updateBalanceById(newBalance, id);
		
		System.out.println("Successfullu updated the balance");
		
		
	}
	
	
	public void removeAcc(int id) {
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		
		adao.deleteById(id);
		System.out.println("Account with the id: "+id+" was removed successfully");
		
	}
	
	
	
}
