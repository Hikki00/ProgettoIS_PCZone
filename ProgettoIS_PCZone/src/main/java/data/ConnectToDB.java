package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectToDB {
	
	public static synchronized Connection createDBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		
			Connection newConnection = null;
			String ip = "localhost";
			String port = "3306";
			String db = "pczone";
			String username = "root";
			String password = "root!";
	
			newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ port+"/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
			newConnection.setAutoCommit(false);
			return newConnection;
		} catch (ClassNotFoundException e) {
			System.out.println("DB driver not found:"+ e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

}
