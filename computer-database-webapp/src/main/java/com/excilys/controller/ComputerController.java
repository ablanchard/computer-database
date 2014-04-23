package com.excilys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Component
public abstract class ComputerController {
	
	public static final String ATTR_SUCCESS = "success";
	public static final String ATTR_ERROR = "error";
	public static final String ATTR_DTO = "dto";
	public static final String ATTR_COMPANIES = "companies";
	public static final String ATTR_ID = "id";
	 
	public static final String FORM_ATTRS = "form_attrs";
	public static final String FORM_ERRORS = "form_errors";

	public static final String NO_COMPANY_DEFAULT_NAME = "No company";
    public static final String SERVICE_ERROR = "service.error";
	
	@Autowired
	private ComputerService computerService ;
	
	@Autowired
	private CompanyService companyService ;
	
	@Autowired
	private ComputerMapper computerMapper;

	public ComputerService getComputerService() {
		return computerService;
	}

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ComputerMapper getComputerMapper() {
		return computerMapper;
	}

	public void setComputerMapper(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	} 

}
