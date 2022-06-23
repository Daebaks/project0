package revature.com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		try(Connection conn = ConnectionUtility.getConnection() ) {
			
			Statement st = conn.createStatement();
			
			String sql = "SELECT * FROM accounts";
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
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
	public Account findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findByOwner(int accHolderId) {
		// TODO Auto-generated method stub
		return null;
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
