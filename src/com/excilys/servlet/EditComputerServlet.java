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
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOException;
import com.excilys.mapper.ComputerMapper;
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
		ComputerDTO computerDTO ;
		
		//Le champ dto n'est pas présent => premier access au formulaire
		if(request.getAttribute("dto") == null){
			int computerId = Integer.valueOf(request.getParameter("id"));
			
			SearchWrapper<Computer> computerWrap = new SearchWrapper<Computer>(Computer.builder().setId(computerId));
			ComputerService.getInstance().retrieve(computerWrap);
			Computer computer = computerWrap.getItems().get(0);
			
			if(computer.getCompany() == null)
				computer.setCompany(Company.build().setId(0));
			
		
			computerDTO = ComputerMapper.toComputerDTO(computer);

		}
		else {
			//Le champ est présent => nouvel access apres avoir fait une faute lors du doPost
			computerDTO = (ComputerDTO) request.getAttribute("dto");
		}
		
		//Recupération de la liste des companies
		SearchWrapper<Company> sw = new SearchWrapper<Company>();
		CompanyService.getInstance().retrieve(sw);	
		List<Company> companies = sw.getItems();
		
		//Ajout de la company d'id 0
		companies.add(0, Company.build().setName("No company").setId(0));
		
		
		request.setAttribute("companies", companies);		
		request.setAttribute("dto", computerDTO);
		
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/editComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = ComputerDTO.build().setName(request.getParameter("name"))
				.setIntroducedDate(request.getParameter("introducedDate"))
				.setDiscontinuedDate(request.getParameter("discontinuedDate"))
				.setCompanyId(Integer.valueOf(request.getParameter("company")))
				.setId(Integer.valueOf(request.getParameter("id")));
		
		Computer c;
		
		try{
			c = ComputerMapper.toComputer(dto);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(c);
			ComputerService.getInstance().update(sw);
			
			
			request.setAttribute("success", "Computer successfully edited.");
			
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
