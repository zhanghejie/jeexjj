package com.xjj.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {
	
	/**
	 * 检测是为非空数值
	 * @param value
	 * @return
	 */
	public static final boolean isRequired(String value){
		return value != null && !value.trim().equals("");
	}
	public static final boolean isRequired(String... values){
		for(String value : values){
			if(value == null || value.trim().equals("")){
				return false;
			}
		}
		return true;
	}
	public static final boolean isRequired(Long value){
		return value != null;
	}
	public static final boolean isRequired(Integer value){
		return value != null;
	}
	/**
	 * 检测数值是否是2的幂
	 * @param value
	 * @return
	 */
	public static final boolean isPowerOf2(long value){
		return (value>0 && ((value & ((~value) + 1)) == value));
	}
	
	/**
	 * 校验是否是电子邮件地址，
	 * 格式为：带有@和.
	 */
	public static final boolean isEmail(String email){
		String regex = "[0-9a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}" ;
        return match( regex ,email );
	}
	/**
	 * 校验是否是url地址
	 * 格式为：http://地址:端口
	 */
	public static final boolean isURL(String url){
		String regex = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)" ;
        return match( regex ,url );
	}
	/**
	 * 校验是否是电话
	 * 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 
	 *        或0XXX XXXXXXX(10-13位首位必须为0) 
	 *        或(0XXX)XXXXXXXX(11-14位首位必须为0) 
	 *        或XXXXXXXX(6-8位首位不为0) 
	 *        或XXXXXXXXXXX(11位首位不为0)
	 * 匹配 : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或 010-12345678 或 12345678912 
	 */
	public static final boolean isPhotoNumber(String photoNumber){
		String regex = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";
        return match( regex ,photoNumber );
	}
	/**
	 * 校验是否是邮政编码
	 * 格式为: XXXXXX(6位数字)
	 */
	public static final boolean isPostCode(String postCode){
		String regex = "^[0-9]{6}$";
        return match( regex ,postCode );
	}
	/**
	 * 对身份证进行验证
	 * 格式为：15或者18位
	 */
	public static final boolean isIdCode(String idCode) {
		if(idCode==null || (idCode.length()!=15 && idCode.length()!=18)){
			return false;
		}
		if (idCode.length() == 15) {
			return true;
		}

		if (idCode.endsWith(getIdCodeVerifyCode(idCode))) {
			return true;
		}
		return false;
	}

	/**
	 * 从身份证中得到生日
	 * @param idCode
	 * @return
	 */
	public static Date getDateFromIdCode(String idCode){
		if(!isIdCode(idCode)){
			return null;
		}
		SimpleDateFormat dateFormat;
		try{
			if (idCode.length() == 15) {
				dateFormat = new SimpleDateFormat("yyMMdd");
				return dateFormat.parse(idCode.substring(6,12));
			}else{
				dateFormat = new SimpleDateFormat("yyyyMMdd");
				return dateFormat.parse(idCode.substring(6,14));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到身份证最后一位的校验码
	 * @param eighteenIdCode
	 * @return
	 */
	public static final String getIdCodeVerifyCode(String idCode) {
		
		final int[] ai = new int[18];
		final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
				1 };
		final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		int remaining = 0;
		String code;
		
		if(idCode==null || (idCode.length()!=15 && idCode.length()!=18)){
			return "";
		}
		
		if(idCode.length()==18){
			code = idCode.substring(0, 17);
		}else{
			code = new StringBuffer(idCode.substring(0, 6)).append("19").append(idCode.substring(6, 15)).toString();
		}

		if (code.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String str = code.substring(i, i + 1);
				ai[i] = Integer.parseInt(str);
			}
			for (int j = 0; j < 17; j++) {
				sum += wi[j] * ai[j];
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	/**
	 * 15位身份证号转换成18位
	 * 
	 * @param idCode
	 * @return
	 */
	public static final String idcodeTo18(String idCode) {
		StringBuffer fifteenIdCode = new StringBuffer(idCode.substring(0, 6))
					.append("19")
					.append(idCode.substring(6, 15));
		
		fifteenIdCode.append(getIdCodeVerifyCode(fifteenIdCode.toString()));
		return fifteenIdCode.toString();
	}
	/**
	 * 18位身份证号转换成15位
	 * 
	 * @param idCode
	 * @return
	 */
	public static final String idcodeTo15(String idcard) {
		if (idcard == null || idcard.length() != 18) {
			return idcard;
		}
		return idcard.substring(0, 6) + idcard.substring(8, 17);
	}
	
	  /** 
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match( String regex ,String str ){
    	if(str == null || str.equals("")){
    		return false;
    	}
        Pattern pattern = Pattern.compile(regex);
        Matcher  matcher = pattern.matcher( str );
        return matcher.matches();
    }
    
    public static void main(String[] args){
    	System.out.println("email(123456@qq.com) is " + ValidatorUtils.isEmail("123456@qq.com"));
    	System.out.println("email(xjj@c) is " + ValidatorUtils.isEmail("xjj@c"));
    	System.out.println("url  (http://a.a) is " + ValidatorUtils.isURL("http://a.a"));
    	System.out.println("url  (http://a.a:8080/a.a) is " + ValidatorUtils.isURL("http://a.a:8080/a.a"));
    }

}
