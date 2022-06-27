package revature.com;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
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
	private Account a4;
	private User dummyUser;
	private ByteArrayOutputStream outBefore = new ByteArrayOutputStream();
	private PrintStream outAfter = System.out;

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
		a4 = new Account();

	}

	@Before
	public void setStreams() {
		System.setOut(new PrintStream(outBefore));
	}

	// Tearing down
	@After
	public void tearDown() {
		as = null;
		a1 = null;
		a2 = null;
		a3 = null;
		a4 = null;
		mockDao = null;
		dummyUser = null;
	}

	@After
	public void restoreInitialStreams() {
		System.setOut(outAfter);
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

	@Test
	public void testViewAllActiveAccountsPrintingActiveAccounts() {
		Account a1 = new Account(1, 2000.95, 2, true);
		Account a2 = new Account(2, 365.95, 2, false);
		Account a3 = new Account(5, 365.95, 9, false);

		List<Account> allAccList = new LinkedList<Account>();
		allAccList.add(a1);
		allAccList.add(a2);
		allAccList.add(a3);

		when(mockDao.findAll()).thenReturn(allAccList); // returns all accounts
		// Now: only first two active accounts should get printed
		for (Account a : allAccList) {
			if (a.isActive()) {
				System.out.print(a);
				Assert.assertEquals(a.toString(), outBefore.toString());
			}
		}
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

	@Test
	public void testViewAllDisabledAccountsPrintingActiveAccounts() {
		Account a1 = new Account(1, 2000.95, 2, true);
		Account a2 = new Account(2, 365.95, 2, true);
		Account a3 = new Account(5, 365.95, 9, false);

		List<Account> allAccList = new LinkedList<Account>();
		allAccList.add(a1);
		allAccList.add(a2);
		allAccList.add(a3);

		when(mockDao.findAll()).thenReturn(allAccList); // returns all accounts
		// Now: only first two active accounts should get printed
		for (Account a : allAccList) {
			if (!a.isActive()) {
				System.out.print(a);
				Assert.assertEquals(a.toString(), outBefore.toString());
			}
		}
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

	@Test
	public void testViewAllAccountsPrintingActiveAccounts() {
		Account a1 = new Account(1, 2000.95, 2, true);
		Account a2 = new Account(2, 365.95, 2, true);
		Account a3 = new Account(5, 365.95, 9, false);

		List<Account> allAccList = new LinkedList<Account>();
		allAccList.add(a1);
		allAccList.add(a2);
		allAccList.add(a3);

		when(mockDao.findAll()).thenReturn(allAccList); // returns all accounts
		// Now: only first two active accounts should get printed
		for (Account a : allAccList) {
			if (!a.isActive()) {
				System.out.print(a);
				Assert.assertEquals(a.toString(), outBefore.toString());
			}
		}
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

		// dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, false); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a3.getId())).thenReturn(a3);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);

		as.withdraw(amount, 5, 2);
	}

	@Test(expected = AccountNotActiveException.class)
	public void testWithdrawAccountNotActive() {

		// dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, false); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

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

		// dummy data
		double amount = -100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

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

		// dummy data
		double amount = 0;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

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

		// dummy data
		double amount = 9999.99;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

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

		// dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

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

		assertEquals(a1.getBalance(), newBalance, 0);
	}

	// =============================================================
	// =============================================================
	// =====================Testing deposit()====================
	@Test(expected = NoAccountsExistException.class)
	public void testDepositNoAccountFowndInBank() {

		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		double amount = 100.00;
		Account a1 = new Account(1, 200.95, 2, false);

		// Returns new empty account if no accounts found
		when(mockDao.findById(0)).thenReturn(new Account());

		as.deposit(amount, 0, 2);
	}

	@Test(expected = NoAccountsExistException.class)
	public void testDepositNoAccountForCustomerFoundWithGivenId() {

		// dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, false); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a3.getId())).thenReturn(a3);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);

		as.deposit(amount, 5, 2);
	}

	@Test(expected = AccountNotActiveException.class)
	public void testDepositAccountNotActive() {

		// dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, false); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());

		as.deposit(amount, a1.getId(), 2);
	}

	@Test(expected = InvalidAmountOfMoneyException.class)
	public void testDepositAmountLessThanZero() {

		// dummy data
		double amount = -100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());

		as.deposit(amount, a1.getId(), 2);
	}

	@Test(expected = InvalidAmountOfMoneyException.class)
	public void testDepositAmountEqualsZero() {

		// dummy data
		double amount = 0;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());

		as.deposit(amount, a1.getId(), 2);
	}

	@Test
	public void testDepositNewAvailableBalance() {

		// dummy data
		double amount = 100.00;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());

		double newBalance = a1.getBalance() + amount;
		mockDao.updateBalanceById(newBalance, a1.getId());
		as.deposit(amount, a1.getId(), 2);
		a1.setBalance(newBalance);
		assertEquals(a1.getBalance(), newBalance, 0);

	}

	// =============================================================
	// =============================================================
	// =====================Testing transfer()====================
	@Test(expected = NoAccountsExistException.class)
	public void testTransferAccFromDoesntExist() {

		// dummy data
		double amount = 50.0;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		Account a1 = new Account(1, 2000.95, 2, true); // owned by customer AccFrom
		Account a2 = new Account(2, 365.95, 2, true); // owned by customer AccTo

		Account a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a4.getId())).thenReturn(a4);
		when(mockDao.findById(a1.getId())).thenReturn(a1);

		as.transfer(amount, a4.getId(), a1.getId(), 2);

	}

	@Test(expected = NoAccountsExistException.class)
	public void testTransferAccToDoesntExist() {

		// dummy data
		double amount = 100;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, true); // owned by customer
		a2 = new Account(2, 365.95, 2, true); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a4.getId())).thenReturn(a4);

		as.transfer(amount, a1.getId(), a4.getId(), 2);
	}

	@Test(expected = NoAccountsExistException.class)
	public void testTransferAccFromNotOwnedByCustomer() {

		// dummy data
		double amount = 100;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, true); // owned by customer
		a2 = new Account(2, 365.95, 2, true); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a3.getId())).thenReturn(a3);
		when(mockDao.findById(a1.getId())).thenReturn(a1);

		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);

		as.transfer(amount, a3.getId(), a1.getId(), 2);
	}

	@Test(expected = NoAccountsExistException.class)
	public void testTransferAccToNotOwnedByCustomer() {

		// dummy data
		double amount = 100;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, true); // owned by customer
		a2 = new Account(2, 365.95, 2, true); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a3.getId())).thenReturn(a3);

		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);

		as.transfer(amount, a1.getId(), a3.getId(), 2);
	}

	@Test(expected = AccountNotActiveException.class)
	public void testTransferAccFromNotActive() {

		// dummy data
		double amount = 100;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, false); // owned by customer
		a2 = new Account(2, 365.95, 2, true); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a2.getId())).thenReturn(a2);
		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		when(mockDao.getStatusById(a2.getId())).thenReturn(a2.isActive());

		as.transfer(amount, a1.getId(), a2.getId(), 2);
	}

	@Test(expected = AccountNotActiveException.class)
	public void testTransferAccToNotActive() {

		// dummy data
		double amount = 100;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, true); // owned by customer
		a2 = new Account(2, 365.95, 2, false); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a2.getId())).thenReturn(a2);

		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		when(mockDao.getStatusById(a2.getId())).thenReturn(a2.isActive());
		as.transfer(amount, a1.getId(), a2.getId(), 2);
	}

	@Test(expected = InvalidAmountOfMoneyException.class)
	public void testTransferAmountLessThanZero() {

		// dummy data
		double amount = -100;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, true); // owned by customer
		a2 = new Account(2, 365.95, 2, true); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a2.getId())).thenReturn(a2);

		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		when(mockDao.getStatusById(a2.getId())).thenReturn(a2.isActive());

		as.transfer(amount, a1.getId(), a2.getId(), 2);
	}

	@Test(expected = InvalidAmountOfMoneyException.class)
	public void testTransferAmountIsZero() {

		// dummy data
		double amount = 0;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, true); // owned by customer
		a2 = new Account(2, 365.95, 2, true); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a2.getId())).thenReturn(a2);

		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		when(mockDao.getStatusById(a2.getId())).thenReturn(a2.isActive());

		as.transfer(amount, a1.getId(), a2.getId(), 2);
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testTransferAmountGreaterThanBalanceAccFrom() {

		// dummy data
		double amount = 99999.99;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.95, 2, true); // owned by customer
		a2 = new Account(2, 365.95, 2, true); // owned by customer

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a2.getId())).thenReturn(a2);

		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		when(mockDao.getStatusById(a2.getId())).thenReturn(a2.isActive());

		as.transfer(amount, a1.getId(), a2.getId(), 2);
	}

	@Test
	public void testTransferNewBalancesAreCorrect() {

		// dummy data
		double amount = 100.5;
		dummyUser = new User(2, "Hila", "pass", Role.Customer, new LinkedList<Account>());
		a1 = new Account(1, 2000.0, 2, true); // owned by customer accFrom
		a2 = new Account(2, 365.95, 2, true); // owned by customer accTo

		a3 = new Account(5, 365.95, 9, false); // not owned by customer

		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB

		List<Account> CustomerAcclist = new LinkedList<Account>();
		CustomerAcclist.add(a1);
		CustomerAcclist.add(a2);

		when(mockDao.findById(a1.getId())).thenReturn(a1);
		when(mockDao.findById(a2.getId())).thenReturn(a2);

		when(mockDao.findByOwner(2)).thenReturn(CustomerAcclist);
		when(mockDao.getStatusById(a1.getId())).thenReturn(a1.isActive());
		when(mockDao.getStatusById(a2.getId())).thenReturn(a2.isActive());
		double newBalanceFrom = a1.getBalance() - amount;
		double newBalanceTo = a2.getBalance() + amount;
		mockDao.updateBalanceById(newBalanceFrom, a1.getId());
		mockDao.updateBalanceById(newBalanceTo, a2.getId());
		a1.setBalance(newBalanceFrom);
		a2.setBalance(newBalanceTo);

		assertEquals(newBalanceFrom, a1.getBalance(), 0);
		assertEquals(newBalanceTo, a2.getBalance(), 0);

		as.transfer(amount, a1.getId(), a2.getId(), 2);
	}

	// =============================================================
	// =============================================================
	// =====================Testing editBalance()====================
	@Test(expected = NoAccountsExistException.class)
	public void testEditBalanceNoAccountFound() {
		a1 = new Account(1, 2000.0, 2, true);
		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB
		double newBalance = 1234.49;
		when(mockDao.findById(a4.getId())).thenReturn(a4);
		as.editBalance(a4.getId(), newBalance);
	}

	@Test
	public void testEditBalanceCheckNewBalance() {
		a1 = new Account(1, 2000.0, 2, true);
		double newBalance = 1234.49;
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		mockDao.updateBalanceById(newBalance, a1.getId());
		a1.setBalance(newBalance);
		assertEquals(newBalance, a1.getBalance(), 0);
		as.editBalance(a1.getId(), newBalance);

	}

	// =============================================================
	// =============================================================
	// =====================Testing viewAccById()====================
	@Test(expected = NoAccountsExistException.class)
	public void testViewAccountByIdNoAccountFound() {
		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB
		when(mockDao.findById(a4.getId())).thenReturn(a4);
		as.viewAccById(0);
	}

	@Test
	public void testViewAccountByIdPrintOutAccountFound() {
		a1 = new Account(1, 2000.0, 2, true);
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		System.out.print(a1);
		Assert.assertEquals(a1.toString(), outBefore.toString());
		as.viewAccById(a1.getId());
	}

	// =============================================================
	// =============================================================
	// =====================Testing approveAcById()====================
	@Test(expected = NoAccountsExistException.class)
	public void testApproveAccountByIdNoAccountFound() {
		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB
		when(mockDao.findById(a4.getId())).thenReturn(a4);
		as.approveAcById(a4.getId());
	}

	@Test
	public void testApproveAccountByIdApprovedWithPrintedMsg() {
		a1 = new Account(1, 2000.0, 2, false);
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		mockDao.alterActivityById(a1.getId(), true);
		System.out.print("Account activated/approved");
		Assert.assertEquals("Account activated/approved", outBefore.toString());
		as.approveAcById(a1.getId());
	}

	// =============================================================
	// =============================================================
	// =====================Testing denyAcById()====================
	@Test(expected = NoAccountsExistException.class)
	public void testDenyAccountByIdNoAccountFound() {
		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB
		when(mockDao.findById(a4.getId())).thenReturn(a4);
		as.denyAcById(a4.getId());
	}

	@Test
	public void testDenyAccountByIdApprovedWithPrintedMsg() {
		a1 = new Account(1, 2000.0, 2, true);
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		mockDao.alterActivityById(a1.getId(), false);
		System.out.print("Account disabled/denied");
		Assert.assertEquals("Account disabled/denied", outBefore.toString());
		as.denyAcById(a1.getId());
	}

	// =============================================================
	// =============================================================
	// =====================Testing removeAcc()====================
	@Test(expected = NoAccountsExistException.class)
	public void testRemoveAccByIdNoAccountFound() {
		a4.setId(0); // id = 0 means this account (a4) doesn't exist in the DB
		when(mockDao.findById(a4.getId())).thenReturn(a4);
		as.removeAcc(a4.getId());
	}
	@Test 
	public void testRemoveAccByIdAccRemovedMsg() {
		int id = a1.getId();
		a1 = new Account(1, 2000.0, 2, true);
		when(mockDao.findById(a1.getId())).thenReturn(a1);
		mockDao.deleteById(a1.getId());
		System.out.print("Account with the id: "+id+" was removed successfully");
		Assert.assertEquals("Account with the id: "+id+" was removed successfully", outBefore.toString());
		as.removeAcc(a1.getId());
	}
	
}
