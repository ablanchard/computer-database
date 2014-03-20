package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DAO;
import com.excilys.dao.DaoException;
import com.excilys.dao.DatabaseHandler;
import com.excilys.dao.LogDAO;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.data.Log;
import com.excilys.data.Operation;

public abstract class Service<E> {
	
	protected DAO<E> dao ;

	final Logger logger = LoggerFactory.getLogger(Service.class);
	
	public void retrieve(SearchWrapper<E> sw){
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.retrieve);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.retrieve(sw,cn);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog,cn);
			cn.commit();
		} catch (DaoException | SQLException e){
			try {
				cn.rollback();
				logger.info("Rollbacked !");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
		
	}
	
	public void create(SearchWrapper<E> sw){
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.create);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.create(sw,cn);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog,cn);
			cn.commit();
		} catch (DaoException | SQLException e){
			try {
				cn.rollback();
				logger.info("Rollbacked !");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
		
	}
	public void update(SearchWrapper<E> sw){
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.update);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.update(sw,cn);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog,cn);
			cn.commit();
		} catch (DaoException | SQLException e){
			try {
				cn.rollback();
				logger.info("Rollbacked !");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
	}
	public void delete(SearchWrapper<E> sw){
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.delete);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.delete(sw,cn);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog,cn);
			cn.commit();
		} catch (DaoException | SQLException e){
			try {
				cn.rollback();
				logger.info("Rollbacked !");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			closeConnection(cn);
		}
	}
	
	public void closeConnection(Connection cn){
		if(cn!=null){
		try {
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	}

	public DAO<E> getDao() {
		return dao;
	}

	public void setDao(DAO<E> dao) {
		this.dao = dao;
	}
	

}
