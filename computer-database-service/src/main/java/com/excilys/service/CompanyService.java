package com.excilys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dao.CompanyDAO;
import com.excilys.data.Company;
import com.excilys.util.SearchWrapper;

@Component
public class CompanyService extends Service<Company> {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyDAO dao = null;
	
	public CompanyService(){
		setLogger(LOGGER);
	}

	public CompanyDAO getDao() {
		return dao;
	}

	public void setDao(CompanyDAO dao) {
		this.dao = dao;
	}
	
	public List<Company> getAll() throws ServiceException {
		SearchWrapper<Company> sw = new SearchWrapper<Company>();
		try {
			retrieve(sw);
		} catch (NotExistException e) {
			
		}	
		List<Company> companies = sw.getItems();
		
		//Ajout de la company d'id 0
		companies.add(0, Company.build().setName("--"));
		return companies ;
	}

	public Company retrieveById(int id) throws ServiceException, NotExistException{
		SearchWrapper<Company> companyWrap = new SearchWrapper<Company>(new Company(id));
		retrieve(companyWrap);
		if(companyWrap.getItem() == null){
			LOGGER.debug("NotExistException !");
			throw new NotExistException();
		}
		else {
			return companyWrap.getItem();
		}
	}
	
	
	
	

}
