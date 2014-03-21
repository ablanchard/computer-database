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

	public static final String SERVICE_ERROR = "An error has occured while connecting to the server. Contact admin.";
	
	public void retrieve(SearchWrapper<E> sw) throws ServiceException {
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.retrieve);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.retrieve(sw);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog);
			cn.commit();
		} catch (DaoException | SQLException e) {
			rollbabk(cn);
			throw new ServiceException();
		} finally {
			closeConnection(cn);
		}
		
	}
	public void create(SearchWrapper<E> sw) throws ServiceException {
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.create);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.create(sw);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog);
			cn.commit();
		} catch (DaoException | SQLException e) {
			rollbabk(cn);
			throw new ServiceException();
		} finally {
			closeConnection(cn);
		}
		
	}
	public void update(SearchWrapper<E> sw) throws ServiceException, NotExistException {
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.update);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.update(sw);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog);
			cn.commit();
		} catch (SQLException e) {//throwed by commit
			rollbabk(cn);
			throw new ServiceException();
		} catch (DaoException e){//throwed by DAO
			rollbabk(cn);
			throw new NotExistException();
		} finally {
			closeConnection(cn);
		}
	}
	public void delete(SearchWrapper<E> sw) throws ServiceException, NotExistException {
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(Operation.delete);

		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			dao.delete(sw);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog);
			cn.commit();
		} catch (SQLException e) {//throwed by commit
			rollbabk(cn);
			throw new ServiceException();
		} catch (DaoException e){//throwed by DAO
			rollbabk(cn);
			throw new NotExistException();
		} finally {
			closeConnection(cn);
		}
	}
	
	public void closeConnection(Connection cn){
		if(cn!=null){
		try {
			cn.close();
			DatabaseHandler.getInstance().unset();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	}
	
	public void rollbabk(Connection cn){
		try {
			cn.rollback();
			logger.info("Rollbacked !");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public DAO<E> getDao() {
		return dao;
	}

	public void setDao(DAO<E> dao) {
		this.dao = dao;
	}
	

}
