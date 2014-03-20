package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.SearchWrapper;
import com.excilys.data.Computer;
import com.excilys.service.ComputerService;

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
		SearchWrapper<Computer> computerToDelete = new SearchWrapper<Computer>(Computer.builder().setId(computerId));
		
		ComputerService.getInstance().retrieve(computerToDelete);
		if(computerToDelete.getItems().size() == 1){
			ComputerService.getInstance().delete(computerToDelete);
			request.setAttribute("success", "Computer successfully deleted.");
		}
		else{
			request.setAttribute("error", "This computer doesn't exist.");
		}
				
		HttpSession session = request.getSession();
		SearchWrapper sw = (SearchWrapper) session.getAttribute(DashboardServlet.ATTR_WRAPPER);
		
		request.setAttribute(DashboardServlet.ATTR_WRAPPER, sw);		
		
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/index");
		rd.forward(request, response);
		
	}



}
