package com.xjj.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xjj.framework.spring.LocalizedTextContext;
import com.xjj.framework.spring.StaticLoader;


public class I18NInterceptor implements HandlerInterceptor  {

	public static final String I18N_KEY = "localizedTextContext";
	public static final String STATIC_KEY = "static";
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//加载国际化资源文件 供ftl调用
		if(modelAndView != null && !modelAndView.getModel().containsKey(I18N_KEY)){
			modelAndView.addObject(I18N_KEY, LocalizedTextContext.getLocalizedText(request));
		}
		
		//加载类的静态属性和方法	供ftl调用
		if(modelAndView != null && !modelAndView.getModel().containsKey(STATIC_KEY)){
			modelAndView.addObject(STATIC_KEY, StaticLoader.getInstance());
		}
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}   

}