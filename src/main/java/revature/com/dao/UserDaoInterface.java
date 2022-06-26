package revature.com.dao;

import java.util.List;

import revature.com.models.User;

public interface UserDaoInterface {
	
	//Create
	int insert(User u);		//Creating a new user returning user's id
	
	//Read
	User findById(int id);                                  //Returns user associated with the id given
	User findByUsername(String username);	   //Returns user by 
	List<User> findAll();                                    //Returns a list of all users available
	
	
	//Update
	void updateUsernameById(int id, String newUsername); 			//Update a user. Returns new username 
	void updatePassById(int id, String newPass);                        //Update password
	
	//Delete
	void deleteById(int id);								//Remove's a user and all of user's associated accounts
	
	
	
	
}
