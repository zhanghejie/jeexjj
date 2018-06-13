package com.xjj.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjj.framework.web.SpringControllerSupport;

@Controller
@RequestMapping("/") 
public class IndexController extends SpringControllerSupport{

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/passport/manager/login";
	}
	
	
}
