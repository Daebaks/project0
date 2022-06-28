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
	public int open(int userID) {
		int accountID=0;
		Connection conn = ConnectionUtility.getConnection();
		//First create a new row in accounts table, then take the new id to create a row in the junction table
		String sql1 = "INSERT INTO accounts  (balance, users_a_id, active) VALUES (? ,? ,?) RETURNING accounts.id";
		
		try {
			PreparedStatement st1 = conn.prepareStatement(sql1);
			st1.setDouble(1, 0);
			st1.setInt(2, userID);
			st1.setBoolean(3, false);
			ResultSet rs;
			if (!(rs = st1.executeQuery()).equals(null)) {
				rs.next();
				accountID=rs.getInt(1);
				//Now creating a new row for the junction table
				String sql2 = "INSERT INTO users_accounts_j  (users_j_id, accounts_j_id) VALUES (? ,? )";
				PreparedStatement st2 = conn.prepareStatement(sql2);
				st2.setInt(1, userID);
				st2.setInt(2, accountID);
				st2.executeUpdate();
				return accountID;
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to create account! SQL - exception!");
			e.printStackTrace();
		} return accountID;
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
			String sql = "SELECT  a.users_a_id , a.id, a.balance, a.active  FROM users u \n"
					+ "INNER JOIN accounts a \n" + "ON u.id = a.users_a_id \n" + "WHERE u.id = ?";

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
		} catch (SQLException e) {
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
		} catch (SQLException e) {
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
		} catch (SQLException e) {
			System.out.println("SQL failure inside AccountDao updateBalanceById()");
			e.printStackTrace();
		}
	}

	@Override
	public boolean getStatusById(int id) {

		boolean status = false;
		try (Connection conn = ConnectionUtility.getConnection();) {

			String sql = "SELECT active FROM accounts WHERE id=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				status = rs.getBoolean("active");
			}

		} catch (SQLException e) {
			System.out.println("SQL failure - unable to get status");
			e.printStackTrace();
		}

		return status;
	}

	@Override
	public void alterActivityById(int id, boolean active) {
		try (Connection conn = ConnectionUtility.getConnection();) {

			String sql = "UPDATE accounts SET active = ? WHERE id = ?";

			PreparedStatement st = conn.prepareStatement(sql);
			st.setBoolean(1, active);
			st.setInt(2, id);
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL failure inside AccountDao alterActivityById()");
			e.printStackTrace();
		}
	}

	public void deleteById(int id) {
		
		try (Connection conn = ConnectionUtility.getConnection();) {

			//To delete an account I need to remove a row from both junctionTable and accounts table
			String sql_accounts = "DELETE FROM accounts WHERE id=?";
			PreparedStatement st_accounts = conn.prepareStatement(sql_accounts);
			st_accounts.setInt(1, id);
			st_accounts.executeUpdate();
			String sql_junction = "DELETE FROM users_accounts_j WHERE accounts_j_id=?";
			PreparedStatement st_junction = conn.prepareStatement(sql_junction);
			st_junction.setInt(1, id);
			st_junction.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL failure inside AccountDao alterActivityById()");
			e.printStackTrace();
		}
		
		
	}

}
