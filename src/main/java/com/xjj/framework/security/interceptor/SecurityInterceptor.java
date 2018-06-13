/****************************************************
 * Description: 权限拦截器类
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      xjj
 * @version     1.0
**************************************************/
package com.xjj.framework.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xjj.common.XJJConstants;
//import com.xjj.framework.security.SecurityContext;
import com.xjj.framework.web.ManagerInfo;
import com.xjj.sec.entity.RoleEntity;

/**
 * 权限管理拦截器
 * @author xjj
 *
 */
public class SecurityInterceptor implements HandlerInterceptor  {
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//首先判断是否是Controller类，如果不是直接返回true，继续执行
		if(handler instanceof HandlerMethod){	
			ManagerInfo loginInfo =(ManagerInfo)request.getSession().getAttribute(XJJConstants.SESSION_MANAGER_INFO_KEY);
		
//			// 验证权限
//			if(!loginInfo.getLoginName().equals("admin")&&!SecurityContext.hasResource(loginInfo.getRoleCodes(), request)){
//				response.sendRedirect(request.getContextPath()+"/components/security/permission/no");
//				return false;
//			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}   
	
	
	@SuppressWarnings("unused")
	private boolean hasRole(RoleEntity[] roles, RoleEntity role){
		if(roles == null || roles.length==0 || role == null){
			return false;
		}
		for(RoleEntity r : roles){
			if(r.equals(role)){
				return true;
			}
		}
		return false;
	}

}