package revature.com.service;

import java.util.Iterator;
import java.util.List;

import revature.com.dao.AccountDao;
import revature.com.dao.AccountDaoInterface;
import revature.com.models.Account;

public class AccountService {

	private AccountDaoInterface adao = new AccountDao();
	
	public void viewAllAccounts() {
		List<Account> accList = adao.findAll();
		for(Account a: accList) {
			System.out.println(a);
		}
	}
}
