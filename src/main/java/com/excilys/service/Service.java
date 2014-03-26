package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;

import com.excilys.dao.DAO;
import com.excilys.dao.DaoException;
import com.excilys.dao.DatabaseHandler;
import com.excilys.dao.LogDAO;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Log;
import com.excilys.data.Operation;

public abstract class Service<E> {
	
	protected DAO<E> dao ;

	Logger LOGGER ;

	public static final String SERVICE_ERROR = "An error has occured while connecting to the server. Contact admin.";
	
	public void retrieve(SearchWrapper<E> sw) throws ServiceException {
		try {
			operation(sw, Operation.retrieve);
		} catch (NotExistException e) {
			
		}
		
		
	}
	public void create(SearchWrapper<E> sw) throws ServiceException {
		try {
			operation(sw, Operation.create);
		} catch (NotExistException e) {
		}
		
	}
	public void update(SearchWrapper<E> sw) throws ServiceException, NotExistException {
		operation(sw, Operation.update);
	}
	public void delete(SearchWrapper<E> sw) throws ServiceException, NotExistException {
		operation(sw,Operation.delete);
	}
	
	private void operation(SearchWrapper<E> sw,Operation op) throws ServiceException, NotExistException {
		Log log = Log.build().setTarget(dao.getTABLE()).setOperation(op);
		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = DatabaseHandler.getInstance().getConnection();
		try{
			daoOperation(sw, op);
			swLog.getItems().get(0).setCommand(sw.toString());
			LogDAO.getInstance().create(swLog);
			cn.commit();
			getLogger().debug("Commited {} {}",op,sw);
		} catch (SQLException e) { //throwed by commit
			rollbabk(cn);
			throw new ServiceException();
		} catch (DaoException e){
			rollbabk(cn);
			throw new NotExistException();
		} finally {
			closeConnection(cn);
		}
	}
	
	private void daoOperation(SearchWrapper<E> sw,Operation op) throws DaoException{
		switch(op){
			case create :
				dao.create(sw);
				break;
			case retrieve:
				dao.retrieve(sw);
				break;
			case update:
				dao.update(sw);
				break;
			case delete:
				dao.delete(sw);
				break;
		default:
			break;
		}
	}
	
	public void closeConnection(Connection cn){
		if(cn!=null){
		try {
			cn.close();
			
			DatabaseHandler.getInstance().unset();
		} catch (SQLException e) {
			
			
			LOGGER.error(e.getMessage(), e.getCause());
		}}
	}
	
	public void rollbabk(Connection cn){
		try {
			cn.rollback();
			getLogger().debug("Rollbacked !");
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
	}
	public DAO<E> getDao() {
		return dao;
	}

	protected void setDao(DAO<E> dao) {
		this.dao = dao;
	}
	
	public Logger getLogger(){
		return LOGGER;
	}
	
	protected void setLogger(Logger LOGGER) {
		this.LOGGER = LOGGER;
	}
	
	
	
	

}
