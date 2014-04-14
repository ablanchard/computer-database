package com.excilys.data;

import javax.servlet.http.HttpServletRequest;

public class FormInput {
	private String title;
	private String name;
	
	public FormInput(String name,String title){
		this.title = title;
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue(HttpServletRequest request){
		return request.getParameter(getName());
	}
	

}
