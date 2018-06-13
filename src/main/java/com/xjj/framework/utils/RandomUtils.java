/**
 * 
 */
package com.xjj.framework.utils;

import java.util.Random;

/**
 * @author xjj
 *
 */
public class RandomUtils {

	
	public static String genPassword(int length){
		String str = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		StringBuffer passwd = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			passwd.append(str.charAt(random.nextInt(82)));
		}
		return passwd.toString();
	}
	
	public static String getDefaultPassword(){
		return "888888";
	}
}
