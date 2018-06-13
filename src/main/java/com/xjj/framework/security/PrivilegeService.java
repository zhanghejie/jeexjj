/****************************************************
 * Description: PrivilegeService for 权限
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      xjj
 * @version     1.0
**************************************************/

package com.xjj.framework.security;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjj.framework.security.annotations.SecCreate;
import com.xjj.framework.security.annotations.SecDelete;
import com.xjj.framework.security.annotations.SecEdit;
import com.xjj.framework.security.annotations.SecFunction;
import com.xjj.framework.security.annotations.SecFunctions;
import com.xjj.framework.security.annotations.SecList;
import com.xjj.framework.security.annotations.SecPrivilege;
import com.xjj.framework.security.annotations.SecPrivileges;
import com.xjj.framework.security.annotations.SecView;
import com.xjj.framework.security.dto.Function;
import com.xjj.framework.security.dto.Privilege;
import com.xjj.framework.utils.StringUtils;

public class PrivilegeService{
	
	//权限缓存
	public static PrivilegeContext privilegeContext = new PrivilegeContext();
	
	//单例权限service
	private static PrivilegeService privilegeService = new PrivilegeService();
	
	/**
	 * 权限初始化
	 */
	public static void init()
	{
		privilegeService.initPrivilege();
	}
	
	
	public static void main(String[] args) {
		init();
		System.out.println("================结束了=====");
	}
	
	/**
	 * 根据code获得权限对象
	 * @param code
	 * @return
	 */
	public static Privilege getPrivilege(String code){
		if(null == code || "".equals(code))
		{
			return null;
		}
		return privilegeContext.getPrivilege(code);
	}
	
	public static Collection<Privilege> getPrivileges(){
		return privilegeContext.getPrivileges();
	}
	
	/**
	 * 根据权限code和操作code获得操作（function）
	 * @param privilegeCode
	 * @param code
	 * @return
	 */
	public static Function getFunction(String privilegeCode,String code)
	{
		Privilege privilege = privilegeContext.getPrivilege(privilegeCode);
		if(privilege == null){
			return null;
		}
		return privilege.getFunction(code);
	}
	
	
	
