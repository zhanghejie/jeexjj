package com.xjj.framework.spring;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.xjj.SpringBeanLoader;
import com.xjj.framework.utils.StringUtils;

/**
 * 内部类，获得
 *
 */
public class LocalizedTextContext{
	private WebApplicationContext applicationContext = null;
	private Locale locale = null;
	
	public LocalizedTextContext(HttpServletRequest request){
		if(request == null){
			request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}
		if(request != null){
			this.applicationContext = (WebApplicationContext) request.getAttribute(RequestContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		}
		if (this.applicationContext == null) {
			this.applicationContext = (WebApplicationContext)SpringBeanLoader.getApplicationContext();
		}
		if (this.applicationContext == null && request != null) {
			this.applicationContext = RequestContextUtils.getWebApplicationContext(request);
		}
		
		if(request != null){
			// Determine locale to use for this RequestContext.
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver != null) {
				// Try LocaleResolver (we're within a DispatcherServlet request).
				this.locale = localeResolver.resolveLocale(request);
			} else {
				// No LocaleResolver available -> try fallback.
				this.locale = request.getLocale();
			}
		}
	}
	
	public static LocalizedTextContext getLocalizedText(){
		return new LocalizedTextContext(null);
	}
	
	public static LocalizedTextContext getLocalizedText(HttpServletRequest request){
		return new LocalizedTextContext(request);
	}
	
	public Locale getLocale(){
        if (locale == null) {
        	return Locale.getDefault();
        } 
        
        return locale;
	}
	
	public String getText(String text, String... args){
		if(StringUtils.isBlank(text)){
			return "";
		}
		if(applicationContext == null){
			return text;
		}
		
		if(args == null || args.length == 0){
			return getText(text);
		}
		
		String[] arglist = new String[args.length];
		for(int i=0; i<args.length; i++){
			if(args[i] != null){
				arglist[i] = getText(args[i]);
			}else{
				arglist[i] = "";
			}
		}
		
		return applicationContext.getMessage(text, arglist, getLocale());
	}
	
	public String getText(String text){
		if(StringUtils.isBlank(text)){
			return "";
		}
		if(applicationContext == null){
			return text;
		}
		return applicationContext.getMessage(text, null, getLocale());
	}
	
	public String getIntText(Integer key){
		return getText(key);
	}
	
	public String getText(Integer key){
		if(key == null){
			return "";
		}
		return getText(String.valueOf(key));
	}
	
	
	
	public String getText(int key){
		return getText(String.valueOf(key));
	}
	
	public String getLongText(Long key){
		return getText(key);
	}
	
	public String getText(Long key){
		if(key == null){
			return "";
		}
		return getText(String.valueOf(key));
	}
	
	public String getText(long key){
		return getText(String.valueOf(key));
	}
}
