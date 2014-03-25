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
import com.excilys.service.Service;
import com.excilys.service.ServiceException;

/**
 * Servlet implementation class AddComputerServlet
 */
public class AddComputerServlet extends ComputerServlet {
	private static final long serialVersionUID = 1L;

	public static final String SUCCESS_MESSAGE = "Computer successfully added." ;
	public static final String JSP = "/WEB-INF/addComputer.jsp";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);
       
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
		try {
			SearchWrapper<Company> sw = new SearchWrapper<Company>();
			CompanyService.getInstance().retrieve(sw);
			
			List<Company> companies = sw.getItems();
			companies.add(0, Company.build().setName(NO_COMPANY_DEFAULT_NAME).setId(0));
			request.setAttribute(ATTR_COMPANIES, companies);
			
			request.setAttribute(FORM_ERRORS, ComputerForm.getErrors());
			request.setAttribute(FORM_ATTRS, ComputerForm.getInstance());
			
			ServletContext ctx = getServletContext();
			RequestDispatcher rd = ctx.getRequestDispatcher(JSP);
			rd.forward(request, response);
			
		} catch (ServiceException e){
			request.setAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
			backToIndex(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ComputerDTO dto = ComputerDTO.build().setName(ComputerForm.getInstance().getName().getValue(request))
				.setIntroducedDate(ComputerForm.getInstance().getIntro().getValue(request))
				.setDiscontinuedDate(ComputerForm.getInstance().getDisc().getValue(request))
				.setCompanyId(Integer.valueOf(ComputerForm.getInstance().getCompany().getValue(request)));
		Computer c;
		
		try{
			c = ComputerMapper.toComputer(dto);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(c);
			ComputerService.getInstance().create(sw);
			request.setAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE);
			
			backToIndex(request, response);
			
		} catch (DTOException e){
			
			request.setAttribute(ATTR_ERROR, e.getMessage());
			request.setAttribute(ATTR_DTO, dto);
			doGet(request,response);
			
		} catch (ServiceException e){
			request.setAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
			backToIndex(request, response);
		}
		
		
	}
	


}
