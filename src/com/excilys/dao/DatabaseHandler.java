package com.excilys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


//Singleton
public class DatabaseHandler {
	private static DatabaseHandler INSTANCE = null;
	private static String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static String DATABASE_NAME = "computer-database-db";
	private static String DATABASE_USER = "root";
	private static String DATABASE_PASS = "root";
	
	private DatabaseHandler(){
		
	}
	
	public static synchronized DatabaseHandler getInstance(){
		if(INSTANCE == null){
			INSTANCE = new DatabaseHandler();
		}
		return INSTANCE;
	}
	
	public Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			return DriverManager.getConnection(DATABASE_URL + DATABASE_NAME,DATABASE_USER,DATABASE_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*public PreparedStatement getPreparedStatement(String query){
		Connection cn = null;
		PreparedStatement ps=null;
		cn = getConnection();
		try {
			return cn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
