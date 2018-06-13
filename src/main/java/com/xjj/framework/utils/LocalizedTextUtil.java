package com.xjj.framework.utils;

import javax.servlet.http.HttpServletRequest;

import com.xjj.framework.spring.LocalizedTextContext;

public class LocalizedTextUtil {

	/**
	 * 私有构造函数，放置实例化
	 */
	private LocalizedTextUtil(){}
	
	/**
	 * 返回资源文件的类
	 * @param request
	 * @return
	 */
	public static LocalizedTextContext getLocalizedText(HttpServletRequest request){
		return new LocalizedTextContext(request);
	}
	
	/**
	 * 返回资源文件对应的本地化值
	 * @param request
	 * @param text
	 * @param args
	 * @return
	 */
	public static String getText(HttpServletRequest request, String text){
		return new LocalizedTextContext(request).getText(text);
	}
	
	/**
	 * 返回资源文件对应的本地化值
	 * @param request
	 * @param text
	 * @param args
	 * @return
	 */
	public static String getText(HttpServletRequest request, String text, String... args){
		return new LocalizedTextContext(request).getText(text, args);
	}
}
