package com.xjj.framework.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {   
    public static void main(String[] args) {   
    	/*
        try {  
            System.out.println("接口实现类：");   
            for (Class<?> c : getAllAssignedClass(Struts2Action.class)) {   
                System.out.println(c.getName());   
            }   
            System.out.println("子类：");   
            for (Class<?> c : getAllAssignedClass(Struts2ActionSupport.class)) {   
                System.out.println(c.getName());   
            } 
        } catch (ClassNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    	 */  
    }   
  
    /**  
     * 获取同一路径下所有子类或接口实现类  
     *   
     * @param intf  
     * @return  
     * @throws IOException  
     * @throws ClassNotFoundException  
     */  
    public static List<Class<?>> getAllAssignedClass(Class<?> cls) throws IOException,   
            ClassNotFoundException {   
        List<Class<?>> classes = new ArrayList<Class<?>>();   
        for (Class<?> c : getClasses(cls)) {   
            if (cls.isAssignableFrom(c) && !cls.equals(c)) {   
                classes.add(c);   
            }   
        }   
        return classes;   
    }   
  
    /**  
     * 取得当前类路径下的所有类  
     *   
     * @param cls  
     * @return  
     * @throws IOException  
     * @throws ClassNotFoundException  
     */  
    public static List<Class<?>> getClasses(Class<?> cls) throws IOException,   
            ClassNotFoundException {   
        String pk = "com.flare";//cls.getPackage().getName();   
        String path = pk.replace('.', '/');
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();   
        URL url = classloader.getResource(path);   
        return getClasses(new File(url.getFile()), pk);   
    }   
  
    /**  
     * 迭代查找类  
     *   
     * @param dir  
     * @param pk  
     * @return  
     * @throws ClassNotFoundException  
     */  
    private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {   
        List<Class<?>> classes = new ArrayList<Class<?>>();   
        if (!dir.exists()) {   
            return classes;   
        }   
        for (File f : dir.listFiles()) {   
            if (f.isDirectory()) {   
                classes.addAll(getClasses(f, pk + "." + f.getName()));   
            }   
            String name = f.getName();   
            if (name.endsWith(".class")) {   
                classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));   
            }   
        }   
        return classes;   
    }   
  
}  
