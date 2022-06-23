package revature.com.dao;

import java.util.List;

import revature.com.models.Account;

public interface AccountDaoInterface {

	//Create a new account
	int insert(Account a);
	
	//Reading
	List<Account> findAll();
	
	Account findById(int id);
	
	List<Account> findByOwner(int accHolderId);
	
	//Updating
	boolean update(Account a);
	
	//Deleting
	boolean delete(Account a);
}
