package revature.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import revature.com.models.Account;
import revature.com.utility.ConnectionUtility;

public class AccountDao implements AccountDaoInterface {

	@Override
	public int insert(Account a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Account> findAll() {

		List<Account> accList = new LinkedList<Account>();
		try (Connection conn = ConnectionUtility.getConnection()) {
			Statement st = conn.createStatement();
			String sql = "SELECT * FROM accounts";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int accountHolderId = rs.getInt("users_a_id");
				boolean isActive = rs.getBoolean("active");
				Account a = new Account(id, balance, accountHolderId, isActive);
				accList.add(a);
			}
		} catch (SQLException e) {
			System.out.println("Unable to get accounts because of SQL!");
			e.printStackTrace();
		}
		return accList;
	}

	

	@Override
	public List<Account> findByOwner(int userOwnerId) {

		List<Account> ownerAccList = new ArrayList<>();
		try (Connection conn = ConnectionUtility.getConnection();) {
			String sql = "SELECT  a.users_a_id , a.id, a.balance, a.active  FROM users u \n" + "INNER JOIN accounts a \n"
					+ "ON u.id = a.users_a_id \n" + "WHERE u.id = ?";
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, userOwnerId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setUsers_a_id(rs.getInt("users_a_id"));
				a.setBalance(rs.getDouble("balance"));
				a.setActive(rs.getBoolean("active"));
				ownerAccList.add(a);
			}
		}
		catch (SQLException e) {
			System.out.println("SQL failure inside AccountDao findByOwner()");
			e.printStackTrace();
		}
		return ownerAccList;
	}

	
	@Override
	public Account findById(int id) {

		Account a = new Account();
		
		try (Connection conn = ConnectionUtility.getConnection();) {
			String sql = "SELECT  *  FROM accounts WHERE id = ?";
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				a.setId(rs.getInt("id"));
				a.setUsers_a_id(rs.getInt("users_a_id"));
				a.setBalance(rs.getDouble("balance"));
				a.setActive(rs.getBoolean("active"));
			}
		}
		catch (SQLException e) {
			System.out.println("SQL failure inside AccountDao findById()");
			e.printStackTrace();
		}
		
		return a;
	}
	
	@Override
	public void updateBalanceById(double newBalance, int id) {
		
		try (Connection conn = ConnectionUtility.getConnection();) {
			
			String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, newBalance);
			st.setInt(2, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println("SQL failure inside AccountDao updateBalanceById()");
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean update(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

	

	

}
