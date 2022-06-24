package revature.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import revature.com.models.Account;
import revature.com.models.Role;
import revature.com.models.User;
import revature.com.utility.ConnectionUtility;

public class UserDao implements UserDaoInterface {

	@Override
	public int insert(User u) {

		Connection conn = ConnectionUtility.getConnection();

		String sql = "INSERT INTO users (username, pwd, user_role) VALUES (?, ?, ?) RETURNING users.id";

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, u.getUsername());
			st.setString(2, u.getPassword());
			st.setObject(3, u.getRole(), Types.OTHER);

			ResultSet rs;

			if (!(rs = st.executeQuery()).equals(null)) {

				rs.next();

				int id = rs.getInt(1);

				return id;

			}

		} catch (SQLException e) {
			System.out.println("Unable to create user! SQL - exception!");
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername(String username) {
		
		
		Connection conn = ConnectionUtility.getConnection();
		String sql = String.format("SELECT id, username, pwd, user_role  FROM users WHERE username =  \'%s\'", username);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				
				User u = new User();
				
				u.setId(rs.getInt("id"));
				u.setUsername(username);
				u.setPassword(rs.getString("pwd"));
				u.setRole(Role.valueOf(rs.getString("user_role")));
				u.setAccounts(null);
				
				return u;
			}
			
		} catch (SQLException e) {
			System.out.println("SQL failure in findByUsername UserDao");
			e.printStackTrace();
		}
		
		
	 
		
		return null;
	}

	@Override
	public List<User> findAll() {

//		List<User> usersList = new LinkedList<User>();
//
//		try (Connection conn = ConnectionUtility.getConnection()) {
//
//			Statement st = conn.createStatement();
//
//			String sql = "SELECT * FROM users INNER JOIN accounts ON users.id=accounts.users_a_id";
//			
//			ResultSet rs = st.executeQuery(sql);
//			
//			while(rs.next()) {
//				
//				int id = rs.getInt("id");
//				String username = rs.getString("username");
//				String password = rs.getString("pwd");
//				String userRole = rs.getString("user_role");
//				List<Account> userAccounts = rs
//				
//				User u = new User
//
//				
//			}
//			
//			
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return usersList;
		return null;
	}

	@Override
	public boolean update(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
