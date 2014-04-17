package com.excilys.dto;

public class ComputerDTO {
	private int id = -1;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private int companyId = -1;
	private String companyName;
	
	public int getId() {
		return id;
	}
	public ComputerDTO setId(int id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public ComputerDTO setName(String name) {
		this.name = name;
		return this;
	}
	public String getIntroducedDate() {
		return introducedDate;
	}
	public ComputerDTO setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
		return this;
	}
	public String getDiscontinuedDate() {
		return discontinuedDate;
	}
	public ComputerDTO setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
		return this;
	}
	public int getCompanyId() {
		return companyId;
	}
	public ComputerDTO setCompanyId(int companyId) {
		this.companyId = companyId;
		return this;
	}
	
	public static ComputerDTO build(){
		return new ComputerDTO();
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String toString(){
		return getId() + " " + getName() + " " +  getCompanyId() + " " + getCompanyName();
	}
	

}
