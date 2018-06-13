package com.xjj.framework.web;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xjj.common.XJJConstants;
import com.xjj.framework.utils.StringUtils;

public class SpringControllerSupport {
protected final Logger logger = Logger.getLogger(getClass());
	
	private String basePath = "";
	
	/**
	 * 实例化时把类注解的地址赋予basePath
	 */
	public SpringControllerSupport(){
		RequestMapping annotation = getClass().getAnnotation(RequestMapping.class);
		
		if(annotation != null && annotation.value() != null && annotation.value().length > 0){
			this.basePath = annotation.value()[0];
		}
		
		for (int i = 0; i < annotation.value().length; i++) {
			System.out.println("====="+annotation.value()[i]);
		}
	}
	
	protected String getViewPath(String path){
		
		StringBuilder builder = new StringBuilder();
		builder.append(basePath).append("/");
		String className = getClass().getSimpleName();
		if(className.endsWith("Controller") && className.length() > 10){
			className = className.substring(0, className.length() - 10);
		}
		builder.append(StringUtils.toUnderScoreCase(className,"-")).append("-");
		if(path.startsWith("/")){
			builder.append(path.substring(1));
		}else{
			builder.append(path);
		}
		return builder.toString(); 
	}
	
	/**
	 * 得到管理员信息
	 * @return
	 */
	public ManagerInfo getManagerInfo() {
		return (ManagerInfo)getRequest().getSession().getAttribute(XJJConstants.SESSION_MANAGER_INFO_KEY);
	}

	public HttpServletRequest getRequest()
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

}
