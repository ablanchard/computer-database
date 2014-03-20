package com.excilys.servlet;

public class Header {
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

}
