package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.dao.CompanyDAO;
import com.excilys.data.Company;

@Controller
public class CompanyService extends Service<Company> {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
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
