package com.excilys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.servlet.EditComputerServlet;


//Singleton
public class DatabaseHandler {
	private static DatabaseHandler INSTANCE = null;
	private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_NAME = "computer-database-db";
	private static final String DATABASE_PARAMETER = "?zeroDateTimeBehavior=convertToNull";
	private static final String DATABASE_USER = "root";
	private static final String DATABASE_PASS = "root";

	final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);
	
	static {
		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
			
			return DriverManager.getConnection(DATABASE_URL + DATABASE_NAME + DATABASE_PARAMETER,DATABASE_USER,DATABASE_PASS);
					
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
