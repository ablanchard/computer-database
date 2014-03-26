package com.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public static final ThreadLocal<Connection> thLocal = new ThreadLocal<Connection>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);
	
	static {
		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException  e) {
			LOGGER.error(e.getMessage(), e.getCause());
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
			LOGGER.error(e.getMessage(), e.getCause());
		} // setup the connection pool
	}
	
	private DatabaseHandler(){
		initPool();
	}
	
	public static DatabaseHandler getInstance(){
		if(INSTANCE == null){
			INSTANCE = new DatabaseHandler();
		}
		return INSTANCE;
	}
	
	public Connection getConnectionFromPool(){
		try {
			LOGGER.debug("Connexions libres : {}",connectionPool.getTotalFree());
			Connection c = connectionPool.getConnection();
			c.setAutoCommit(AUTO_COMMIT); // fetch a connection
			return c;
			//return DriverManager.getConnection(DATABASE_URL + DATABASE_NAME + DATABASE_PARAMETER,DATABASE_USER,DATABASE_PASS);
		} catch (SQLException e) {
			
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	public void set(Connection cn){
		thLocal.set(cn);
	}
	
	public void unset(){
		thLocal.remove();
	}
	
	public Connection getConnection(){
		if(thLocal.get() == null)
			set(getConnectionFromPool());
		return (Connection) thLocal.get();
	}
	

	


	
	
	

}
