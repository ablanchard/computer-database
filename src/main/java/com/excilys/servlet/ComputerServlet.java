package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

public abstract class ComputerServlet extends HttpServlet  {

	public static final String ATTR_SUCCESS = "success";
	public static final String ATTR_ERROR = "error";
	public static final String ATTR_DTO = "dto";
	public static final String ATTR_COMPANIES = "companies";
	public static final String ATTR_ID = "id";
	
	 
	public static final String FORM_ATTRS = "form_attrs";
	public static final String FORM_ERRORS = "form_errors";

	public static final String NO_COMPANY_DEFAULT_NAME = "No company";

	
	
	protected void backToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DashboardServlet.setAttrAsLastPage(request, response);
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher(DashboardServlet.PATH);
		rd.forward(request, response);
	}


	public ComputerService getComputerService() {
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());  // (1) get ctx using the WebApplicationContextUtils class

		return (ComputerService ) ctx.getBean("computerService");  // (2) I have a service factory class that retrieves the beans for me.

	}



	public CompanyService getCompanyService() {
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());  // (1) get ctx using the WebApplicationContextUtils class

		return (CompanyService ) ctx.getBean("companyService");  // (2) I have a service factory class that retrieves the beans for me.

	}


	



}
