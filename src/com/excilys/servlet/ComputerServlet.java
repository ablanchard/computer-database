package com.excilys.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.data.Company;
import com.excilys.data.Computer;

/**
 * Servlet implementation class ComputerServlet
 */
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getServletPath();
		switch(context){
			case "/addComputer":
				form(request,response);
				break;
			default:
				listing(request,response);
				break;
		}
		//System.out.println(context);
		//System.out.println("Requete GET");
	}
	
	protected void listing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Computer> computers = ComputerDAO.getInstance().getAll();
		request.setAttribute("computers", computers);
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/dashboard.jsp");
		rd.forward(request, response);
	}
	
	protected void form(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = CompanyDAO.getInstance().getAll();
		request.setAttribute("companies", companies);
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/addComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String)request.getAttribute("action");
		System.out.println(action);
		switch(action){
			case "add":
				add(request,response);
				break;
			case "mod":
				mod(request,response);
				break;
			case "del":
				del(request,response);
				break;
		}
	}
	
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = (String) request.getAttribute("name");
		Date introducedDate = (Date) request.getAttribute("introducedDate");
		Date discontinuedDate = (Date) request.getAttribute("discontinuedDate");
		int company_id = (int) request.getAttribute("company");
		
	}
	
	protected void mod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
