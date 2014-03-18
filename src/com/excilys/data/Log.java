package com.excilys.data;

import java.util.Date;

public class Log {
	private int id;
	private Date executedOn;
	private String target;
	private String command = "";
	private Operation operation;
	public int getId() {
		return id;
	}
	public Log setId(int id) {
		this.id = id;
		return this;
	}
	public Date getExecutedOn() {
		return executedOn;
	}
	public Log setExecutedOn(Date executedOn) {
		this.executedOn = executedOn;
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
}
