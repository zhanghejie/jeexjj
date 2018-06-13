package com.xjj.framework.utils;


/**
 * 内存检测工具
 * @author hulei
 * 2009-3-18
 *
 */
public class MemoryUtils {

	private static long freeMemory0;
	private static long freeMemory1;
	
	/**
	 * 检测内存使用情况,在要测试的内容前面添加MemoryUtils.testMemory0();
	 */
	public static void testMemory0(){
		freeMemory0= Runtime.getRuntime().freeMemory();
	}
	
	
	
	/**
	 * 检测内存使用情况,在要测试的内容后面添加MemoryUtils.testMemory1("参数中输入文字信息，提示本次测试的内容");
	 * 
	 * 例如：
	 * MemoryUtils.testMemory1("题库加载")
	 */
	public static void testMemory1(String info){
		freeMemory1=Runtime.getRuntime().freeMemory();
		long usedMemory= freeMemory0-freeMemory1;
		if(usedMemory >=10240){
			double um = usedMemory/1024;
			System.out.println(info + ",本次内存检测，占用去内存：" + um + "K");
		}else{
			System.out.println(info + ",本次内存检测，占用去内存：" + usedMemory + "byte");
		}
		
	}
	

}
