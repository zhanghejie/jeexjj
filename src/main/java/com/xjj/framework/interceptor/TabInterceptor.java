package com.xjj.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xjj.framework.utils.StringUtils;

public class TabInterceptor implements HandlerInterceptor  {

	private static final String TAB_PRE = "tab_";
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//接收tabId
		String tabId = request.getParameter("tabId");
		if(!StringUtils.isBlank(tabId) && null!=modelAndView)
		{
			modelAndView.addObject("tabId", tabId.replace(TAB_PRE,""));
		}
		
		//接收nav导航信息
		String navs = request.getParameter("navs");
		if(!StringUtils.isBlank(navs) && null!=modelAndView)
		{
			modelAndView.addObject("navArr", navs.split(","));
		}
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}   

}