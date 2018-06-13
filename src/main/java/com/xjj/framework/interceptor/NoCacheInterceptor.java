package com.xjj.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 禁止浏览器缓存
 * @author zhanghejie
 *
 */
public class NoCacheInterceptor implements HandlerInterceptor  {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//不缓存动态内容
		if(handler instanceof HandlerMethod){
			response.addHeader("Pragma","No-cache"); 
			response.addHeader("Cache-Control","no-cache"); 
			response.addHeader("Cache-Control","no-store"); 
			response.addDateHeader("Expires", 0); 
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}   

}