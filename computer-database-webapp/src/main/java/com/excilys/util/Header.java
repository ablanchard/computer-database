package com.excilys.util;

import java.util.ArrayList;
import java.util.List;

public class Header {
	

	public static final String TABLE_HEADER_NAME = "computer.name";
	public static final String TABLE_HEADER_INTRO = "introduced.date";
	public static final String TABLE_HEADER_DISC = "discontinued.date";
	public static final String TABLE_HEADER_COMPANY = "company.name";
	public static final String TABLE_HEADER_ACTIONS  = "actions";
	
	
	String name;
	String orderName;
	String icon;
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
		headers.add(new Header(TABLE_HEADER_NAME,OrderComputerCol.name.toProperty()).setIcon("alpha"));
		headers.add(Header.build().setName(TABLE_HEADER_INTRO).setOrderName(OrderComputerCol.intro.toProperty()).setIcon("num"));
		headers.add(Header.build().setName(TABLE_HEADER_DISC).setOrderName(OrderComputerCol.disc.toProperty()).setIcon("num"));
		headers.add(Header.build().setName(TABLE_HEADER_COMPANY).setOrderName(OrderComputerCol.company.toProperty()).setIcon("alpha"));
		headers.add(Header.build().setName(TABLE_HEADER_ACTIONS));
		return headers ;
	}
	public String getIcon() {
		return icon;
	}
	public Header setIcon(String icon) {
		this.icon = icon;
		return this;
	}

}
