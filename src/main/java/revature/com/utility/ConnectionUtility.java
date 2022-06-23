package revature.com.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

	private static Connection conn = null;

	private ConnectionUtility() {
		super();
	}

	//Checking/establishing a connection to the DB
	public static Connection getConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				System.out.println("There is an active connection in use!");
				return conn;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Getting env variables
		String url = System.getenv("DB-URL");
		String username = System.getenv("DB-USERNAME");
		String password = System.getenv("DB-PASSWORD");
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			//System.out.println("Established connection to the DB!");
		} catch (SQLException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		return conn;
	}
}
