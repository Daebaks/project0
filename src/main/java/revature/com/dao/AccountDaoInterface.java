package revature.com.dao;

import java.util.List;

import revature.com.models.Account;

public interface AccountDaoInterface {

	//Create a new account
	int open(Account a, int userID);
	
	//Reading
	List<Account> findAll();
	Account findById(int id);
	List<Account> findByOwner(int accHolderId);
	boolean getStatusById(int id); //Get activity status of an account
	
	//Updating
	void alterActivityById(int id,boolean active);
	
	void updateBalanceById(double newBalance, int id); //updates account balance by account id if possible
	
	//Deleting
	boolean delete(Account a);
}
