package revature.com.service;

import java.util.Iterator;
import java.util.List;

import revature.com.dao.AccountDao;
import revature.com.dao.AccountDaoInterface;
import revature.com.dao.UserDao;
import revature.com.dao.UserDaoInterface;
import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.exceptions.NoAccountsExistException;
import revature.com.exceptions.UsernameNotFoundException;
import revature.com.models.Account;
import revature.com.models.User;

public class AccountService {

	private AccountDaoInterface adao = new AccountDao();
	private UserDaoInterface udao = new UserDao();

	public void viewAllAccounts() {
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

	// Withdraws amount from account using account id
	public double withdraw(double amount, int id, int custId) {

		Account a = adao.findById(id);

		// Get the user ID to match it
		User u = udao.findById(custId);

		if (u != null) {
			if (u.getId() != a.getUsers_a_id()) {
				throw new NoAccountsExistException("No such accounts with this id = " + id); // this is for the user's Account list only not all accounts in the bank
			}
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
				throw new NoAccountsExistException("No such accounts with this id = " + id); // this is for the user's Account list only not all accounts in the bank
			}
		}
		if (amount <= 0) {
			throw new InvalidAmountOfMoneyException("Amount to deposit must be greater than 0 $");
		}

		// If all of the above pass, then balance will be updated.
		double newBalance = a.getBalance() + amount;

		adao.updateBalanceById(newBalance, id);

		return newBalance;

	}

}
