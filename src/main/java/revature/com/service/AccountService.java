package revature.com.service;

import java.util.List;

import org.apache.log4j.Logger;

import revature.com.dao.AccountDao;
import revature.com.dao.AccountDaoInterface;
import revature.com.exceptions.AccountNotActiveException;
import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.exceptions.NoAccountsExistException;
import revature.com.models.Account;

public class AccountService {

	public AccountDaoInterface adao = new AccountDao();

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
			throw new NoAccountsExistException("No accounts found! or user doesn't exist!");
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
		logger.info("Account activation/approval... id = "+id);
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		adao.alterActivityById(id, true);
		System.out.println("Account approved/activated");
	}

	public void denyAcById(int id) {
		logger.info("Account deactivation/denial... id = "+id);
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		adao.alterActivityById(id, false);
		System.out.println("Account disabled/denied");
	}

	// Withdraws amount from account using account id
	public double withdraw(double amount, int id, int custId) {
		
		logger.info("Withdraw transaction... customer id ="+custId);

		Account a = adao.findById(id);

		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}else
		// Verify ownership of the account
		
			if (!adao.findByOwner(custId).contains(a)) {
				throw new NoAccountsExistException("No such accounts with this id = " + id); // this is for the user's
																								// Account list only not
																								// all accounts in the
																								// bank
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

		logger.info("Deposit transaction... customer id ="+custId);
		Account a = adao.findById(id);

		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}else
		// Verify ownership of the account
					if (!adao.findByOwner(custId).contains(a)) {
						throw new NoAccountsExistException("No such accounts with this id = " + id); // this is for the user's
																										// Account list only not
																										// all accounts in the
																										// bank
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

	//Admins can transfer money from any 2 accounts regardless ownership and status of the accounts
	public void transferAdmin(double amount, int accFrom, int accTo) {
		
		logger.info("Transfer transaction -- admin...");
		Account aFrom = adao.findById(accFrom);
		Account aTo = adao.findById(accTo);
		
		if(aFrom.getId()==0 || aTo.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		if (amount <= 0) {
			throw new InvalidAmountOfMoneyException("Amount to transfer must be greater than 0 $");
		}
		if (amount > aFrom.getBalance()) {
			throw new InsufficientBalanceException("Sorry, insufficient funds in your account");
		}
		// TRANSFER FROM
				double newBalanceFrom = aFrom.getBalance() - amount;
				adao.updateBalanceById(newBalanceFrom, accFrom);
				// TO
				double newBalanceTo = aTo.getBalance() + amount;
				adao.updateBalanceById(newBalanceTo, accTo);
		
	}
	
	public void transfer(double amount, int accFrom, int accTo, int custId) {

		logger.info("Transfer transaction... customer id ="+custId);
		Account aFrom = adao.findById(accFrom);
		Account aTo = adao.findById(accTo);

		if(aFrom.getId()==0 || aTo.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}else
		// Verify ownership of the accounts
					if (!adao.findByOwner(custId).contains(aFrom) || !adao.findByOwner(custId).contains(aTo)) {
						throw new NoAccountsExistException("No such accounts with the provided ids "); // this is for the user's
																										// Account list only not
																										// all accounts in the
																										// bank
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
		
		System.out.println("Successfully updated the balance");
		
		
	}
	
	
	public void removeAcc(int id) {
		logger.info("Account deletion... id = "+id);
		Account a = adao.findById(id);
		if(a.getId()==0) {
			throw new NoAccountsExistException("Account doesn't exist with this id");
		}
		
		adao.deleteById(id);
		System.out.println("Account with the id: "+id+" was removed successfully");
		
	}
	
	public void createNew(int userId) {
		logger.info("Account creation...");
		int accID= adao.open(userId);
		System.out.println("New account created successfully! account id = "+accID);
	}
	
	
}
