package revature.com;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import revature.com.dao.UserDao;
import revature.com.exceptions.NewUserRegistrationFailedException;
import revature.com.exceptions.UsernameAlreadyExistsException;
import revature.com.exceptions.UsernameNotFoundException;
import revature.com.exceptions.WrongPasswordException;
import revature.com.models.Account;
import revature.com.models.Role;
import revature.com.models.User;
import revature.com.service.UserService;

public class UserServiceTests {

	private UserService us;
	private UserDao mockDao;
	private User dummyUser;

	// Setting up the tests
	@Before
	public void setup() {

		us = new UserService();
		mockDao = mock(UserDao.class);

		us.udao = mockDao;

		dummyUser = new User();
		dummyUser.setAccounts(new LinkedList<Account>());
		dummyUser.setId(0);
	}

	// Tearing down
	@After
	public void tearDown() {
		us = null;
		dummyUser = null;
		mockDao = null;
	}

	// Testings
	// ===============Testing register()===================
	@Test
	public void testSuccessfulRigisterUserReturnsNewPKId() {

		// I am using two DAOs methods inside register()
		// So, I need to mock both DAOs method
		dummyUser = new User(0, "Hila", "pass", Role.Admin, new LinkedList<Account>());

		// Mocking findByUsername() dao method. Given username is clear to register
		// i.e. username isn't taken and the return is null or empty new user
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(new User());

		// Mocking inser() dao method
		Random r = new Random();
		int fakePK = r.nextInt(100);
		when(mockDao.insert(dummyUser)).thenReturn(fakePK);

		// Finally, registering the user
		User registeredUser = us.register(dummyUser);
		assertEquals(registeredUser.getId(), fakePK);
	}

	@Test(expected = UsernameAlreadyExistsException.class)
	public void testEnteredUsernameAlreadyExistsInDB() {

		// Mocking findByUsername() dao method returning a user in the DB
		User returnedUsername = new User();
		returnedUsername.setUsername("mike");

		dummyUser.setUsername("Mike"); // entered username is taken
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(returnedUsername);
		us.register(dummyUser);
	}

	@Test(expected = NewUserRegistrationFailedException.class)
	public void testRegisterInitialIdNotEqualsZero() {

		dummyUser = new User(0, "Hila", "pass", Role.Admin, new LinkedList<Account>());
		// Mocking findByUsername() dao method. Given username is clear to register
		// i.e. username isn't taken
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(new User());
		dummyUser.setId(1);
		us.register(dummyUser);
	}

	@Test(expected = NewUserRegistrationFailedException.class)
	public void testRegisterReturnedIdMinusOneAndNotEqualsNewUserId() {

		dummyUser = new User(0, "Hila", "pass", Role.Admin, new LinkedList<Account>());
		// Mocking findByUsername() dao method. Given username is clear to register
		// i.e. username isn't taken
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(new User());

		// Mocking inser() dao method. Returning -1
		when(mockDao.insert(dummyUser)).thenReturn(-1);

		us.register(dummyUser);
	}
	// ===========================================================

	// ===============Testing login()====================
	@Test
	public void testLoginSuccessfulLogin() {

		// Strings entered
		String username = "Mike";
		String password = "pass";

		// In DB user
		User foundInDB = new User();
		foundInDB.setUsername("Mike");
		foundInDB.setPassword("pass");

		// Entered credentials user
		dummyUser.setUsername("Mike");
		dummyUser.setPassword("pass");

		// Mocking findByUsername() dao method. Returning a user found in DB
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(foundInDB);

		us.login(username, password);
		assertEquals(foundInDB.getUsername(), username);
		assertEquals(foundInDB.getPassword(), password);

	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoginWrongUsername() {

		// Strings entered
		String username = "hella";
		String password = "pass";

		// Entered credentials user
		dummyUser.setUsername("hella");
		dummyUser.setPassword("pass");

		// Mocking findByUsername() dao method. Returning a new user. No such user in DB
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(new User());

		us.login(username, password);

	}

	@Test(expected = WrongPasswordException.class)
	public void testLoginWrongPassword() {

		// Strings entered
		String username = "Mike";
		String password = "1234";

		// In DB user
		User foundInDB = new User();
		foundInDB.setUsername("Mike");
		foundInDB.setPassword("pass");

		// Entered credentials user
		dummyUser.setUsername("Mike");
		dummyUser.setPassword("1234");

		// Mocking findByUsername() dao method. Returning a user found in DB
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(foundInDB);

		us.login(username, password);
		}

	// ===========================================================

	// ===============Testing viewByUsername()====================
	
	@Test(expected = UsernameNotFoundException.class)
	public void testViewByUsernameNoUserFound() {
		dummyUser = new User(0, "Hila", "pass", Role.Admin, new LinkedList<Account>());
		when(mockDao.findByUsername("Hillla")).thenReturn(new User());
		
		us.viewByUsername("Hillla");
	}
	
	@Test
	public void testViewByUsernameReturningCorrectFoundUser() {
		dummyUser = new User(0, "Hila", "pass", Role.Admin, new LinkedList<Account>());
		
		when(mockDao.findByUsername("Hila")).thenReturn(dummyUser);
		
		us.viewByUsername("Hila");
		
	}
	// ===========================================================

	// ===============Testing viewById()====================
	@Test(expected = UsernameNotFoundException.class)
	public void testViewByIdNoUserFound() {
		dummyUser = new User(1, "Hila", "pass", Role.Admin, new LinkedList<Account>());
		when(mockDao.findById(100)).thenReturn(new User());
		
		us.viewById(100);
	}
	
	@Test
	public void testViewByIdReturningCorrectFoundUser() {
		dummyUser = new User(1, "Hila", "pass", Role.Admin, new LinkedList<Account>());
		
		when(mockDao.findById(1)).thenReturn(dummyUser);
		
		us.viewById(1);
		
	}
	// ===========================================================

	// ===============Testing viewBAllUsers()====================
	@Test
	public void testViewAllUsersReturnAllUsers() {
		List<User> users = new LinkedList<User>();
		User dummyUser1 = new User(1, "Hila", "pass", Role.Admin, new LinkedList<Account>());
		User dummyUser2 = new User(2, "Jacobe", "pass", Role.Employee, new LinkedList<Account>());
		User dummyUser3 = new User(3, "Mike", "pass", Role.Customer, new LinkedList<Account>());
		
		users.add(dummyUser1);
		users.add(dummyUser2);
		users.add(dummyUser3);
		
		when(mockDao.findAll()).thenReturn(users);
		us.viewAllUsers();
		
		
	}
	
	
}
