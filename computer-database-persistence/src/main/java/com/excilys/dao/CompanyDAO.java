package com.excilys.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.data.Company;
import com.excilys.util.SearchWrapper;

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
	public void retrieve(SearchWrapper<Company> sw)
			 {
		
		if(sw.getItems().size() == 1){//Retrieve by id
			sw.setItem((Company) getSessionFactory().getCurrentSession().get(Company.class, sw.getItems().get(0).getId()));
		}
		else{//Retrieve All
			sw.setItems(getSessionFactory().getCurrentSession().createQuery("from Company").list());
		}
		
	}




	


	
}
