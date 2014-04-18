package com.excilys.dao;

import com.excilys.data.QCompany;
import com.excilys.data.QComputer;
import com.excilys.util.OrderDirection;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.data.Computer;
import com.excilys.util.SearchWrapper;


@Repository
public class ComputerDAO extends DAO<Computer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    public ComputerDAO(){
		setLogger(LOGGER);
	}	
		
    private void setSearchQuery(JPQLQuery query,SearchWrapper<Computer> sw){
        QComputer computer = QComputer.computer;
        QCompany company = QCompany.company;
        query.leftJoin(computer.company,company);
        if(sw.getQuery() != null){
            query.where(computer.name.like("%"+sw.getQuery()+"%").or(company.name.like("%"+sw.getQuery()+"%")));
        }
        if(sw.getOrderCol() != null){
            if(sw.getOrderDirection() == OrderDirection.desc){
                query.orderBy(sw.getOrderCol().toQuery().desc());
            }
            else{
                query.orderBy(sw.getOrderCol().toQuery().asc());
            }
        }
    }

    private void setPageQuery(JPQLQuery query,SearchWrapper<Computer> sw){
        query.offset((sw.getPage() - 1) * sw.getNbComputersPerPage());
        query.limit(sw.getNbComputersPerPage());

    }

	@Override
	public void create(SearchWrapper<Computer> sw)  {
		getSessionFactory().getCurrentSession().save(sw.getItems().get(0));
	}

	@Override
	public void retrieve(SearchWrapper<Computer> sw) {
        QComputer computer = QComputer.computer;
        JPQLQuery query = new HibernateQuery(getSessionFactory().getCurrentSession());
        query.from(computer);
        if(sw.getItems().size() == 1){//Retrieve by id
            sw.setItem(query.where(computer.id.eq(sw.getItem().getId())).uniqueResult(computer));
        }
		else{//Retrieve All
            setSearchQuery(query,sw);
            setPageQuery(query,sw);
            sw.setItems(query.list(computer));
            count(sw);
        }
	}

	@Override
	public void count(SearchWrapper<Computer> sw) {
        QComputer computer = QComputer.computer;
        JPQLQuery query = new HibernateQuery(getSessionFactory().getCurrentSession());
        query.from(computer);
        setSearchQuery(query, sw);
		sw.setCount((int) query.count());
		
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
