package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.data.Company;
import com.excilys.data.Computer;

/**
 * Servlet implementation class DeleteComputerServlet
 */
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int computerId = Integer.valueOf(request.getParameter("id"));
		ComputerDAO.getInstance().deleteById(computerId);
		HttpSession session = request.getSession();
		int page = Integer.valueOf((String)session.getAttribute("page"));
		response.sendRedirect("/computer-database/index?page="+page);
	}



}
