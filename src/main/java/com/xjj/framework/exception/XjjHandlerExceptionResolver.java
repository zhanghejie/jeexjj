package com.xjj.framework.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj.common.XJJConstants;
import com.xjj.framework.configuration.ConfigUtils;
import com.xjj.framework.json.XjjJson;
import com.xjj.framework.spring.LocalizedTextContext;
import com.xjj.framework.utils.MailUtils;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.ManagerInfo;

/**
 * 默认的处理异常的类
 * @author zhanghejie
 */
@Component
public class XjjHandlerExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = Logger.getLogger(XjjHandlerExceptionResolver.class);
	private boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
	/**
	 * 异常返回类型
	 * @author xjj
	 */
	public enum ReturnType { JSON, REDIRECT };
	
	private Properties viewList;//异常的显示页面
	private String view = "/common/exception/error";//默认的异常显示页面
	
	public Properties getViewList() {
		return viewList;
	}
	public void setViewList(Properties viewList) {
		this.viewList = viewList;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}


	/**
	 * 处理异常
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		//处理异常，这里分为三部分：
		//第一部分：不算是异常，只是一种处理方式，比如验证失败等
		//第二部分：已知的异常，需要重定向到制定的页面显示错误信息
		//第三部分：未知的异常，除了重定向到指定页面，还要发送邮件到制定邮箱
		boolean ret_json = false;
		boolean send_email = false;
		
		//处理错误信息
		if(ex instanceof ValidationException){
			//验证异常信息
			ret_json = ReturnType.JSON == ((ValidationException)ex).getReturnType();
			if(logger.isDebugEnabled()){
				ex.printStackTrace();
			}
		}else if(ex instanceof BusinessException){
			//业务异常信息
			ret_json = ReturnType.JSON == ((BusinessException)ex).getReturnType();
			if(logger.isDebugEnabled()){
				ex.printStackTrace();
			}
		}else if(ex instanceof PermissionException){
			//权限异常信息
			ret_json = ReturnType.JSON == ((PermissionException)ex).getReturnType();
			if(logger.isDebugEnabled()){
				ex.printStackTrace();
			}
		}else{
			//其他未知异常
			if(handler instanceof HandlerMethod){
				HandlerMethod handlerMethod = (HandlerMethod)handler;
				ret_json = handlerMethod.getMethodAnnotation(ResponseBody.class)!= null;
			}
			send_email = true;
			
			ex.printStackTrace();
		}
		
		if(ret_json){
			return jsonView(request,response,ex,send_email);
		}else{
			return redirectView(request,response,ex,send_email);
		}
	}
	

	private ModelAndView jsonView(HttpServletRequest request,HttpServletResponse response, Exception ex, boolean sendMail){
		response.setContentType("text/json; charset=utf-8");
		response.setContentType("application/json"); 
		try {
			response.getWriter().write(getJson(ex.getMessage()));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		if(sendMail){
			sendMail(ex);
		}
		ModelAndView modelAndView=new ModelAndView();
		//设置工程路径，给freemarker使用
		modelAndView.addObject("base",request.getContextPath());
		
		return new  ModelAndView();
	}
	
	private ModelAndView redirectView(HttpServletRequest request,HttpServletResponse response, Exception ex, boolean sendMail) {
		// 处理其他未知异常信息
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", ex.getMessage());
		model.put("stackTrace", getExceptionStackTrace(ex));
		model.put("ex", ex);
		if(sendMail){
			sendMail(ex);
		}
		ModelAndView modelAndView=new ModelAndView(findMatchingViewName(ex), model);
		//设置工程路径，给freemarker使用
		modelAndView.addObject("base",request.getContextPath());
		
		return modelAndView;
	}
	
	private String getJson(String message){
		XjjJson messageJson = XjjJson.error(message);
		try { 
			ObjectMapper mapper = new ObjectMapper(); 
			//mapper.set.setVisibility(JsonMethod.GETTER, Visibility.PUBLIC_ONLY);
			//mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true); 
			//mapper.setDateFormat( new SimpleDateFormat("yyyy-MM-dd"));
			String json = mapper.writeValueAsString(messageJson);
			return json;
		} catch (IOException e) { 
			e.printStackTrace();
		} 
		return "";
	}
	
	private String findMatchingViewName(Exception ex) {
		if(viewList == null || viewList.size() == 0){
			return view;
		}
		
		String viewName = null;
		String dominantMapping = null;
		int deepest = Integer.MAX_VALUE;
		for (Enumeration<?> names = viewList.propertyNames(); names.hasMoreElements();) {
			String name = (String) names.nextElement();
			int depth = getDepth(name, ex);
			if (depth >= 0 && depth < deepest) {
				deepest = depth;
				dominantMapping = name;
				viewName = viewList.getProperty(name);
			}
		}
		if (viewName != null && logger.isDebugEnabled()) {
			logger.debug("Resolving to view '" + viewName + "' for exception of type [" + ex.getClass().getName() + "], based on exception mapping [" + dominantMapping + "]");
		}
		if(viewName == null || StringUtils.isBlank(viewName)){
			viewName = view;
		}
		return viewName;
	}
	
	private int getDepth(String exceptionMapping, Exception ex) {
		return getDepth(exceptionMapping, ex.getClass(), 0);
	}

	private int getDepth(String exceptionMapping, Class<?> exceptionClass, int depth) {
		if (exceptionClass.getName().contains(exceptionMapping)) {
			// Found it!
			return depth;
		}
		// If we've gone as far as we can go and haven't found it...
		if (exceptionClass.equals(Throwable.class)) {
			return -1;
		}
		return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
	}
	
	private void sendMail(Exception ex){
		//debug模式下不发送邮件
		if(isDebug){
			return;
		}
		//如果是tomcat报的链接关闭异常则不发邮件
		if(ex instanceof SocketException ){
			return;
		}
		String subject = "系统异常-"+ex.getMessage();
		StringBuilder content = new StringBuilder("系统异常：").append(ex.getMessage()).append("<br>");
		try{
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			content.append("服务器名：").append(request.getServerName()).append("<br>");
			content.append("服务器端口：").append(request.getServerPort()).append("<br>");
			content.append("请求地址：").append(request.getRequestURI()).append("<br>");
			content.append("用户地址：").append(getIpAddr(request)).append("<br>");
			
			
			ManagerInfo info = (ManagerInfo)request.getSession().getAttribute(XJJConstants.SESSION_MANAGER_INFO_KEY);
			if(info != null){
				content.append("用户名：").append(info.getUserName()).append("<br>");
				content.append("登录名：").append(info.getLoginName()).append("<br>");
				content.append("用户Id：").append(info.getUserId()).append("<br>");
				content.append("用户类型：").append(info.getUserType()==null?"未知":LocalizedTextContext.getLocalizedText().getText(info.getUserType())).append("<br>");
			}
		}catch(Exception e){}
		
		content.append("<br><hr><br>").append(getExceptionStackTrace(ex));
		String to = ConfigUtils.get("mail.errorTo","");
		String cc = ConfigUtils.get("mail.errorCc","");
		MailUtils.sendHtmlMail(to, cc, null, subject, content.toString(), false);
	}
	
	private String getExceptionStackTrace(Exception ex){
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		try{
			ex.printStackTrace(printWriter);
			return stringWriter.toString();
		}finally{
			try {
				printWriter.close();
				stringWriter.close();
			} catch (IOException e) {}
		}
	}
	
	/**
     * 取得客户端ip
     * @param request HttpServletRequest对象
     * @return ip地址
     */
	private static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}