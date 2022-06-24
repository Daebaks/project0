package revature.com.service;

import java.util.Iterator;
import java.util.List;

import revature.com.dao.AccountDao;
import revature.com.dao.AccountDaoInterface;
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
}
