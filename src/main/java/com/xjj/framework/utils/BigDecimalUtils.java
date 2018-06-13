package com.xjj.framework.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

	/** 
	 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 
	 * 确的浮点数运算，包括加减乘除和四舍五入。 
	 */
	//默认除法运算精度 
	private static final int DEF_DIV_SCALE = 4;

	//这个类不能实例化 
	private BigDecimalUtils() {
	}

	/** 
	 * 提供精确的加法运算。 
	 * @param b1 被加数 
	 * @param b2 加数 
	 * @return 两个参数的和 
	 */
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b1.add(b2);
	}

	/** 
	 * 提供精确的减法运算。 
	 * @param b1 被减数 
	 * @param b2 减数 
	 * @return 两个参数的差 
	 */
	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b1.subtract(b2);
	}

	/** 
	 * 提供精确的乘法运算。 
	 * @param b1 被乘数 
	 * @param b2 乘数 
	 * @return 两个参数的积 
	 */
	public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b1.multiply(b2);
	}

	/** 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
	 * 小数点以后10位，以后的数字四舍五入。 
	 * @param b1 被除数 
	 * @param b2 除数 
	 * @return 两个参数的商 
	 */
	public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		if(b2.doubleValue() == 0.0)
			throw new IllegalArgumentException(
			"The number must not be zero.");
		return div(b1, b2, DEF_DIV_SCALE);
	}

	/** 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	 * 定精度，以后的数字四舍五入。 
	 * @param b1 被除数 
	 * @param b2 除数 
	 * @param scale 表示表示需要精确到小数点以后几位。 
	 * @return 两个参数的商 
	 */
	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		if(b2.doubleValue() == 0.0)
			throw new IllegalArgumentException(
			"The number must not be zero.");
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 
	 * 提供精确的小数位四舍五入处理。 
	 * @param v 需要四舍五入的数字 
	 * @param scale 小数点后保留几位 
	 * @return 四舍五入后的结果 
	 */
	public static BigDecimal round(BigDecimal b, int scale) {
		if(b == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal one = new BigDecimal(1);
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 
	 * 提供精确的类型转换(Float) 
	 * @param v 需要被转换的数字 
	 * @return 返回转换结果 
	 */
	public static float convertsToFloat(BigDecimal b) {
		if(b == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b.floatValue();
	}

	/** 
	 * 提供精确的类型转换(Int)不进行四舍五入 
	 * @param v 需要被转换的数字 
	 * @return 返回转换结果 
	 */
	public static int convertsToInt(BigDecimal b) {
		if(b == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b.intValue();
	}

	/** 
	 * 提供精确的类型转换(Long) 
	 * @param v 需要被转换的数字 
	 * @return 返回转换结果 
	 */
	public static long convertsToLong(BigDecimal b) {
		if(b == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b.longValue();
	}

	/** 
	 * 返回两个数中大的一个值 
	 * @param b1 需要被对比的第一个数 
	 * @param b2 需要被对比的第二个数 
	 * @return 返回两个数中大的一个值 
	 */
	public static BigDecimal returnMax(BigDecimal b1, BigDecimal b2) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b1.max(b2);
	}

	/** 
	 * 返回两个数中小的一个值 
	 * @param b1 需要被对比的第一个数 
	 * @param b2 需要被对比的第二个数 
	 * @return 返回两个数中小的一个值 
	 */
	public static BigDecimal returnMin(BigDecimal b1, BigDecimal b2) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		return b1.min(b2);
	}

	/** 
	 * 精确对比两个数字 
	 * @param b1 需要被对比的第一个数 
	 * @param b2 需要被对比的第二个数 
	 * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1 
	 */
	public static int compareTo(BigDecimal b1, BigDecimal b2) {
		if(b1 == null || b2 == null)
			throw new IllegalArgumentException(
			"The number must not be a empty number.");
		
		return b1.compareTo(b2);
	}
}
