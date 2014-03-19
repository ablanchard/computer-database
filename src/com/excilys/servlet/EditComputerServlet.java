package com.excilys.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Company;
import com.excilys.data.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 */
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String error = "";
	final Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int computerId = Integer.valueOf(request.getParameter("id"));
		SearchWrapper<Company> sw = new SearchWrapper<Company>();
		CompanyService.getInstance().retrieve(sw);
		List<Company> companies = sw.getItems();
		companies.add(0, Company.build().setName("No company").setId(0));
		
		SearchWrapper<Computer> sw2 = new SearchWrapper<Computer>(Computer.builder().setId(computerId));
		ComputerService.getInstance().retrieve(sw2);
		Computer computer = sw2.getItems().get(0);
		if(computer.getCompany() == null)
			computer.setCompany(Company.build().setId(0));
		request.setAttribute("companies", companies);
		request.setAttribute("computer", computer);
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/editComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int computerId = Integer.valueOf(request.getParameter("id"));
		Computer computer = verify(request);
		
		if(computer != null){
			computer.setId(computerId);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(computer);
			ComputerService.getInstance().update(sw);
			response.sendRedirect("/computer-database/index");
		}
		else{
			request.setAttribute("error", error);
			doGet(request,response);
		}
	}
	
	private Computer verify(HttpServletRequest request) throws ServletException, IOException {
		//Non nullité et non vide
		String pName = request.getParameter("name");
		
		//Formatage
		String pIntroducedDate =  request.getParameter("introducedDate");
		String pDiscontinuedDate = request.getParameter("discontinuedDate");
		
		//Rien de spécifique
		String pCompanyId= request.getParameter("company");
		
		if(pName == null)
			return null;
		if(pName.equals("")){
			error="You must specify a name";
			return null;
		}
		
		Date introducedDate = null;
		Date discontinuedDate = null;
		if(pIntroducedDate != null){
			if(!pIntroducedDate.equals("")){
				try {	
					introducedDate = new SimpleDateFormat("yyyy-MM-dd").parse(pIntroducedDate);
				} catch (ParseException e) {
					error="Introduced Date format is invalid";
					return null;
				}
			}
		}
		if(pDiscontinuedDate != null){
			if(!pDiscontinuedDate.equals("")){
				try {
					discontinuedDate = new SimpleDateFormat("yyyy-MM-dd").parse(pDiscontinuedDate);
				} catch (ParseException e) {
					error="Discontinued Date format is invalid";
					return null;
				}
			}
		}
		
		
		int companyId = Integer.valueOf(pCompanyId);
		SearchWrapper<Company> sw = new SearchWrapper<Company>(Company.build().setId(companyId));
		
		CompanyService.getInstance().retrieve(sw);
		Company company = sw.getItems().get(0);
		return Computer.builder().setName(pName).setIntroduction(introducedDate).setDiscontinued(discontinuedDate).setCompany(company);
		
		
	}

}
