package com.excilys.dao;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.data.Computer;
import com.excilys.util.SearchWrapper;

@Repository
public class ComputerDAO extends DAO<Computer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
    private static final String SEARCH_CLAUSE = " where c.name like :query or com.name like :query ";
    private static final String FROM_CLAUSE = "Computer c left join c.company com";

    public ComputerDAO(){
		setLogger(LOGGER);
	}	
		

	@Override
	public void create(SearchWrapper<Computer> sw)  {
		getSessionFactory().getCurrentSession().save(sw.getItems().get(0));
	}

	
	public Query getRetrieveQuery(SearchWrapper<Computer> sw) {
		StringBuilder queryHQL = new StringBuilder("select c from " + FROM_CLAUSE);


		if(sw.getQuery() != null){
			queryHQL.append(SEARCH_CLAUSE);
		}
			
		queryHQL.append(getOrderClause(sw));
		
		Query query = getSessionFactory().getCurrentSession().createQuery(queryHQL.toString());
		
		if(sw.getQuery() != null){
            query.setParameter("query", "%" + sw.getQuery() + "%");
		}
		
		query.setFirstResult((sw.getPage()-1)*sw.getNbComputersPerPage());
		query.setMaxResults(sw.getNbComputersPerPage());
		
		return query;
	}
	
	@Override
	public void retrieve(SearchWrapper<Computer> sw) {
				
		if(sw.getItems().size() == 1){//Retrieve by id
			sw.setItem((Computer) getSessionFactory().getCurrentSession().get(Computer.class, sw.getItems().get(0).getId()));
		}
		else{//Retrieve All
			sw.setItems(getRetrieveQuery(sw).list());
		}
		
		count(sw);
	}
	
	public Query getCountQuery(SearchWrapper<Computer> sw) {
		StringBuilder queryHQL = new StringBuilder("select count(*) from " + FROM_CLAUSE);


		if(sw.getQuery() != null){
			queryHQL.append(SEARCH_CLAUSE);
		}
			
		queryHQL.append(getOrderClause(sw));
		
		Query query = getSessionFactory().getCurrentSession().createQuery(queryHQL.toString());
		
		if(sw.getQuery() != null){
			query.setParameter("query", "%" + sw.getQuery() + "%");
		}
		
		
		return query;
	}


	@Override
	public void count(SearchWrapper<Computer> sw)
			 {
		sw.setCount(((Long)getCountQuery(sw).uniqueResult()).intValue());
		
	}


	@Override
	public void update(SearchWrapper<Computer> sw) {
		getSessionFactory().getCurrentSession().update(sw.getItems().get(0));
	}

	
	@Override
	public void delete(SearchWrapper<Computer> sw){
		getSessionFactory().getCurrentSession().delete(sw.getItems().get(0));
	}


}
