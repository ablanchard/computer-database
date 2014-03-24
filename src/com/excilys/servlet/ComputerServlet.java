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

public abstract class ComputerServlet extends HttpServlet {

	public static final String ATTR_SUCCESS = "success";
	public static final String ATTR_ERROR = "error";
	public static final String ATTR_DTO = "dto";
	public static final String ATTR_COMPANIES = "companies";
	public static final String ATTR_ID = "id";
	

	
	public static final String FORM_ATTRS = "form_attrs";
	

	public static final String NO_COMPANY_DEFAULT_NAME = "No company";
	
	
	protected void backToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DashboardServlet.setAttrAsLastPage(request, response);
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher(DashboardServlet.PATH);
		rd.forward(request, response);
	}
	
}
