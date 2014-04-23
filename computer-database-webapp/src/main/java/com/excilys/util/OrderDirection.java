package com.excilys.util;

import org.springframework.data.domain.Sort;

public enum OrderDirection {
	asc,
	desc ;

    public Sort.Direction toDirection(){
        switch(this){
            case desc:
                return Sort.Direction.DESC;
            default:
                return Sort.Direction.ASC;
        }
    }
}
