package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dao.DAO;
import com.excilys.dao.DaoException;
import com.excilys.dao.DatabaseHandler;
import com.excilys.dao.LogDAO;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Log;
import com.excilys.data.Operation;


@Component
public abstract class Service<E> {

	Logger LOGGER ;
	
	@Autowired
	private DatabaseHandler dh = null;
	
	@Autowired
	private LogDAO logDAO = null;

	public static final String SERVICE_ERROR = "service.error";
	
	public void retrieve(SearchWrapper<E> sw) throws ServiceException, NotExistException {
			operation(sw, Operation.retrieve);
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
		Log log = Log.build().setTarget(getDao().getTABLE()).setOperation(op);
		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		Connection cn = getDh().getConnection();
		try{
			getLogger().debug("Start transaction");
			daoOperation(sw, op);
			swLog.getItems().get(0).setCommand(sw.toString());
			logDAO.create(swLog);
			cn.commit();
			getLogger().debug("Transaction commited {} {}",op,sw);
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
				getDao().create(sw);
				break;
			case retrieve:
				getDao().retrieve(sw);
				break;
			case update:
				getDao().update(sw);
				break;
			case delete:
				getDao().delete(sw);
				break;
		default:
			break;
		}
	}
	
	public void closeConnection(Connection cn){
		if(cn!=null){
		try {
			cn.close();
			
			dh.unset();
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
	
	public Logger getLogger(){
		return LOGGER;
	}
	
	protected void setLogger(Logger LOGGER) {
		this.LOGGER = LOGGER;
	}
	public DatabaseHandler getDh() {
		return dh;
	}
	
	
	public void setDh(DatabaseHandler dh) {
		this.dh = dh;
	}
	public LogDAO getLogDAO() {
		return logDAO;
	}
	
	
	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}
	
	protected abstract DAO<E> getDao();
}
