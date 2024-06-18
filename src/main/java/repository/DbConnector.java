//$Id$
package repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {

	static String url = "jdbc:mysql://localhost:3306/flex";
	static String username = "root";
	static String password = "admin@123";
	
	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, username, password);
			return con;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

