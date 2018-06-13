package com.xjj.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

	/**
	 * 利用反射取值
	 * @param obj
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("all")
	public static Object getValue(Object obj, String methodName){
		try{
			if(methodName.indexOf(".")>-1){
				String m1 = methodName.substring(0,methodName.indexOf("."));
				String m2 = methodName.substring(methodName.indexOf(".")+1);
				if(m1!=null && !m1.trim().equals("")){
					Method m = obj.getClass().getMethod("get"+String.valueOf(m1.charAt(0)).toUpperCase()+m1.substring(1), null);
					Object o = m.invoke(obj, null);
					return getValue(o,m2);
				}else{
					return getValue(obj,m2);
				}
			}else{
				if(methodName!=null && !methodName.trim().equals("")){
					Method m = obj.getClass().getMethod("get"+String.valueOf(methodName.charAt(0)).toUpperCase()+methodName.substring(1), null);
					Object o = m.invoke(obj, null);
					return o;
				}else{
					return obj;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Object();
	}
	
	
	public static Object convertEntity(Object src,Object desc)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Class<?> clazz = src.getClass();
		Class<?> descClazz = desc.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			invoke1(src,desc,clazz.getDeclaredMethods(), descClazz.getDeclaredMethods(),f.getName());
		}
		return desc;

	}

	@SuppressWarnings("all")
	private static void invoke1(Object src, Object desc, Method[] methods,
			Method[] descMethods, String name) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		String upperName = Character.toUpperCase(name.charAt(0))
				+ name.substring(1);
		String setterName = "set" + upperName;
		String getterName = "get" + upperName;
		Method method = null;
		Method descMethod = null;
		method = getMethodByName(methods, getterName);
		descMethod = getMethodByName(descMethods, setterName);
		if (method != null && descMethod != null) {
			descMethod.invoke(desc, method.invoke(src, null));
		}
	}

	private static Method getMethodByName(Method[] methods, String methodName) {
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}


}
