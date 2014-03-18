package com.excilys.data;

public enum Operation {
	create,
	retrieve,
	update,
	delete;
	
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
		}
		return null;
	}
	
	

}