	/**
	 * 权限初始化
	 */
	private void initPrivilege()
	{
		/*
		 * 得到所有的继承了Controller的类；
		 * 查找每个类里面的方法；
		 * 
		 */
		System.out.println("=====================缓存权限开始==========================");
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		classSet.addAll(getClasses("com.xjj"));
		//通过配置文件来读取需要扫描的包名
	
		
		
		//过滤出所有的controller
		List<Class<?>> controllerList = new ArrayList<Class<?>>();
		for (Class<?> clazz : classSet) {
			if(clazz.getAnnotation(Controller.class) != null)
			{
				controllerList.add(clazz);
			}
		}
		
		//过滤出所有的privilege，并设置到缓存map中
		Method[] methods = null;
		SecPrivilege secPrivilege = null;
		SecFunction secFunction = null;
		SecPrivileges secPrivileges = null;
		SecFunctions secFunctions = null;
		
		SecCreate secCreate = null;
		SecDelete secDelete = null;
		SecEdit secEdit = null;
		SecList secList = null;
		SecView secView = null;
		
		RequestMapping clazzMapping = null;
		RequestMapping methodMapping = null;
		
		String defaultCode = null;//权限默认编码
		String privilegeCode = null;
		String url = null;
		for (Class<?> clazz : controllerList) {
			clazzMapping = clazz.getAnnotation(RequestMapping.class);
			if(clazzMapping != null){
				defaultCode = getDefaultCode(clazzMapping);
			}
			methods = clazz.getMethods();
			
			if(null != methods && methods.length>0)
			{
				for (Method method : methods) {
					//得到对应的标注
					methodMapping = method.getAnnotation(RequestMapping.class);
					secPrivilege =  method.getAnnotation(SecPrivilege.class);
					secPrivileges = method.getAnnotation(SecPrivileges.class);
					
					url = getMethodURL(clazzMapping,methodMapping);
					
					//增加权限
					if(secPrivileges != null && secPrivileges.value() != null && secPrivileges.value().length > 0){
						if(secPrivilege == null){
							secPrivilege = secPrivileges.value()[0];
						}
						for(SecPrivilege sp : secPrivileges.value()){

							String title=sp.title();
							String code=(StringUtils.isBlank(sp.code()) ? defaultCode : sp.code());
							addPrivilege(title,code, url);
						}
						//添加默认的功能
						//获得默认权限
						privilegeCode = (secPrivilege == null || StringUtils.isBlank(secPrivilege.code()))  ? defaultCode : secPrivilege.code();
						addFunction(null, null, privilegeCode, SecurityConstants.SECURITY_DEFAULT_TITLE, SecurityConstants.SECURITY_DEFAULT_CODE, url,null);
					}else if(secPrivilege != null){
						String title=secPrivilege.title();
						String code=(StringUtils.isBlank(secPrivilege.code()) ? defaultCode : secPrivilege.code());
						addPrivilege(title,code, url);
						//添加默认的功能
						//获得默认权限
						addFunction(null, null, privilegeCode, SecurityConstants.SECURITY_DEFAULT_TITLE, SecurityConstants.SECURITY_DEFAULT_CODE, url,null);
					}
					
					
				}
			}
		}
		
		for (Class<?> clazz : controllerList) {
			clazzMapping = clazz.getAnnotation(RequestMapping.class);
			if(clazzMapping != null){
				defaultCode = getDefaultCode(clazzMapping);
			}
			methods = clazz.getMethods();
			
			if(null != methods && methods.length>0)
			{
				for (Method method : methods) {
					
					//得到对应的标注
					methodMapping = method.getAnnotation(RequestMapping.class);
					secFunction = method.getAnnotation(SecFunction.class);
					secFunctions = method.getAnnotation(SecFunctions.class);
					secCreate = method.getAnnotation(SecCreate.class);
					secDelete = method.getAnnotation(SecDelete.class);
					secEdit = method.getAnnotation(SecEdit.class);
					secList = method.getAnnotation(SecList.class);
					secView = method.getAnnotation(SecView.class);
					//获得默认权限
					if(secPrivilege == null && secPrivileges != null && secPrivileges.value() != null && secPrivileges.value().length > 0){
						secPrivilege = secPrivileges.value()[0];
					}
					privilegeCode = (secPrivilege == null || StringUtils.isBlank(secPrivilege.code()))  ? defaultCode : secPrivilege.code();
					url = getMethodURL(clazzMapping,methodMapping);
					//增加功能
					if(secFunctions != null && secFunctions.value() != null && secFunctions.value().length > 0){
						for(SecFunction sf : secFunctions.value()){
							addFunction(sf.privileges(), sf.privilege(), privilegeCode, sf.title(), sf.code(), url,sf.depend());
						}
					}
					if(secFunction != null){
						addFunction(secFunction.privileges(), secFunction.privilege(), privilegeCode, secFunction.title(), secFunction.code(), url,secFunction.depend());
					}
					if(secCreate != null){
						addFunction(null, secCreate.privilege(), privilegeCode, secCreate.title(), secCreate.code(), url,secCreate.depend());
					}
					if(secDelete != null){
						addFunction(null, secDelete.privilege(), privilegeCode, secDelete.title(), secDelete.code(), url,secDelete.depend());
					}
					if(secEdit != null){
						addFunction(null, secEdit.privilege(), privilegeCode, secEdit.title(), secEdit.code(), url,secEdit.depend());
					}
					if(secList != null){
						addFunction(null, secList.privilege(), privilegeCode, secList.title(), secList.code(), url,secList.depend());
					}
					if(secView != null){
						addFunction(null, secView.privilege(), privilegeCode, secView.title(), secView.code(), url,secView.depend());
					}
					
				}
			}
		}
		
		//整理功能的依赖关系
		for(Privilege p : privilegeContext.getPrivileges()){
			if(p.getFunctions() == null || p.getFunctions().size() == 0){
				continue;
			}
			for(Function f : p.getFunctions()){
				if(f.getDependCodes() == null || f.getDependCodes().length == 0){
					continue;
				}
				for(String c : f.getDependCodes()){
					if( c==null || "".equals(c)){
						continue;
					}
					int pos = c.indexOf("_");
					if(pos > -1){
						String pc = c.substring(0,pos);
						String fc = c.substring(pos,c.length());
						Privilege _p = privilegeContext.getPrivilege(pc);
						if(_p == null){
							continue;
						}
						Function _f = _p.getFunction(fc);
						f.addDepend(_f);
					}else{
						Function _f = p.getFunction(c);
						f.addDepend(_f);
					}
				}
			}
		}
		
		Collection<Privilege> preColl = privilegeContext.getPrivileges();
		
		for (Iterator<Privilege> iterator = preColl.iterator(); iterator.hasNext();) {
			Privilege privilege = (Privilege) iterator.next();
			System.out.println(privilege.getCode()+"==="+privilege.getTitle());
			System.out.println(privilege.getFunctions());
		}
		System.out.println("=====================缓存权限结束==========================");
	}
	
