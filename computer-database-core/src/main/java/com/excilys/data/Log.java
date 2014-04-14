package com.excilys.data;

import org.joda.time.DateTime;



public class Log {
	private int id;
	private DateTime executedOn;
	private String target;
	private String command = "";
	private Operation operation;
	
	public String toString(){
		return getTarget() +  " " + getOperation();
	}
	public int getId() {
		return id;
	}
	public Log setId(int id) {
		this.id = id;
		return this;
	}
	public String getTarget() {
		return target;
	}
	public Log setTarget(String target) {
		this.target = target;
		return this;
	}
	public String getCommand() {
		return command;
	}
	public Log setCommand(String command) {
		this.command = command;
		return this;
	}
	public Operation getOperation() {
		return operation;
	}
	public Log setOperation(Operation operation) {
		this.operation = operation;
		return this;
	}
	
	public static Log build(){
		return new Log();
	}
	public DateTime getExecutedOn() {
		return executedOn;
	}
	public void setExecutedOn(DateTime executedOn) {
		this.executedOn = executedOn;
	}
}
