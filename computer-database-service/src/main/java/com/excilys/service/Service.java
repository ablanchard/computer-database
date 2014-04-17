package com.excilys.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.DAO;
import com.excilys.dao.DaoException;
import com.excilys.dao.LogDAO;
import com.excilys.data.Log;
import com.excilys.util.Operation;
import com.excilys.util.SearchWrapper;


@Component
@Transactional
public abstract class Service<E> {

	Logger LOGGER ;
		
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
	

	@Transactional(propagation = Propagation.REQUIRED)
	private void operation(SearchWrapper<E> sw,Operation op) throws ServiceException, NotExistException {
		Log log = Log.build().setTarget(getDao().getTABLE()).setOperation(op);
		SearchWrapper<Log> swLog = new SearchWrapper<Log>(log);
		try{
			getLogger().debug("Start transaction");
			daoOperation(sw, op);
			swLog.getItems().get(0).setCommand(sw.toString());
			logDAO.create(swLog);
			getLogger().debug("Transaction \"{} {}\" commited",op,sw);
		} catch (DaoException e){
			LOGGER.debug("DaoException during the transaction \"{} {}\"",op,sw);
			throw new NotExistException();
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
	
		
	public Logger getLogger(){
		return LOGGER;
	}
	
	protected void setLogger(Logger LOGGER) {
		this.LOGGER = LOGGER;
	}
	
	public LogDAO getLogDAO() {
		return logDAO;
	}
	
	
	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}
	
	protected abstract DAO<E> getDao();

}
