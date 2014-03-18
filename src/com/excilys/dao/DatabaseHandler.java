package com.excilys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.servlet.EditComputerServlet;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


//Singleton
public class DatabaseHandler {
	private static DatabaseHandler INSTANCE = null;
	private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_NAME = "computer-database-db";
	private static final String DATABASE_PARAMETER = "?zeroDateTimeBehavior=convertToNull";
	private static final String DATABASE_USER = "jee-cdb";
	private static final String DATABASE_PASS = "password";
	private static final boolean AUTO_COMMIT = false;

	private BoneCP connectionPool = null;
	
	final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);
	
	static {
		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void initPool(){

		// setup the connection pool
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(DATABASE_URL +  DATABASE_NAME + DATABASE_PARAMETER); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
		config.setUsername(DATABASE_USER); 
		config.setPassword(DATABASE_PASS);
		config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);
		try {
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // setup the connection pool
	}
	
	private DatabaseHandler(){
		initPool();
	}
	
	public static synchronized DatabaseHandler getInstance(){
		if(INSTANCE == null){
			INSTANCE = new DatabaseHandler();
		}
		return INSTANCE;
	}
	
	public Connection getConnection(){
		try {
			logger.info("Connexions libres : {}",connectionPool.getTotalFree());
			Connection c = connectionPool.getConnection();
			c.setAutoCommit(AUTO_COMMIT);; // fetch a connection
			return c;
			//return DriverManager.getConnection(DATABASE_URL + DATABASE_NAME + DATABASE_PARAMETER,DATABASE_USER,DATABASE_PASS);
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	


	
	
	

}
