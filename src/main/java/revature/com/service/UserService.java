package revature.com.service;

import revature.com.dao.UserDao;
import revature.com.dao.UserDaoInterface;
import revature.com.exceptions.NewUserRegistrationFailedException;
import revature.com.exceptions.UsernameNotFoundException;
import revature.com.exceptions.WrongPasswordException;
import revature.com.models.User;

public class UserService {

	private UserDaoInterface udao = new UserDao();

	public User register(User u) {

		System.out.println("Loading...\n");

		if (u.getId() != 0) {
			throw new NewUserRegistrationFailedException("Invalid user registration because id was not zero!");
		}

		int generatedId = udao.insert(u); // Creating new user and returning user's id

		if (generatedId != -1 && generatedId != u.getId()) {
			u.setId(generatedId);
		} else {
			throw new NewUserRegistrationFailedException(
					"Invalid user id. Id was -1 or did not change after insertion into the DB");
		}

		System.out.println("Registration was successful and user id is " + u.getId());

		return u;

	}

	public User getByUsername() {

		return null;

	}

	public User login(String username, String password) {

		User u = udao.findByUsername(username);

		if (u == null) {
			throw new UsernameNotFoundException("Username " + username + " does not exist in the DB. Try again");
		}

		if (!(u.getPassword().equals(password))) {
			throw new WrongPasswordException("Password doesn't match our records. Please try again");	
		}

		System.out.println("Successful log in\n");
		
		return u;

	}

}