	/**
	 * 通过RequestMapping的参数得到默认的权限编码，将/转化成_处理
	 * @param mapping
	 * @return
	 */
	private String getDefaultCode(RequestMapping mapping){
		if(mapping == null || mapping.value() == null || mapping.value().length == 0){
			return "";
		}
		String code = mapping.value()[0];
		if(code.startsWith("/")){
			code = code.substring(1);
		}
		if(code.endsWith("/")){
			code = code.substring(0, code.length() - 1);
		}
		return code.replaceAll("/", "_");
	}
	
	/**
	 * 得到方法的路径
	 * @param clazzMapping
	 * @param methodMapping
	 * @return
	 */
	private  String getMethodURL(RequestMapping... mappings){
		StringBuilder sb = new StringBuilder();
		for(RequestMapping mapping : mappings){
			if(mapping != null && mapping.value() != null && mapping.value().length > 0){
				sb.append(mapping.value()[0]);
			}
		}
		return sb.toString();
	}
	/**
	 * 添加权限
	 * @param title
	 * @param code
	 * @param url
	 * @return
	 */
	private boolean addPrivilege(String title,String code,String url){
		Privilege privilege = new Privilege();
		privilege.setCode(code);
		privilege.setTitle(title);
		privilege.setUrl(url);
		return privilegeContext.addPrivilege(privilege);
	}
	/**
	 * 添加功能
	 * @param privilege
	 * @param title
	 * @param code
	 * @param resource
	 * @return
	 */
	private boolean addFunction(Privilege privilege,String title,String code,String url,String[] dependCodes){
		Function function = new Function();
		function.setPrivilege(privilege);
		function.setTitle(title);
		function.setCode(code);
		function.addURL(url);
		function.setDependCodes(dependCodes);
		return privilegeContext.addFunction(function);
	}
	
	/**
	 * 添加功能
	 * @param privilegeCode
	 * @param title
	 * @param code
	 * @param resource
	 * @return
	 */
	private boolean addFunction(String privilegeCode,String title,String code,String resource,String[] dependCodes){
		Privilege privilege = privilegeContext.getPrivilege(privilegeCode);
		if(privilege == null){
			return false;
		}
		return addFunction(privilege, title, code, resource, dependCodes);
	}
	
