package com.excilys.dao;

import com.excilys.util.OrderDirection;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.data.Computer;
import com.excilys.util.SearchWrapper;

import java.util.List;

@Repository
public class ComputerDAO extends DAO<Computer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    public ComputerDAO(){
		setLogger(LOGGER);
	}	
		
    private void setCriteria(Criteria criteria,SearchWrapper<Computer> sw,boolean count){
        criteria.createAlias("company", "com", JoinType.LEFT_OUTER_JOIN);
        if(sw.getQuery() != null){
            criteria.add(Restrictions.disjunction()
                    .add(Restrictions.like("name", "%" + sw.getQuery() + "%"))
                    .add(Restrictions.like("com.name", "%" + sw.getQuery() + "%")));
        }
        Order order = null;

        if(sw.getOrderCol() != null){
            if(sw.getOrderDirection() == OrderDirection.desc){
                order = Order.desc(sw.getOrderCol().toQuery());
            }
            else{
                order = Order.asc(sw.getOrderCol().toQuery());
            }
            criteria.addOrder(order);
        }
        if(!count) {
            criteria.setFirstResult((sw.getPage() - 1) * sw.getNbComputersPerPage());
            criteria.setMaxResults(sw.getNbComputersPerPage());
        }
    }

	@Override
	public void create(SearchWrapper<Computer> sw)  {
		getSessionFactory().getCurrentSession().save(sw.getItems().get(0));
	}

	@Override
	public void retrieve(SearchWrapper<Computer> sw) {
	    Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Computer.class);
		if(sw.getItems().size() == 1){//Retrieve by id
            criteria.add(Restrictions.eq("id", sw.getItem().getId()));
			sw.setItem((Computer) criteria.uniqueResult());
		}
		else{//Retrieve All
            setCriteria(criteria,sw,false);
			sw.setItems((List<Computer>) criteria.list());
            count(sw);
		}
		

	}

	@Override
	public void count(SearchWrapper<Computer> sw) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Computer.class);
        setCriteria(criteria,sw,true);
		sw.setCount(((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
		
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
