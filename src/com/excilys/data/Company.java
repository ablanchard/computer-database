package com.excilys.data;

public class Company {
	private int id;
	private String name;
	
	public Company(String name){
		this.name = name;
	}
	
	private Company(){
		
	}

	public int getId() {
		return id;
	}

	public Company setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Company setName(String name) {
		this.name = name;
		return this;
	}
	
	public static Company build(){
		return new Company();
	}
	

}
