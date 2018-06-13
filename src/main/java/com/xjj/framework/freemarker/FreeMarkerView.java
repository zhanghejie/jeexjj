/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xjj.framework.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

/**
 * View using the FreeMarker template engine.
 *
 * <p>Exposes the following JavaBean properties:
 * <ul>
 * <li><b>url</b>: the location of the FreeMarker template to be wrapped,
 * relative to the FreeMarker template context (directory).
 * <li><b>encoding</b> (optional, default is determined by FreeMarker configuration):
 * the encoding of the FreeMarker template file
 * </ul>
 *
 * <p>Depends on a single {@link FreeMarkerConfig} object such as {@link FreeMarkerConfigurer}
 * being accessible in the current web application context, with any bean name.
 * Alternatively, you can set the FreeMarker {@link Configuration} object as bean property.
 * See {@link #setConfiguration} for more details on the impacts of this approach.
 *
 * <p>Note: Spring's FreeMarker support requires FreeMarker 2.3 or higher.
 *
 * @author Darren Davison
 * @author Juergen Hoeller
 * @since 03.03.2004
 * @see #setUrl
 * @see #setExposeSpringMacroHelpers
 * @see #setEncoding
 * @see #setConfiguration
 * @see FreeMarkerConfig
 * @see FreeMarkerConfigurer
 */
public class FreeMarkerView extends org.springframework.web.servlet.view.freemarker.FreeMarkerView {

	
	private static final String CONTEXT_PATH = "base";
	private static final String SERVLET_PATH = "servletPath";
	private static final String SERVLET_URL = "servletUrl";
	private static final String REFERER_PATH = "refererPath";
	
	
	@Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
		//设置工程路径，给freemarker使用
    	model.put(CONTEXT_PATH, request.getContextPath());
		
		//设置基本的url路径，不包括参数
		StringBuilder url = new StringBuilder();
		url.append(request.getServletPath()); //请求页面或其他地址
		model.put(SERVLET_PATH, url.toString());
		
		//设置完整的url路径，包括参数
		String param = request.getQueryString();
		if(param != null && !param.equals("")){
			url.append("?").append(param);
		}
		model.put(SERVLET_URL, url.toString());
		
		//设置上一次访问的页面路径
		String preUrl = request.getHeader("Referer");
		if(preUrl != null){
			model.put(REFERER_PATH, preUrl);
		}
        super.exposeHelpers(model, request);
    }
    
    
	/**
	 * Autodetect a {@link FreeMarkerConfig} object via the ApplicationContext.
	 * @return the Configuration instance to use for FreeMarkerViews
	 * @throws BeansException if no Configuration instance could be found
	 * @see #getApplicationContext
	 * @see #setConfiguration
	 */
	protected FreeMarkerConfig autodetectConfiguration() throws BeansException {

		FreeMarkerConfig config = super.autodetectConfiguration();
		ClassTemplateLoader ctl = new ClassTemplateLoader(this.getClass(), "/webapp/WEB-INF/view/");
		ClassTemplateLoader ctl1 = new ClassTemplateLoader(this.getClass(), "/webapp/WEB-INF/view/");
		TemplateLoader tl = config.getConfiguration().getTemplateLoader();
		TemplateLoader[] loaders = new TemplateLoader[] { tl, ctl, ctl1 };
		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
		config.getConfiguration().setTemplateLoader(mtl);

		return config;
	}


}
