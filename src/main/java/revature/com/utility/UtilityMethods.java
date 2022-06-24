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
	
	
		
	
}
