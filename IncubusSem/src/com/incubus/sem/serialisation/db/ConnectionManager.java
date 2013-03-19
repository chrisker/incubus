package com.incubus.sem.serialisation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private final static ConnectionManager instance = new ConnectionManager();
	
	
	public static ConnectionManager getInstance(){
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/incubus","root", "s33how1tgo3s");
		
		return conn;
		
	}
	

}
