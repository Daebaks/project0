package revature.com;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import revature.com.dao.UserDao;
import revature.com.exceptions.NewUserRegistrationFailedException;
import revature.com.exceptions.UsernameAlreadyExistsException;
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
	//===============Testing register() method in UserService===================
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

		// Mocking findByUsername() dao method. Given username is clear to register
		// i.e. username isn't taken
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(new User());
		dummyUser.setId(1);
		us.register(dummyUser);
	}

	@Test(expected = NewUserRegistrationFailedException.class)
	public void testRegisterReturnedIdMinusOneAndNotEqualsNewUserId() {

		// Mocking findByUsername() dao method. Given username is clear to register
		// i.e. username isn't taken
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(new User());

		// Mocking inser() dao method. Returning -1
		when(mockDao.insert(dummyUser)).thenReturn(-1);
		
		us.register(dummyUser);
	}
	//===========================================================
	
	//===============Testing login() method in UserService====================
	@Test
	public void testLoginSuccessfulLogin() {
		
		//Strings
		String username = "Mike";
		String password = "pass";
		
		//In DB user
		User foundInDB = new User();
		foundInDB.setUsername("Mike");
		foundInDB.setPassword("pass");
		
		//Entered credentials user
		dummyUser.setUsername("Mike");
		dummyUser.setPassword("pass");
		
		// Mocking findByUsername() dao method. Returning a user found in DB
		when(mockDao.findByUsername(dummyUser.getUsername())).thenReturn(foundInDB);
		
		us.login(username, password);
		assertEquals(foundInDB.getUsername(),username );
		assertEquals(foundInDB.getPassword(), password);
		
	}
}
