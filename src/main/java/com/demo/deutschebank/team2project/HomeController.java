package com.demo.deutschebank.team2project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    
    @GetMapping("/wel")
    public String wel(){
    	System.out.println("Execute ");
        return "welcome";
    }
}
