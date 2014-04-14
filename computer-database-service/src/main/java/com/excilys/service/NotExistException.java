package com.excilys.service;

public class NotExistException extends Exception{

	public NotExistException(String message){
		super(message);
	}
	
	public NotExistException(){
		super();
	}
}
