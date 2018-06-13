package com.xjj.framework.utils;

import java.math.BigDecimal;

public class MathUtils {
        
	/**
	 * 四舍五入，默认保留两位
	 * @param d
	 * @return
	 */
	public static double roundHalfUp (double d) { 
	     return roundHalfUp(d,2); 
	}
	
	/**
	 * 四舍五入
	 * @param d
	 * @return
	 */
	public static double roundHalfUp (double d, int scale) { 
	     BigDecimal bd = new BigDecimal(d); 
	     bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP); 
	     return bd.doubleValue(); 
	}
	
	/**
	 * 四舍五入，默认保留两位
	 * @param f
	 * @return
	 */
	public static float roundHalfUp (float f) { 
	     return  roundHalfUp(f,2); 
	}
	
	/**
	 * 四舍五入
	 * @param f
	 * @param scale 精度
	 * @return
	 */
	public static float roundHalfUp (float f, int scale) { 
		double d = roundHalfUp((double)f,scale); 
	     return (float)d;
	}
   
	/**
	 * 四舍五入格式化
	 * @param f
	 * @param scale 精度
	 * @return
	 */
	public static String roundHalfUpFormat(float f, int scale) {
		return  String.format("%." + scale + "f", roundHalfUp(f,scale));
	}
	
	
	public static void main(String[] args){
		System.out.println( roundHalfUp(0.10500d,2));
		System.out.println( roundHalfUp(0.115000f,2));
		System.out.println( roundHalfUp(0.125000f,2));
		System.out.println( roundHalfUp(0.135000f,2));
		System.out.println( roundHalfUp(0.195000f,2));
	}
}
