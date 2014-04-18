package com.excilys.dao;

import com.excilys.data.QCompany;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	
	public CompanyDAO(){
		setLogger(LOGGER);
	}
	//Insertion
	
	//Suppresion
	
	//Modification
	
	//Selection
	@Override
	public void retrieve(SearchWrapper<Company> sw) {
        QCompany company = QCompany.company;
        JPQLQuery query = new HibernateQuery(getSessionFactory().getCurrentSession());
        query.from(company);
		if(sw.getItems().size() == 1){//Retrieve by id
			sw.setItem(query.where(company.id.eq(sw.getItem().getId())).uniqueResult(company));
		}
		else{//Retrieve All
			sw.setItems(query.list(company));
		}
	}
}
