package com.xjj.framework.web.support;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class QueryParameterMethodArgumentResolver implements HandlerMethodArgumentResolver {

	public QueryParameterMethodArgumentResolver() {	}

	public boolean supportsParameter(MethodParameter parameter) {
		if(XJJParameter.class.isAssignableFrom(parameter.getParameterType())){
			return true;
		}
		return false;
	}

	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		XJJParameter queryParam = new XJJParameter();
		String query_name = getQueryName(parameter);
		String query_prefix = query_name.endsWith(".") ? query_name : query_name+".";
				
        for(String key : webRequest.getParameterMap().keySet()){
        	handlerParameter(key,webRequest.getParameter(key),queryParam);
//        	if(key.startsWith(query_prefix) && key.length() > query_prefix.length()){
//        	}
        }

        if (binderFactory != null) {
			WebDataBinder binder = binderFactory.createBinder(webRequest, null, query_prefix);
			queryParam = binder.convertIfNecessary(queryParam, XJJParameter.class, parameter);
		}
        
        //mavContainer.addAttribute(query_name, queryParam);
        
		return queryParam;
		
		
	}
	
	private void handlerParameter(String name,String value,XJJParameter queryParam){
		queryParam.addQuery(name, value);
	}
	
	/**
	 * 得到查询参数的前缀
	 * @param parameter
	 * @return
	 */
	private String getQueryName(MethodParameter parameter){
		String prefix = null;
		QueryParameter annotation = parameter.getParameterAnnotation(QueryParameter.class);
		if(annotation != null && annotation.value() != null){
			prefix = annotation.value();
		}else{
			prefix = QueryParameter.DEFAULT_QUERY_NAME;
		}
		return prefix;
	}
}
