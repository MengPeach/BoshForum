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
@Controller
@RequestMapping("/posts")
public class PostsControllerp {
	
	@RequestMapping("/add")
    public String add(){
        return "posts/add";
    }

    @RequestMapping("/detail")
    public String detail() {
        return "posts/detail";
    }

}
