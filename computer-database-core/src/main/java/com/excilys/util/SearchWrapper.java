package com.excilys.util;

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
	
	public SearchWrapper(int nbItemsPerPage,Integer page,String search,String order,String direction){
		this.nbComputersPerPage = nbItemsPerPage;
		items = new ArrayList<E>();
		this.update(page, search, order, direction);
	}
	
	public SearchWrapper(SearchWrapper<?> sw){
		this.nbComputersPerPage = sw.nbComputersPerPage;
		this.query = sw.query;
		this.orderCol = sw.orderCol;
		this.orderDirection = sw.orderDirection;
		this.page = sw.page;
		this.items = new ArrayList<E>();
	}
	
	
	
	public String toString(){
		StringBuilder res = new StringBuilder();
		if(getItems().size() == 1){
			return getItems().get(0).toString();
		}
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

	public void updatePageMax(){
		int pageMax = (int)Math.floor(this.getCount()/nbComputersPerPage);
		
		if (this.getCount()%nbComputersPerPage != 0)
			pageMax++;
		
		this.setPageMax(pageMax);
	}
	
	public E getItem(){
		if(this.getItems().size() == 0)
			return null;
		return this.getItems().get(0);
	}
	
	public void setItem(E e){
		items.removeAll(getItems());
		items.add(0,e);
	}
	
	public void update(Integer page,String search,String order,String direction){
		if(search != null){
			if(!search.equals("")){
				this.setQuery(search);
			}
		}
		
		if(order != null){
			try {
				this.setOrderCol(OrderComputerCol.valueOf(order));
			} catch(IllegalArgumentException e){
				
			}
		}
		
		if(direction !=null){
			try {
				this.setOrderDirection(OrderDirection.valueOf(direction));
			}catch(IllegalArgumentException e){
				
			}
		}
		
		if(page != null){
			if(page <= 0){
				page = 1;
			}
			this.setPage(page);
		}
	}

}
