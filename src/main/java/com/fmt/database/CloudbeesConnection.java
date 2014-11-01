package com.fmt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CloudbeesConnection {
	private Connection connection = null;
	
	public Connection getConnection() {
		if(null == connection) {
			try {
				final String dbForName= "org.postgresql.Driver";
				final String dbPrefix= "postgres";
				Class.forName(dbForName);

				final String username= System.getenv().get("DBUSERNAME");
				final String password= System.getenv().get("DBPASSWORD");
				final String port= System.getenv().get("DBPORT");
				final String url= System.getenv().get("DBURL");
				final String dbUrl= System.getenv().get("DATABASE_URL");
				final String dbName= System.getenv().get("DBNAME");


				// Setup the connection with the DB
				connection= DriverManager.getConnection(String.format("jdbc:%s", dbUrl));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
	public void close() {
		if(null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		connection= null;
	}
}
