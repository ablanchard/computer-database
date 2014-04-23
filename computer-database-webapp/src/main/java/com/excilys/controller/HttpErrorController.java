package com.excilys.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Controller
public class HttpErrorController {


    @RequestMapping(value="/error/404")
    public String handle404(){
        return "404";
    }
	

	@RequestMapping(value="/error/400")
	public String handle400(){
		return "400";
	}


    @RequestMapping(value="/error/401")
    public String handle401(){
        return "401";
    }
}
