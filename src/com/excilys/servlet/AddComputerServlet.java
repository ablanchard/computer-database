package com.excilys.servlet;

import java.io.IOException;
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
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 */
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SearchWrapper<Company> sw = new SearchWrapper<Company>();
		CompanyService.getInstance().retrieve(sw);
		
		List<Company> companies = sw.getItems();
		companies.add(0, Company.build().setName("No company").setId(0));
		request.setAttribute("companies", companies);
		
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/addComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ComputerDTO dto = ComputerDTO.build().setName(request.getParameter("name"))
				.setIntroducedDate(request.getParameter("introducedDate"))
				.setDiscontinuedDate(request.getParameter("discontinuedDate"))
				.setCompanyId(Integer.valueOf(request.getParameter("company")));
		Computer c;
		
		try{
			c = ComputerMapper.toComputer(dto);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(c);
			ComputerService.getInstance().create(sw);
			request.setAttribute("success", "Computer successfully added.");
			
			ServletContext ctx = getServletContext();
			RequestDispatcher rd = ctx.getRequestDispatcher("/index");
			rd.forward(request, response);
			
		} catch (DTOException e){
			
			request.setAttribute("error", e.getMessage());
			request.setAttribute("dto", dto);
			doGet(request,response);
		}
		
		
	}


}
