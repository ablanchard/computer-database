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
import com.excilys.service.NotExistException;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;

/**
 * Servlet implementation class AddComputerServlet
 */
public class EditComputerServlet extends ComputerServlet {
	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
	public static final String JSP = "/WEB-INF/editComputer.jsp";
	public static final String SUCCESS_MESSAGE = "Computer successfully edited.";
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
		try{
			if(request.getAttribute(ATTR_DTO) == null){
				int computerId = Integer.valueOf(request.getParameter(ATTR_ID));
				
				SearchWrapper<Computer> computerWrap = new SearchWrapper<Computer>(Computer.builder().setId(computerId));
				ComputerService.getInstance().retrieve(computerWrap);
				
				if(computerWrap.getItems().size() == 0)
					throw new NotExistException(ComputerService.NOT_EXIST);
					
				Computer computer = computerWrap.getItems().get(0);
				
				if(computer.getCompany() == null)
					computer.setCompany(Company.build().setId(0));
				
			
				computerDTO = ComputerMapper.toComputerDTO(computer);
	
			}
			else {
				//Le champ est présent => nouvel access apres avoir fait une faute lors du doPost
				computerDTO = (ComputerDTO) request.getAttribute(ATTR_DTO);
			}
			
			//Recupération de la liste des companies
			SearchWrapper<Company> sw = new SearchWrapper<Company>();
			CompanyService.getInstance().retrieve(sw);	
			List<Company> companies = sw.getItems();
			
			//Ajout de la company d'id 0
			companies.add(0, Company.build().setName(NO_COMPANY_DEFAULT_NAME).setId(0));
			
			
			request.setAttribute(ATTR_COMPANIES, companies);		
			request.setAttribute(ATTR_DTO, computerDTO);
			
			ServletContext ctx = getServletContext();
			RequestDispatcher rd = ctx.getRequestDispatcher(JSP);
			rd.forward(request, response);
			
		} catch(NotExistException e){
			request.setAttribute(ATTR_ERROR, e.getMessage());
			backToIndex(request, response);
		} catch (ServiceException e){
			request.setAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
			backToIndex(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = ComputerDTO.build().setName(request.getParameter(FORM_ATTR_NAME))
				.setIntroducedDate(request.getParameter(FORM_ATTR_INTRO))
				.setDiscontinuedDate(request.getParameter(FORM_ATTR_DISC))
				.setCompanyId(Integer.valueOf(request.getParameter(FORM_ATTR_COMPANY)))
				.setId(Integer.valueOf(request.getParameter(FORM_ATTR_ID)));
		
		Computer c;
		
		try{
			c = ComputerMapper.toComputer(dto);
			SearchWrapper<Computer> sw = new SearchWrapper<Computer>(c);
			ComputerService.getInstance().update(sw);
			
			request.setAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE);
			backToIndex(request, response);
		} catch (DTOException e){
			
			request.setAttribute(ATTR_ERROR, e.getMessage());
			request.setAttribute(ATTR_DTO, dto);
			doGet(request,response);
			
		} catch(NotExistException e){
			
			request.setAttribute(ATTR_ERROR, ComputerService.NOT_EXIST);
			backToIndex(request, response);
			
		} catch (ServiceException e){
			request.setAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
			backToIndex(request, response);
		}
		
		
	}
	

	


}
