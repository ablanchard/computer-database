package com.excilys.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.data.Company;
import com.excilys.util.SearchWrapper;

import java.util.List;

@Repository
public class CompanyDAO extends DAO<Company> {
	public static final String TABLE = "company";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_ID = "id";

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	
	public CompanyDAO(){
		setTABLE(TABLE);
		setLogger(LOGGER);
	}
	//Insertion
	
	//Suppresion
	
	//Modification
	
	//Selection
	@Override
	public void retrieve(SearchWrapper<Company> sw) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Company.class);
		if(sw.getItems().size() == 1){//Retrieve by id
            criteria.add(Restrictions.eq("id",sw.getItem().getId()));
			sw.setItem((Company) criteria.uniqueResult());
		}
		else{//Retrieve All
			sw.setItems((List<Company>)criteria.list());
		}
	}
}
