package revature.com.service;

import org.apache.log4j.Logger;

import revature.com.dao.UserDao;
import revature.com.dao.UserDaoInterface;
import revature.com.exceptions.NewUserRegistrationFailedException;
import revature.com.exceptions.UsernameAlreadyExistsException;
import revature.com.exceptions.UsernameNotFoundException;
import revature.com.exceptions.WrongPasswordException;
import revature.com.models.User;

public class UserService {

	public UserDaoInterface udao = new UserDao();
	// Log4j
		Logger logger = Logger.getLogger(UserService.class);

	public User register(User u) {

		logger.info("New user registration");
		System.out.println("Registering the new user...\n");

		// Validate username if it's taken before registering
		User userRetrievedAttempt = udao.findByUsername(u.getUsername());
		if (userRetrievedAttempt.getUsername() != null
				&& userRetrievedAttempt.getUsername().equalsIgnoreCase(u.getUsername())) {
			throw new UsernameAlreadyExistsException(
					"Username " + u.getUsername() + " is taken. Please choose a different username");
		}

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

	public void viewByUsername(String username) {

		User u = udao.findByUsername(username);
		if (u.getUsername() == null) {
			throw new UsernameNotFoundException("User doesn't exist");
		}
		System.out.println(u);

	}

	public void viewById(int id) {
		User u = udao.findById(id);
		if (u.getUsername() == null) {
			throw new UsernameNotFoundException("User doesn't exist");
		}
		
		System.out.println(u);

	}

	
	public void viewAllUsers() {
		
		if(!udao.findAll().isEmpty()) {
			for(User u: udao.findAll()) {
				System.out.println(u);
			}
		}else {
			System.out.println("No accounts exist in the bank");
		}
	}
	
	public User login(String username, String password) {

		logger.info("User login..."+username);
		
		User u = udao.findByUsername(username);

		System.out.println("Loading...\n");

		if (u.getUsername() == null) {
			throw new UsernameNotFoundException("Username " + username + " does not exist in the DB. Try again");
		}

		if (!(u.getPassword().equals(password))) {
			throw new WrongPasswordException("Password doesn't match our records. Please try again");
		}

		System.out.println("Successful log in\n");

		return u;

	}

	public void removeUser(int id) {
		logger.info("User removal...");
		User u = udao.findById(id);
		if (u.getUsername() == null) {
			throw new UsernameNotFoundException("User doesn't exist");
		}
		
		udao.deleteById(id);
		System.out.println("successfully removed user and all associated accounts");
		
	}
	
	public void changeUsername(int id, String newName) {
		logger.info("Username change ");
		User u = udao.findById(id);
		if (u.getUsername() == null) {
			throw new UsernameNotFoundException("User doesn't exist");
		}
		if(u.getUsername().equalsIgnoreCase(newName)) {
			throw new UsernameAlreadyExistsException("username is taken, try a different new name");
		}
		udao.updateUsernameById(id, newName);
		System.out.println("Successfully updated username to "+newName);		
	}
	
	public void changePassword(int id, String newPass) {
		logger.info("Password change");
		User u = udao.findById(id);
		if (u.getUsername() == null) {
			throw new UsernameNotFoundException("User doesn't exist");
		}
		
		udao.updatePassById(id, newPass);
		System.out.println("Successfully updated password");		
	}
	
	
	
	
	
	
}
