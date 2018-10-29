/**
 * 
 */
package com.bosh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangmt
 * @date 2018年9月26日
 */
@RequestMapping("/user")
@Controller
public class UserControllerp {
	
	@RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/register")
    public String register() {
        return "user/register";
    }

    @RequestMapping("/home")
    public String home() {
        return "user/home";
    }

    @RequestMapping("/set")
    public String setting() {
        return "user/set";
    }

    @RequestMapping("/seticon")
    public String seticon() {
        return "user/seticon";
    }

    @RequestMapping("/setpsw")
    public String setpsw() {
        return "user/setpsw";
    }

    @RequestMapping("/message")
    public String message() { 
    	return "user/message"; 
    }

}
