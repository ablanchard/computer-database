package com.excilys.util;

public enum Operation {
	create,
	retrieve,
	update,
	delete,
	count;
	
	public int toInt(){
		switch(this){
		case create:
			return 0;
		case retrieve:
			return 1;
		case update:
			return 2;
		case delete:
			return 3;

		case count:
			return 4;
		}
		return -1;
	}
	
	public Operation valueOf(int id){
		switch(id){
		case 0:
			return create;
		case 1:
			return retrieve;
		case 2:
			return update;
		case 3:
			return delete;
		case 4:
			return count;
		}
		return null;
	}
	
	

}
