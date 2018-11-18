package com.mcda5510.connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSingleton {

	private static Connection connection;

	private ConnectionSingleton () {}

	public static Connection getConnection() throws Exception
	{
		connection = null;
		if(connection == null) {
			try {
				//Driver Registration
				Class.forName("com.mysql.cj.jdbc.Driver");

				//establishing connection
//				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/transactions?"    
//						+ "user=root&password=a1b2c3d4"   
//						+"&useSSL=false"              
//						+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/transactions?"    
						+ "user=mm_leong&password=A00430926"   
						+"&useSSL=false"              
						+"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
			}catch (Exception e) {
				e.printStackTrace();
			}finally {

			}
		}
		return connection;
	}
}
