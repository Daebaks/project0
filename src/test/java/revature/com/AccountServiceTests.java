package revature.com;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import revature.com.dao.AccountDao;
import revature.com.exceptions.AccountNotActiveException;
import revature.com.exceptions.InsufficientBalanceException;
import revature.com.exceptions.InvalidAmountOfMoneyException;
import revature.com.exceptions.NoAccountsExistException;
import revature.com.models.Account;
import revature.com.models.Role;
import revature.com.models.User;
import revature.com.service.AccountService;

public class AccountServiceTests {

	private AccountService as;
	private AccountDao mockDao;
	private Account a1;
	private Account a2;
	private Account a3;
	private User dummyUser;

	// Setting up the tests
	@Before
	public void setup() {

		as = new AccountService();
		mockDao = mock(AccountDao.class);

		as.adao = mockDao;

		dummyUser = new User();

		a1 = new Account();
		a2 = new Account();
		a3 = new Account();
	}

	// Tearing down
	@After
	public void tearDown() {
		as = null;
		a1 = null;
		a2 = null;
		a3 = null;
		mockDao = null;
		dummyUser = null;
	}

	// Testings
	// =============================================================
	// =============================================================
	// ===============Testing viewAllActiveAccounts()==========================
	@Test(expected = NoAccountsExistException.class)
	public void testViewAllActiveAccountsNoAccountsFound() {
		when(mockDao.findAll()).thenReturn(new LinkedList<Account>());
		as.viewAllActiveAccounts();
	}

	// =============================================================
	// =============================================================
	// ===============Testing viewAllDisabledAccounts()========================
	@Test(expected = NoAccountsExistException.class)
	public void testViewAllDisabledAccountsNoAccountsFound() {
		when(mockDao.findAll()).thenReturn(new LinkedList<Account>());
		as.viewAllDisabledAccounts();
	}

	// =============================================================
	// =============================================================
	// ===============Testing viewAllAccounts()==============================
	@Test(expected = NoAccountsExistException.class)
	public void testViewAllAccountsNoAccountsFound() {
		when(mockDao.findAll()).thenReturn(new LinkedList<Account>());
		as.viewAllAccounts();
	}

//=============================================================
//=============================================================
//=====================Testing viewOwnerAccListById()====================
	@Test(expected = NoAccountsExistException.class)
	public void testViewOwnerAccListByIdNoAccountsFound() {

		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());

		when(mockDao.findByOwner(2)).thenReturn(new LinkedList<Account>());
		as.viewOwnerAccListById(2);
	}

	@Test
	public void testViewOwnerAccListReturnsCorrectListOfAccounts() {

		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(200, 2, false);
		Account a2 = new Account(350, 2, true);

		List<Account> list = new LinkedList<Account>();
		list.add(a1);
		list.add(a2);

		when(mockDao.findByOwner(2)).thenReturn(list);
		as.viewOwnerAccListById(2);

	}

	// =============================================================
	// =============================================================
	// =====================Testing withdraw()====================
	@Test(expected = NoAccountsExistException.class)
	public void testWithdrawNoAccountFowndInBank() {

		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		double amount = 100.00;
		Account a1 = new Account(1, 200.95, 2, false);

		// Returns new empty account if no accounts found
		when(mockDao.findById(0)).thenReturn(new Account());

		as.withdraw(amount, 0, 2);
	}

	@Test(expected = NoAccountsExistException.class)
	public void testWithdrawNoAccountForCustomerFoundWithGivenId() {

		//dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); //owned by customer
		Account a2 = new Account(2, 365.95, 2, false); //owned by customer
		
		Account a3 = new Account(5, 365.95, 9, false); //not owned by customer
		
		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);
		
		when(mockDao.findById(a3.getId())).thenReturn(a3);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		
		as.withdraw(amount, 5, 2);
	}
	
	@Test(expected = AccountNotActiveException.class)
	public void testWithdrawAccountNotActive() {

		//dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, false); //owned by customer
		Account a2 = new Account(2, 365.95, 2, true); //owned by customer
		
		Account a3 = new Account(5, 365.95, 9, false); //not owned by customer
		
		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);
		
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		
		as.withdraw(amount, a1.getId(), 2);
	}

	@Test(expected = InvalidAmountOfMoneyException.class)
	public void testWithdrawAmountLessThanZero() {

		//dummy data
		double amount = -100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); //owned by customer
		Account a2 = new Account(2, 365.95, 2, true); //owned by customer
		
		Account a3 = new Account(5, 365.95, 9, false); //not owned by customer
		
		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);
		
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		
		as.withdraw(amount, a1.getId(), 2);
	}
	
	@Test(expected = InvalidAmountOfMoneyException.class)
	public void testWithdrawAmountEqualsZero() {

		//dummy data
		double amount = 0;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); //owned by customer
		Account a2 = new Account(2, 365.95, 2, true); //owned by customer
		
		Account a3 = new Account(5, 365.95, 9, false); //not owned by customer
		
		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);
		
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		
		as.withdraw(amount, a1.getId(), 2);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawAmountGreaterThanBalance() {

		//dummy data
		double amount = 9999.99;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); //owned by customer
		Account a2 = new Account(2, 365.95, 2, true); //owned by customer
		
		Account a3 = new Account(5, 365.95, 9, false); //not owned by customer
		
		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);
		
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		
		as.withdraw(amount, a1.getId(), 2);
	}
	
	@Test
	public void testWithdrawNewAvailableBalance() {

		//dummy data
		double amount =100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); //owned by customer
		Account a2 = new Account(2, 365.95, 2, true); //owned by customer
		
		Account a3 = new Account(5, 365.95, 9, false); //not owned by customer
		
		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);
		
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		
		double newBalance = a1.getBalance() - amount;
		mockDao.updateBalanceById(newBalance, a1.getId());
		
		as.withdraw(amount, a1.getId(), 2);
		a1.setBalance(newBalance);
		
		assertEquals(a1.getBalance(),newBalance, 0 );
	}
}
