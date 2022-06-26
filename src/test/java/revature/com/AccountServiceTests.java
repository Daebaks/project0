package revature.com;

import static org.mockito.Mockito.mock;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;

import revature.com.dao.AccountDao;
import revature.com.dao.UserDao;
import revature.com.models.Account;
import revature.com.models.User;
import revature.com.service.AccountService;
import revature.com.service.UserService;

public class AccountServiceTests {
	
	private AccountService as;
	private AccountDao mockDao;
	private Account dummyAccount;

	// Setting up the tests
	@Before
	public void setup() {

		as = new AccountService();
		mockDao = mock(AccountDao.class);

		as.adao = mockDao;

		dummyAccount = new Account();
		dummyAccount.setId(0);
	}

	// Tearing down
	@After
	public void tearDown() {
		as = null;
		dummyAccount = null;
		mockDao = null;
	}

	// Testings
	// ===========================================================

	// ===============Testing login()====================
	
	
}
