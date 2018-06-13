package com.xjj;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.xjj.common.DictConstants;
import com.xjj.framework.exception.XjjHandlerExceptionResolver;
import com.xjj.framework.freemarker.FreeMarkerView;
import com.xjj.framework.freemarker.FreemarkerStaticModels;
import com.xjj.framework.interceptor.I18NInterceptor;
import com.xjj.framework.interceptor.LoginInterceptor;
import com.xjj.framework.interceptor.TabInterceptor;
import com.xjj.framework.security.PrivilegeService;
import com.xjj.framework.security.SecurityCache;
import com.xjj.framework.web.support.QueryParameterMethodArgumentResolver;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter implements CommandLineRunner{
    
	/**
	 * 拦载器配置
	 */
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new TabInterceptor()).addPathPatterns("/**");       
        registry.addInterceptor(new I18NInterceptor()).addPathPatterns("/**");       
        registry.addInterceptor(new LocaleChangeInterceptor()).addPathPatterns("/**");
        
        InterceptorRegistration regist = registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
        regist.excludePathPatterns("/");
        regist.excludePathPatterns("/fore/**");
        regist.excludePathPatterns("/passport/**");
    }
    
    /*
     * 参数处理
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addArgumentResolvers(java.util.List)
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new QueryParameterMethodArgumentResolver());
    }
    
    /**
     * 定义FreeMarkerViewResolver
     * @return
     */
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
        resolver.setExposeSpringMacroHelpers(true);
        resolver.setViewClass(FreeMarkerView.class);
        resolver.setAttributesMap(getFreemarkerStaticModels());
        
        return resolver;
    }
    
    @Bean(name="freemarkerStaticModels")
    public FreemarkerStaticModels getFreemarkerStaticModels() {
    	FreemarkerStaticModels staticModels = FreemarkerStaticModels.getInstance();
    	Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("config/freemarkerstatic.properties");
        // 使用properties对象加载输入流
        try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	staticModels.setStaticModels(properties);
        return staticModels;
    }
    
    @Bean(name="exceptionHandler")
    public XjjHandlerExceptionResolver getExceptionHandler() {
    	XjjHandlerExceptionResolver  exceptionResolver = new XjjHandlerExceptionResolver();
        return exceptionResolver;
    }
    
    /**
     * 启动后执行
     */
    @Order(value=1)
   	@Override
   	public void run(String... args) throws Exception {
    	
    	System.out.println("==========系统缓存开始===============");
		//初始化权限
		PrivilegeService.init();
		//权限缓存
		SecurityCache.init();
		//初始化字典缓存
		DictConstants.init();
   		
		System.out.println("==========系统缓存结束===============");
   	}
}