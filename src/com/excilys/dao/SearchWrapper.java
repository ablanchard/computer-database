package com.excilys.dao;

public class SearchWrapper {
	private String query;
	private OrderComputerCol orderCol;
	private OrderDirection orderDirection;
	private int page = 1;
	private int nbComputersPerPage;
	
	private SearchWrapper(int nbComputersPerPage){
		this.nbComputersPerPage = nbComputersPerPage;
	}
	
	public String getQuery() {
		return query;
	}
	public SearchWrapper setQuery(String query) {
		this.query = query;
		return this;
	}
	public OrderComputerCol getOrderCol() {
		return orderCol;
	}
	public SearchWrapper setOrderCol(OrderComputerCol orderCol) {
		this.orderCol = orderCol;
		return this;
	}
	public OrderDirection getOrderDirection() {
		return orderDirection;
	}
	public SearchWrapper setOrderDirection(OrderDirection orderDirection) {
		this.orderDirection = orderDirection;
		return this;
	}
	public int getPage() {
		return page;
	}
	public SearchWrapper setPage(int page) {
		this.page = page;
		return this;
	}
	
	public int getNbComputersPerPage() {
		return nbComputersPerPage;
	}

	public void setNbComputersPerPage(int nbComputersPerPage) {
		this.nbComputersPerPage = nbComputersPerPage;
	}

	public static SearchWrapper build(int nbComputersPerPage){
		return new SearchWrapper(nbComputersPerPage);
	}
	
	public String toString(){
		return "page : " + getPage() + " query : " + getQuery();
	}
	
}
