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
@RequestMapping("/label")
@Controller
public class LabelControllerp {
	
	@RequestMapping("/detail")
    public String getLabelDetail(){
        return "label/detail";
    }

}
