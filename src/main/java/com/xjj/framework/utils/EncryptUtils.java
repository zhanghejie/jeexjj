package com.xjj.framework.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtils {

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final String PASSWORD_CRYPT_KEY = "xjj.com";
	private static final String MAC_NAME = "HmacSHA1";    
    private static final String ENCODING = "UTF-8";    
    
	public final static String MD5Encode(String s) {
		String returnString = null;
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			returnString = new String(str);
		} catch (Exception e) {
		}

		return returnString;
	}

	/**  
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名，使用公共密钥
     * @param str 被签名的字符串  
     * @return  
     * @throws Exception  
     */    
    public static byte[] HmacSHA1Encode(String str) throws Exception     
    {           
    	return HmacSHA1Encode(str,PASSWORD_CRYPT_KEY);
    }   
    
    /**  
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
     * @param str 被签名的字符串  
     * @param passwd  密钥  
     * @return  
     * @throws Exception  
     */    
    public static byte[] HmacSHA1Encode(String str, String passwd) throws Exception     
    {           
       byte[] data=passwd.getBytes(ENCODING);  
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称   
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);   
        //生成一个指定 Mac 算法 的 Mac 对象   
        Mac mac = Mac.getInstance(MAC_NAME);   
        //用给定密钥初始化 Mac 对象   
        mac.init(secretKey);    
          
        byte[] text = str.getBytes(ENCODING);    
        //完成 Mac 操作    
        return mac.doFinal(text);    
    }
	/**
	 * 用base64算法进行加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return base64加密后的结果
	 */
	public static String Base64Encode(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str.getBytes());
	}

	/**
	 * 用base64算法进行解密
	 * 
	 * @param str
	 *            需要解密的字符串
	 * @return base64解密后的结果
	 * @throws IOException
	 */
	public static String Base64decode(String str) throws IOException {
		BASE64Decoder encoder = new BASE64Decoder();
		return new String(encoder.decodeBuffer(str));
	}

	/**
	 * 密码加密
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public final static String DESEncode(String str) {
		return DESEncode(str, PASSWORD_CRYPT_KEY);
	}
	
	/**
	 * 密码加密
	 * @param str
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public final static String DESEncode(String str, String passwd) {
		try {
			return byte2hex(DESEncode(str.getBytes(), passwd.getBytes()));
		} catch (Exception e) { }
		return null;
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] DESEncode(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String DESDecode(String str) {
		return DESDecode(str, PASSWORD_CRYPT_KEY);
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String DESDecode(String str, String passwd) {
		try {
			return new String(DESDecode(hex2byte(str.getBytes()), passwd.getBytes()));
		} catch (Exception e) { }
		return null;
	}
	/**
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] DESDecode(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static void main(String[] args) {
		System.out.println(MD5Encode("123456"));
	}

}
