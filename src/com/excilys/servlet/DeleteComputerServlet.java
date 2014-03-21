package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.NotExistException;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;

/**
 * Servlet implementation class DeleteComputerServlet
 */
public class DeleteComputerServlet extends ComputerServlet {
	private static final long serialVersionUID = 1L;

	public static final String SUCCESS_MESSAGE = "Computer successfully deleted.";
	

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
		int computerId = Integer.valueOf(request.getParameter(ATTR_ID));
		SearchWrapper<Computer> computerToDelete = new SearchWrapper<Computer>(Computer.builder().setId(computerId));
		
		try{
			ComputerService.getInstance().delete(computerToDelete);
			request.setAttribute(ATTR_SUCCESS, SUCCESS_MESSAGE);
		} catch (NotExistException e){
			request.setAttribute(ATTR_ERROR, ComputerService.NOT_EXIST);
		} catch (ServiceException e){
			request.setAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		
		backToIndex(request, response);
		
	}
	




}
