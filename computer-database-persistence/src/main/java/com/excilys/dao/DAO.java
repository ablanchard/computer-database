package com.excilys.dao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.util.Operation;
import com.excilys.util.OrderDirection;
import com.excilys.util.SearchWrapper;



@Repository
@Transactional(propagation = Propagation.REQUIRED)
public abstract class DAO<E>  {
	
	public String TABLE ;
	
	Logger LOGGER ;
	

    @Autowired
    private SessionFactory sessionFactory;
	
	public void create(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.create);
	}
	
	public void retrieve(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.retrieve);
	}
	
	public void count(SearchWrapper<E> sw)  throws DaoException  {
		operation(sw, Operation.count);
	}
	
	public void update(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.update);
	}
	public void delete(SearchWrapper<E> sw) throws DaoException {
		operation(sw, Operation.delete);
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	private void operation(SearchWrapper<E> sw,Operation op)  {
		
		getLogger().debug("Try {} \"{}\"",op,sw);
		
		getLogger().debug("{} suceed",op);
		if(op == Operation.retrieve){
			operation(sw,Operation.count);
		}
			
		
		
	}
	
	public String getTABLE() {
		return TABLE;
	}

	protected void setTABLE(String tABLE) {
		TABLE = tABLE;
	}
	
	public Logger getLogger(){
		return LOGGER;
	}
	
	protected void setLogger(Logger LOGGER){
		this.LOGGER = LOGGER;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	

}
