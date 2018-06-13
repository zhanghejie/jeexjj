/****************************************************
 * Description: Class的反射取静态值或执行静态方法
 * @author      zhanghejie
 * @version     1.0
**************************************************/
package com.xjj.framework.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.xjj.framework.utils.StringUtils;


public class StaticLoader {
	
	public static StaticLoader loader = new StaticLoader();
	private Map<String,Object> classMap = new HashMap<String,Object>();

	private StaticLoader(){}
	
	public static  StaticLoader getInstance(){
		return loader;
	}
	
	/**
	 * 获得静态属性的值
	 * @param className
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public  Object getValue(String className, String propertyName) throws Exception{
		
		if(StringUtils.isBlank(className) || StringUtils.isBlank(propertyName)){
			return null;
		}
		
		Object clazz = classMap.get(className);
		if(null == clazz){
			clazz =  Class.forName(className);
			classMap.put(className, clazz);
		}
		
		Object field = classMap.get(className+"@"+propertyName);
		if (null == field){
			field = ((Class<?>)clazz).getField(propertyName);
			classMap.put(className+"@"+propertyName,field);
		}
		
		Object propertyValue = ((Field)field).get(clazz);
		return propertyValue;
	} 
	
	/**
	 * 执行静态方法
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object excuteMethod(String className,String methodName, Object ... args) throws Exception{
		
		
		if(StringUtils.isBlank(className) || StringUtils.isBlank(methodName)){
			throw new Exception("类名或方法名错误！");
		}
		
		Object clazz = classMap.get(className);
		if(null == clazz){
			clazz = Class.forName(className);
			classMap.put(className, clazz);
		}
		
		Object method  = classMap.get(className+"#"+methodName);
		if(null == method){
			Class<?>[] arg_clazzs = null;
			if(args != null && args.length > 0){
				arg_clazzs = new Class<?>[args.length];
				for(int i=0;i<args.length;i++){
					arg_clazzs[i] = args[i].getClass();
				}
			}
			method  = ((Class<?>)clazz).getMethod(methodName, arg_clazzs);
			classMap.put(className+"#"+methodName,method);
		}
		
		if(method != null){
			return ((Method)method).invoke(method, args);
		}
		return null;
	}
}