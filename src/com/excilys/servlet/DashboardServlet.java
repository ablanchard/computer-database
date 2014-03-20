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

import com.excilys.dao.OrderComputerCol;
import com.excilys.dao.OrderDirection;
import com.excilys.dao.SearchWrapper;
import com.excilys.data.Computer;
import com.excilys.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 */
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int nbComputersPerPage= 15;
	public static final String ATTR_SEARCH = "search";
	public static final String ATTR_ORDER_COL = "order";
	public static final String ATTR_ORDER_DIRECTION = "direction";
	public static final String ATTR_COMPUTERS = "computers";
	public static final String ATTR_NB_COMPUTER = "nbComputers";
	public static final String ATTR_NB_COMPUTERS_PER_PAGE = "nbComputersPerPage";
	public static final String ATTR_PAGE_MAX = "pageMax";
	public static final String ATTR_PAGE = "page";
	public static final String ATTR_WRAPPER = "sw";

	final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String search = request.getParameter(ATTR_SEARCH);
		String order = request.getParameter(ATTR_ORDER_COL);
		String direction = request.getParameter(ATTR_ORDER_DIRECTION);
		String page = request.getParameter(ATTR_PAGE);
		SearchWrapper<Computer> sw = new SearchWrapper<Computer>(nbComputersPerPage);
		
		List<Computer> computers;
		logger.info("Request parameters : Search : {}, Order : {}, Direction : {}",search,order,direction);
		
		if(search != null){
			sw.setQuery(search);
		}
		
		if(order != null){
			sw.setOrderCol(OrderComputerCol.valueOf(order));
		}
		
		if(direction !=null){
			sw.setOrderDirection(OrderDirection.valueOf(direction));
		}
		
		if(page != null){
			sw.setPage(Integer.parseInt(page));
		}
		logger.info(sw.toString());
		request.setAttribute(ATTR_WRAPPER, sw);
		
		HttpSession session = request.getSession();
		session.setAttribute("sw", sw);
		
		ComputerService.getInstance().retrieve(sw);
		computers = sw.getItems();
		int nbComputers = sw.getCount();
		
		int pageMax = (int)Math.floor(nbComputers/nbComputersPerPage);
		
		if (nbComputers%nbComputersPerPage != 0)
			pageMax++;
		
		request.setAttribute(ATTR_COMPUTERS, computers);
		request.setAttribute(ATTR_NB_COMPUTER, nbComputers);
		request.setAttribute(ATTR_NB_COMPUTERS_PER_PAGE, nbComputersPerPage);
		request.setAttribute(ATTR_PAGE_MAX,pageMax);
		
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/dashboard.jsp");
		rd.forward(request, response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}
