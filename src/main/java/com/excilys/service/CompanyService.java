package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.dao.CompanyDAO;
import com.excilys.data.Company;

public class CompanyService extends Service<Company> {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
	
	private CompanyDAO dao = null;
	
	private CompanyService(){
		setLogger(LOGGER);
	}

	public CompanyDAO getDao() {
		return dao;
	}

	public void setDao(CompanyDAO dao) {
		this.dao = dao;
	}

	
	
	

}