	/**
	 * 添加功能
	 * @param secFunction
	 * @param defaultCode
	 * @param title
	 * @param code
	 * @param resource
	 * @return
	 */
	private boolean addFunction(String[] privileges,String privilege,String defaultCode, String title,String code,String resource,String[] dependCodes){
		if(privileges != null && privileges.length > 0&&!"".equals(privileges[0])){
			for(String pcode : privileges){
				if(pcode != null && !"".equals(pcode)){
					addFunction(pcode, title, code, resource,dependCodes);
				}
			}
			return true;
		}
		if(privilege != null && !"".equals(privilege)){
			addFunction(privilege, title, code, resource,dependCodes);
			return true;
		}
		if(defaultCode != null && !"".equals(defaultCode)){
			addFunction(defaultCode, title, code, resource,dependCodes);
			return true;
		}
		return false;
	}
	/** 
     * 从包package中获取所有的Class 
     * @param pack 
     * @return 
     */ 
	private Set<Class<?>> getClasses(String packageName){ 
        //第一个class类的集合 
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>(); 
        //是否循环迭代 
        boolean recursive = true; 
        //获取包的名字 并进行替换  
        String packageDirName = packageName.replace('.', '/'); 
        //定义一个枚举的集合 并进行循环来处理这个目录下的things 
        Enumeration<URL> dirs; 
        try { 
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName); 
            //循环迭代下去 
            while (dirs.hasMoreElements()){ 
                //获取下一个元素 
                URL url = dirs.nextElement(); 
                //得到协议的名称 
                String protocol = url.getProtocol(); 
                //如果是以文件的形式保存在服务器上 
                if ("file".equals(protocol)) { 
                    //获取包的物理路径 
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8"); 
                    //以文件的方式扫描整个包下的文件 并添加到集合中 
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes); 
                } else if ("jar".equals(protocol)){ 
                    //如果是jar包文件  
                    //定义一个JarFile 
                    JarFile jar; 
                    try { 
                        //获取jar 
                        jar = ((JarURLConnection) url.openConnection()).getJarFile(); 
                        //从此jar包 得到一个枚举类 
                        Enumeration<JarEntry> entries = jar.entries(); 
                        //同样的进行循环迭代 
                        while (entries.hasMoreElements()) { 
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件 
                            JarEntry entry = entries.nextElement(); 
                            String name = entry.getName(); 
                            //如果是以/开头的 
                            if (name.charAt(0) == '/') { 
                                //获取后面的字符串 
                                name = name.substring(1); 
                            } 
                            //如果前半部分和定义的包名相同 
                            if (name.startsWith(packageDirName)) { 
                                int idx = name.lastIndexOf('/'); 
                                //如果以"/"结尾 是一个包 
                                if (idx != -1) { 
                                    //获取包名 把"/"替换成"." 
                                    packageName = name.substring(0, idx).replace('/', '.'); 
                                } 
                                //如果可以迭代下去 并且是一个包 
                                if ((idx != -1) || recursive){ 
                                    //如果是一个.class文件 而且不是目录 
                                    if (name.endsWith(".class") && !entry.isDirectory()) { 
                                        //去掉后面的".class" 获取真正的类名 
                                        String className = name.substring(packageName.length() + 1, name.length() - 6); 
                                        try { 
                                            //添加到classes 
                                        	//System.out.println(packageName + '.' + className);
                                            classes.add(Class.forName(packageName + '.' + className)); 
                                        } catch (Throwable e) { 
                                        	e.printStackTrace();
                                        } 
                                      } 
                                } 
                            } 
                        } 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    }  
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        
        return classes; 
    } 	
	
	/** 
     * 从包package中获取所有的Class 
     * @param pack 
     * @return 
     */ 
	@SuppressWarnings("unused")
	private Set<Class<?>> getClasses(Package pack){ 
       
        //获取包的名字 并进行替换 
        String packageName = pack.getName(); 
       return getClasses(packageName);
    } 
     
    /** 
     * 以文件的形式来获取包下的所有Class 
     * @param packageName 
     * @param packagePath 
     * @param recursive 
     * @param classes 
     */ 
    private void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes){ 
        //获取此包的目录 建立一个File 
        File dir = new File(packagePath); 
        //如果不存在或者 也不是目录就直接返回 
        if (!dir.exists() || !dir.isDirectory()) { 
            return; 
        } 
        //如果存在 就获取包下的所有文件 包括目录 
        File[] dirfiles = dir.listFiles(new FileFilter() { 
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件) 
              public boolean accept(File file) { 
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class")); 
              } 
            }); 
        //循环所有文件 
        for (File file : dirfiles) { 
            //如果是目录 则继续扫描 
            if (file.isDirectory()) { 
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), 
                                      file.getAbsolutePath(), 
                                      recursive, 
                                      classes); 
            } 
            else { 
                //如果是java类文件 去掉后面的.class 只留下类名 
                String className = file.getName().substring(0, file.getName().length() - 6); 
                
                if(null==className || !className.contains("Controller"))
                {
                	continue;
                }
                try { 
                    //添加到集合中去 
                	System.out.println("className======"+packageName + '.' + className);
                    classes.add(Class.forName(packageName + '.' + className)); 
                } catch (Exception e) { 
                	
                	
                	System.out.println("className======"+packageName+className);
                	System.out.println("className======"+packageName+className);
                    //e.printStackTrace(); 
                } 
            } 
        } 
    } 

	

}