package revature.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import revature.com.models.User;
import revature.com.utility.ConnectionUtility;

public class UserDao implements UserDaoInterface {

	@Override
	public int insert(User u) {
		
		Connection conn = ConnectionUtility.getConnection();
		
		String sql = "INSERT INTO users (username, pwd, user_role) VALUES (?, ?, ?) RETURNING users.id";
		try {
			PreparedStatement s = conn.prepareStatement(sql);
			
			s.setString(1, u.getUsername());
			s.setString(2, u.getPassword());
			s.setObject(3, u.getRole(), Types.OTHER);
			
			ResultSet rs;
			
			if(!(rs = s.executeQuery()).equals(null)) {
				
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
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
