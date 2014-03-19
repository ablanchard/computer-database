package com.excilys.servlet;

import java.io.IOException;

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
		SearchWrapper<Computer> sw1 = new SearchWrapper<Computer>(Computer.builder().setId(computerId));
		ComputerService.getInstance().delete(sw1);
		
		HttpSession session = request.getSession();
		SearchWrapper sw = (SearchWrapper) session.getAttribute("sw");
		
		String url = "";
		
		if(sw.getQuery() != null){
			url += "search=" + sw.getQuery();
		}
		
		if(sw.getOrderCol() != null){
			if(!url.equals(""))
				url += "&";
			url += "order=" + sw.getOrderCol();
		}
		
		if(sw.getOrderDirection() !=null){
			if(!url.equals(""))
				url += "&";
			url += "direction=" + sw.getOrderDirection();
		}
		
		if(sw.getPage() != 0){

			if(!url.equals(""))
				url += "&";
			url += "page=" + sw.getPage();
		}
		if(!url.equals(""))
			url = "?" + url;
		
		response.sendRedirect("/computer-database/index" + url);
	}



}
