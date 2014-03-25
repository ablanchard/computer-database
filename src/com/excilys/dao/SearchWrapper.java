package com.excilys.dao;

import java.util.ArrayList;
import java.util.List;

public class SearchWrapper<E> {
	private String query;
	private OrderComputerCol orderCol;
	private OrderDirection orderDirection;
	private int page = 1;
	private int nbComputersPerPage;
	private List<E> items;
	private int count = 0;
	private int pageMax;
	
	public SearchWrapper(E e){
		items = new ArrayList<E>();
		items.add(0, e);
	}
	
	public SearchWrapper(int nbItemsPerPage){
		this.nbComputersPerPage = nbItemsPerPage;
		items = new ArrayList<E>();
		
	}
	
	public SearchWrapper(){
		items = new ArrayList<E>();
	}
	
	
	
	public String toString(){
		StringBuilder res = new StringBuilder();
		if(getItems().size() == 1)
			return getItems().get(0).toString();
		else{
			res.append("Page " );
			res.append(getPage());
			if(getQuery() != null){
				res.append(" looking for \"" );
				res.append(getQuery() );
				res.append( "\"");
			}
			if(getOrderCol() != null){
				res.append(" order by ");
				res.append(getOrderCol());
				if(getOrderDirection() != null){
					res.append(" ");
					res.append(getOrderDirection());
				}
				else {
					res.append(" ASC ");
				}
			}
			return res.toString();
		}
	}
	
	
	public String getQuery() {
		return query;
	}
	public SearchWrapper<E> setQuery(String query) {
		this.query = query;
		return this;
	}
	public OrderComputerCol getOrderCol() {
		return orderCol;
	}
	public SearchWrapper<E> setOrderCol(OrderComputerCol orderCol) {
		this.orderCol = orderCol;
		return this;
	}
	public OrderDirection getOrderDirection() {
		return orderDirection;
	}
	public SearchWrapper<E> setOrderDirection(OrderDirection orderDirection) {
		this.orderDirection = orderDirection;
		return this;
	}
	public int getPage() {
		return page;
	}
	public SearchWrapper<E> setPage(int page) {
		this.page = page;
		return this;
	}
	
	public int getNbComputersPerPage() {
		return nbComputersPerPage;
	}

	public void setNbComputersPerPage(int nbComputersPerPage) {
		this.nbComputersPerPage = nbComputersPerPage;
	}




	public List<E> getItems() {
		return items;
	}

	public void setItems(List<E> items) {
		this.items = items;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageMax() {
		return pageMax;
	}

	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
	}


}
