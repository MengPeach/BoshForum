/**
 * 
 */
package com.bosh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangmt
 * @date 2018年9月27日
 */
@Controller
public class PageControllerp {
	
	@RequestMapping("/index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping("/label1")
    public String labelhome() {
        return "label/home";
    }

    @RequestMapping("/chat")
    public String chathome(){
        return "chat/home";
    }

}
