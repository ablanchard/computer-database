package com.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


//Singleton
public class DatabaseHandler {
	private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_NAME = "computer-database-db";
	private static final String DATABASE_PARAMETER = "?zeroDateTimeBehavior=convertToNull";
	private static final String DATABASE_USER = "jee-cdb";
	private static final String DATABASE_PASS = "password";
	private static final boolean AUTO_COMMIT = false;

	
	DataSource ds;
	
	private ThreadLocal<Connection> thLocal = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);
	
	/*static {
		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException  e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
	}
	
	
	public void initPool(){
		// setup the connection pool
		Properties props = new Properties();
		BoneCPConfig config = new BoneCPConfig(props);
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
	}*/
	
	
	
	public Connection getConnectionFromPool(){
		try {
			//LOGGER.debug("Connexions libres : {}",.getTotalFree());
			Connection c = getDs().getConnection();
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
	/*
	public BoneCP getConnectionPool() {
		return connectionPool;
	}

	public void setConnectionPool(BoneCP connectionPool) {
		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.connectionPool = connectionPool;
	}*/

	public ThreadLocal<Connection> getThlocal() {
		return thLocal;
	}

	public void setThLocal(ThreadLocal<Connection> thLocal) {
		this.thLocal = thLocal;
	}

	public DataSource getDs() {
		return ds;
	}

	@Autowired
	public void setDs(DataSource ds) {
		this.ds = ds;
	}
	

	


	
	
	

}
