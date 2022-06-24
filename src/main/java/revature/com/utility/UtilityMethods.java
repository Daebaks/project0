package revature.com.utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import revature.com.exceptions.UsernameAlreadyExistsException;
import revature.com.exceptions.UsernameNotFoundException;
import revature.com.exceptions.WrongPasswordException;

public class UtilityMethods {
	
	public static void validateRegistrationUsername(String u) throws UsernameAlreadyExistsException {
		
		List<String> allUsernames = new ArrayList<>();  //to check whether entered username is unique/not
		try (Connection conn = ConnectionUtility.getConnection()) {
			
			Statement st = conn.createStatement();
			String sql = "SELECT * FROM users";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				String username = rs.getString("username");
				allUsernames.add(username);
			}
			for(String s: allUsernames) {
				if(s.equalsIgnoreCase(u)) {
					throw new UsernameAlreadyExistsException("Username "+u+" is taken. Please choose a different username");
				}
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL failure in validateUsername utility method");
			e.printStackTrace();
		} 
	}
	
	public static void validateLoginUsername(String u) throws UsernameNotFoundException{
		List<String> allUsernames = new ArrayList<>();  //to check whether entered username is unique/not
		try (Connection conn = ConnectionUtility.getConnection()) {
			
			Statement st = conn.createStatement();
			String sql = "SELECT * FROM users";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				String username = rs.getString("username");
				allUsernames.add(username);
			}
					if(!allUsernames.contains(u)) {
						throw new UsernameNotFoundException("Username "+u+" does not exist in the DB. Try again");
						}
				
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL failure in validateUsername utility method");
			e.printStackTrace();
		} 
	}
	
	public static void validateLoginPassword(String u, String p) throws WrongPasswordException{
		
		String passwordFromDb ="";
		
		try (Connection conn = ConnectionUtility.getConnection()) {
			
			Statement st = conn.createStatement();
			String sql = String.format("SELECT * FROM users WHERE username =  \'%s\'", u);
			ResultSet rs = st.executeQuery(sql);
			 
			while(rs.next()) {
				passwordFromDb = rs.getString("pwd");
			}
			
			
			if(!p.equals(passwordFromDb)) {
				throw new WrongPasswordException("Password doesn't match our records. Please try again");
			}
			 
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL failure in validateLoginPassword utility method");
			e.printStackTrace();
		} 
		
	}
}
