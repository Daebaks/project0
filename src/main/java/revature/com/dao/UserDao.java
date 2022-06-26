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
		User u = new User();

		try (Connection conn = ConnectionUtility.getConnection();) {

			String sql = "SELECT *  FROM users WHERE id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("pwd"));
				u.setRole(Role.valueOf(rs.getString("user_role")));
				

			}

		} catch (SQLException e) {
			System.out.println("SQL failure in findByUsername UserDao");
			e.printStackTrace();
		}

		return u;
	}

	@Override
	public User findByUsername(String username) {

		User u = new User();

		try (Connection conn = ConnectionUtility.getConnection();) {

			String sql = "SELECT *  FROM users WHERE lower(username) = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username.toLowerCase());
			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("pwd"));
				u.setRole(Role.valueOf(rs.getString("user_role")));

			}

		} catch (SQLException e) {
			System.out.println("SQL failure in findByUsername UserDao");
			e.printStackTrace();
		}
		return u;
	}

	public void deleteById(int id) {
		
		try (Connection conn = ConnectionUtility.getConnection();) {

			//To delete a user, all three tables need to be updated
			String sql_accounts = "DELETE FROM accounts WHERE users_a_id=?";
			PreparedStatement st_accounts = conn.prepareStatement(sql_accounts);
			st_accounts.setInt(1, id);
			st_accounts.executeUpdate();
			String sql_junction = "DELETE FROM users_accounts_j WHERE users_j_id=?";
			PreparedStatement st_junction = conn.prepareStatement(sql_junction);
			st_junction.setInt(1, id);
			st_junction.executeUpdate();
			String sql_users = "DELETE FROM users WHERE id=?";
			PreparedStatement st_users = conn.prepareStatement(sql_users);
			st_users.setInt(1, id);
			st_users.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL failure inside AccountDao alterActivityById()");
			e.printStackTrace();
		}
		
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

	

}
