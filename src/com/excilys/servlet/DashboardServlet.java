package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.ComputerService;
import com.excilys.service.Service;
import com.excilys.service.ServiceException;

/**
 * Servlet implementation class DashboardServlet
 */
public class DashboardServlet extends ComputerServlet {
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
	public static final String ATTR_TABLE_HEADERS = "tableHeaders";
	
	public static final String TABLE_HEADER_NAME = "Computer name";
	public static final String TABLE_HEADER_INTRO = "Introduced Date";
	public static final String TABLE_HEADER_DISC = "Discontinued Date";
	public static final String TABLE_HEADER_COMPANY = "Company";
	public static final String TABLE_HEADER_ACTIONS  = "Actions";
	
	public static final String PATH = "/index" ;
	public static final String JSP = "/WEB-INF/dashboard.jsp";

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);
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
		SearchWrapper<Computer> sw = new SearchWrapper<Computer>(nbComputersPerPage);
		
		if(request.getAttribute(ATTR_WRAPPER) == null){
			String search = request.getParameter(ATTR_SEARCH);
			String order = request.getParameter(ATTR_ORDER_COL);
			String direction = request.getParameter(ATTR_ORDER_DIRECTION);
			String page = request.getParameter(ATTR_PAGE);
			
			
			//LOGGER.info("Request parameters : Search : {}, Order : {}, Direction : {}",search,order,direction);
			
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
		} else {
			sw = (SearchWrapper<Computer>) request.getAttribute(ATTR_WRAPPER);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(ATTR_WRAPPER, sw);
		
		try {
			
		
			ComputerService.getInstance().retrieve(sw);
					
			int pageMax = (int)Math.floor(sw.getCount()/nbComputersPerPage);
			
			if (sw.getCount()%nbComputersPerPage != 0)
				pageMax++;
			
			sw.setPageMax(pageMax);
			
			
			request.setAttribute(ATTR_WRAPPER, ComputerMapper.toComputerDTOWrapper(sw));
		} catch (ServiceException e){
			request.setAttribute(ATTR_ERROR, Service.SERVICE_ERROR);
		}
		//Headers
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header(TABLE_HEADER_NAME,OrderComputerCol.name.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_INTRO).setOrderName(OrderComputerCol.intro.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_DISC).setOrderName(OrderComputerCol.disc.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_COMPANY).setOrderName(OrderComputerCol.company.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_ACTIONS));
		
		request.setAttribute(ATTR_TABLE_HEADERS, headers);
		
		
		ServletContext ctx = getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher(JSP);
		rd.forward(request, response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public static void setAttrAsLastPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		SearchWrapper sw = (SearchWrapper) session.getAttribute(DashboardServlet.ATTR_WRAPPER);
		
		request.setAttribute(DashboardServlet.ATTR_WRAPPER, sw);		
		
	
	}



}
