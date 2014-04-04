package com.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseHandler {
	
	private static final boolean AUTO_COMMIT = false;

	@Autowired
	DataSource ds;
	
	private ThreadLocal<Connection> thLocal = new ThreadLocal<Connection>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);
			
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

	public ThreadLocal<Connection> getThlocal() {
		return thLocal;
	}

	public void setThLocal(ThreadLocal<Connection> thLocal) {
		this.thLocal = thLocal;
	}

	
	public DataSource getDs() {
		return ds;
	}

	
	public void setDs(DataSource ds) {
		this.ds = ds;
	}
}
