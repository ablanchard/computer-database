package com.excilys.servlet;

import java.util.ArrayList;
import java.util.List;

import com.excilys.dao.OrderComputerCol;

public class Header {
	

	public static final String TABLE_HEADER_NAME = "computer.name";
	public static final String TABLE_HEADER_INTRO = "introduced.date";
	public static final String TABLE_HEADER_DISC = "discontinued.date";
	public static final String TABLE_HEADER_COMPANY = "company.name";
	public static final String TABLE_HEADER_ACTIONS  = "actions";
	
	
	String name;
	String orderName;
	public String getName() {
		return name;
	}
	public Header setName(String name) {
		this.name = name;
		return this;
	}
	public String getOrderName() {
		return orderName;
	}
	public Header setOrderName(String orderName) {
		this.orderName = orderName;
		return this;
	}
	public Header(String name, String orderName){
		this.name = name;
		this.orderName = orderName;
	}
	public Header(){
		
	}
	
	public static Header build(){
		return new Header();
	}
	
	public static List<Header> getArray(){
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header(TABLE_HEADER_NAME,OrderComputerCol.name.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_INTRO).setOrderName(OrderComputerCol.intro.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_DISC).setOrderName(OrderComputerCol.disc.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_COMPANY).setOrderName(OrderComputerCol.company.toString()));
		headers.add(Header.build().setName(TABLE_HEADER_ACTIONS));
		return headers ;
	}

}
