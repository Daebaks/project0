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
	boolean update(User u);								//Update a user. Returns true/false 
	
	//Delete
	void deleteById(int id);
	
	
	
	
}
