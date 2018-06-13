package com.xjj.framework.utils;

/**
 * @author xjj
 */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StringUtils.class);

	/**
	 * 将字符串转化为中文编码gb2312，默认输入的格式为iso-8859-1
	 * 
	 * @param strInput
	 *            输入字符串
	 * @return
	 */
	public static String convertToChinese(String strInput) {
		return convertToChinese(strInput, "iso-8859-1");
	}

	/**
	 * 将字符串转化为中文编码gb2312
	 * 
	 * @param strInput
	 *            输入文本
	 * @param srcEncode
	 *            输入的格式
	 * @return
	 */
	public static String convertToChinese(String strInput, String srcEncode) {
		String str = "";
		try {
			str = new String(strInput.getBytes(srcEncode), "gb2312");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return strInput;
		}
		return str;

	}

	/**
	 * 将列表中的文本前后加入指定字符
	 * 
	 * @param list
	 *            字符串列表
	 * @param appStr
	 *            添加的指定字符
	 * @return
	 */
	public static List<String> appendAll(List<String> list, String appStr) {
		List<String> tmpList = new ArrayList<String>();
		;
		for (String str : list) {
			str = appStr + str + appStr;
			tmpList.add(str);
		}
		return tmpList;
	}

	/**
	 * 将列表中的文本前面加入指定字符
	 * 
	 * @param list
	 *            字符串列表
	 * @param appStr
	 *            添加的指定字符
	 * @return
	 */
	public static List<String> appendFore(List<String> list, String appStr) {
		List<String> tmpList = new ArrayList<String>();
		;
		for (String str : list) {
			str = appStr + str;
			tmpList.add(str);
		}
		return tmpList;
	}

	/**
	 * 将列表中的文本后面加入指定字符
	 * 
	 * @param list
	 *            字符串列表
	 * @param appStr
	 *            添加的指定字符
	 * @return
	 */
	public static List<String> appendBack(List<String> list, String appStr) {
		List<String> tmpList = new ArrayList<String>();
		;
		for (String str : list) {
			str = str + appStr;
			tmpList.add(str);
		}
		return tmpList;
	}

	/**
	 * 将列表中的字符串合并
	 * 
	 * @param list
	 *            列表
	 * @param delim
	 *            分隔符
	 * @return
	 */
	public static String join(List<String> list, String delim) {
		if (list == null || list.size() < 1) {
			return null;
		}
		if (delim == null) {
			delim = "";
		}
		StringBuffer buf = new StringBuffer();
		for (String str : list) {
			buf.append(str);
			buf.append(delim);
		}
		return buf.toString().substring(0, buf.toString().lastIndexOf(delim));
	}

	/**
	 * 将列表中的字符串合并
	 * 
	 * @param list
	 *            列表
	 * @return
	 */
	public static String join(Object... list) {
		if (list == null || list.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (Object c : list) {
			builder.append(c);
		}
		return builder.toString();
	}

	/**
	 * 将字符窜拆成数组
	 * 
	 * @param str
	 *            带拆分的字符串
	 * @param delim
	 *            分隔符
	 * @return
	 */
	public static String[] splitToArray(String str, String delim) {
		if (delim == null || delim.equals(""))
			delim = ",";
		String[] ret = new String[] {};
		if (str == null || str.equals("")) {
			return ret;
		}
		if (str.indexOf(delim) < 0) {
			return new String[] { str.trim() };
		}
		Vector<String> splitList = new Vector<String>();
		for (String s : str.split(delim)) {
			String trimmed = s.trim();
			if (trimmed.length() > 0)
				splitList.add(trimmed);
		}
		return splitList.toArray(ret);
	}

	/**
	 * 将字符窜拆成List列表
	 * 
	 * @param str
	 *            带拆分的字符串
	 * @param delim
	 *            分隔符
	 * @return
	 */
	public static List<String> splitToList(String str, String delim) {
		if (delim == null || delim.equals(""))
			delim = ",";
		List<String> splitList = new ArrayList<String>();
		for (String s : str.split(delim)) {
			String trimmed = s.trim();
			if (trimmed.length() > 0)
				splitList.add(trimmed);
		}
		return splitList;
	}

	/**
	 * 将字符窜拆成Set
	 * 
	 * @param str
	 *            带拆分的字符串
	 * @param delim
	 *            分隔符
	 * @return
	 */
	public static Set<String> splitToSet(String str, String delim) {
		if (delim == null || delim.equals(""))
			delim = ",";
		Set<String> splitSet = new HashSet<String>();
		;
		for (String s : str.split(delim)) {
			String trimmed = s.trim();
			if (trimmed.length() > 0)
				splitSet.add(trimmed);
		}
		return splitSet;
	}

	/**
	 * 截断过长的以空格分割的单词
	 * 
	 * @param str
	 *            被截断的字符串
	 * @param maxLength
	 *            最大长度
	 * @return
	 */
	public static String createBreaks(String str, int maxLength) {
		char chars[] = str.toCharArray();
		int len = chars.length;
		StringBuffer buf = new StringBuffer(len);
		int count = 0;
		int cur = 0;
		for (int i = 0; i < len; i++) {
			if (Character.isWhitespace(chars[i])) {
				count = 0;
			}
			if (count >= maxLength) {
				count = 0;
				buf.append(chars, cur, i - cur).append(" ");
				cur = i;
			}
			count++;
		}
		buf.append(chars, cur, len - cur);
		return buf.toString();
	}

	/**
	 * 格式化SQL语句标志
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeSQLTags(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\\') {
				buf.append("\\\\");
			} else if (ch == '\'') {
				buf.append("\'\'");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 格式化html文本，将html文本进行编码
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeHTMLTags(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '"') {
				buf.append("&quot;");
			} else if (ch == ' ') {
				buf.append("&nbsp;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 反格式化html文本，将html中的编码转化为对应格式
	 * 
	 * @param str
	 * @return
	 */
	public static String toHTMLTags(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return str.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
				.replaceAll("&nbsp;", " ");
	}

	/**
	 * 将换行转换为html标签
	 * 
	 * @param str
	 * @return
	 */
	public static String convertNewlines(String str) {
		str = replace(str, "\r\n", "<P>");
		str = replace(str, "\n", "<BR>");
		return str;
	}

	/**
	 * 替换字符串中的字符
	 * 
	 * @param str
	 *            待替换的字符串
	 * @param oldString
	 *            旧字符
	 * @param newString
	 *            新字符
	 * @return
	 */
	public static String replace(String str, String oldString, String newString) {
		if (str == null) {
			return null;
		}
		int i = str.lastIndexOf(oldString);
		if (i < 0) {
			return str;
		}
		StringBuffer mainSb = new StringBuffer(str);
		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = str.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().equals("")) ? true : false;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @param can_be_decimal
	 *            是否允许小数
	 * @return
	 */
	public static boolean isNumeric(String str, boolean can_be_decimal) {
		boolean flag = true;
		int dot_cnt = 0; // 检查字符中小数点出现的次数，如果>=2，则不能认定这个字符串要表达是数字
		if (str == null || str.isEmpty()) {
			flag = false;
		} else {
			for (int i = str.length(); --i >= 0;) {
				if (!Character.isDigit(str.charAt(i))) {

					if (can_be_decimal == true) {
						if (str.charAt(i) != '.')
							flag = false;
						else
							++dot_cnt; // 如果是小数点，计数器加1
					} else {
						flag = false;
					}

				}
			}
		}
		if (dot_cnt >= 2)
			flag = false;
		return flag;
	}

	/**
	 * 得到非空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getStringNotNull(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * 字符串转化为整型数字
	 * 
	 * @param param
	 * @return
	 */
	public static int parseInt(String param) {
		int i = 0;
		try {
			i = Integer.parseInt(param);
		} catch (Exception e) {
			i = (int) parseFloat(param);
		}
		return i;
	}

	/**
	 * 字符串转化为长整型数字
	 * 
	 * @param param
	 * @return
	 */
	public static long parseLong(String param) {
		long l = 0;
		try {
			l = Long.parseLong(param);
		} catch (Exception e) {
			l = (long) parseDouble(param);
		}
		return l;
	}

	/**
	 * 字符串转化为浮点型数字
	 * 
	 * @param param
	 * @return
	 */
	public static float parseFloat(String param) {
		float f = 0;
		try {
			f = Float.parseFloat(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return f;
	}

	/**
	 * 字符串转化为双浮点型数字
	 * 
	 * @param param
	 * @return
	 */
	public static double parseDouble(String param) {
		double d = 0;
		try {
			d = Double.parseDouble(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return d;
	}

	/**
	 * 字符串转化为布尔型
	 * 
	 * @param param
	 * @return
	 */
	public static boolean parseBoolean(String param) {
		if (isBlank(param)) {
			return false;
		}
		switch (param.charAt(0)) {
		case '1':
		case 'y':
		case 'Y':
		case 't':
		case 'T':
			return true;
		}
		return false;
	}

	/**
	 * Convert URL .
	 * 
	 * @param input
	 *            string to convert
	 * @return string
	 */
	public static String convertURL(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input.length() + 25);
		char chars[] = input.toCharArray();
		int len = input.length();
		int index = -1;
		int i = 0;
		int j = 0;
		int oldend = 0;
		while (++index < len) {
			char cur = chars[i = index];
			j = -1;
			if ((cur == 'f' && index < len - 6 && chars[++i] == 't'
					&& chars[++i] == 'p' || cur == 'h' && (i = index) < len - 7
					&& chars[++i] == 't' && chars[++i] == 't'
					&& chars[++i] == 'p'
					&& (chars[++i] == 's' || chars[--i] == 'p'))
					&& i < len - 4
					&& chars[++i] == ':'
					&& chars[++i] == '/'
					&& chars[++i] == '/') {
				j = ++i;
			}
			if (j > 0) {
				if (index == 0 || (cur = chars[index - 1]) != '\''
						&& cur != '"' && cur != '<' && cur != '=') {
					cur = chars[j];
					while (j < len) {
						if (cur == ' ' || cur == '\t' || cur == '\''
								|| cur == '"' || cur == '<' || cur == '['
								|| cur == '\n' || cur == '\r' && j < len - 1
								&& chars[j + 1] == '\n') {
							break;
						}
						if (++j < len) {
							cur = chars[j];
						}
					}
					cur = chars[j - 1];
					if (cur == '.' || cur == ',' || cur == ')' || cur == ']') {
						j--;
					}
					buf.append(chars, oldend, index - oldend);
					buf.append("<a href=\"");
					buf.append(chars, index, j - index);
					buf.append('"');
					buf.append(" target=\"_blank\"");
					buf.append('>');
					buf.append(chars, index, j - index);
					buf.append("</a>");
				} else {
					buf.append(chars, oldend, j - oldend);
				}
				oldend = index = j;
			} else if (cur == '[' && index < len - 6
					&& chars[i = index + 1] == 'u' && chars[++i] == 'r'
					&& chars[++i] == 'l'
					&& (chars[++i] == '=' || chars[i] == ' ')) {
				j = ++i;
				int u2;
				int u1 = u2 = input.indexOf("]", j);
				if (u1 > 0) {
					u2 = input.indexOf("[/url]", u1 + 1);
				}
				if (u2 < 0) {
					buf.append(chars, oldend, j - oldend);
					oldend = j;
				} else {
					buf.append(chars, oldend, index - oldend);
					buf.append("<a href =\"");
					String href = input.substring(j, u1).trim();
					if (href.indexOf("javascript:") == -1
							&& href.indexOf("file:") == -1) {
						buf.append(href);
					}
					buf.append("\" target=\"_blank");
					buf.append("\">");
					buf.append(input.substring(u1 + 1, u2).trim());
					buf.append("</a>");
					oldend = u2 + 6;
				}
				index = oldend;
			}
		}
		if (oldend < len) {
			buf.append(chars, oldend, len - oldend);
		}
		return buf.toString();
	}

	/**
	 * 显示转化后的html字符串
	 * 
	 * @param input
	 * @return
	 */
	public static String displayHtml(String input) {
		String str = input;
		str = createBreaks(str, 80);
		str = escapeHTMLTags(str);
		str = convertURL(str);
		str = convertNewlines(str);
		return str;
	}

	/**
	 * 返回数字对应的ascii码
	 * 
	 * @param number
	 * @return
	 */
	public static String getASCII(int number) {
		String str = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String asc = "" + str.charAt(number);
		return asc;
	}

	/**
	 * 转换数字为字符串，并格式化数字长度，不足则在前面补0
	 * 
	 * @param number
	 * @param length
	 * @return
	 */
	public static String convertNumberString(int number, int length) {
		int yourNumberCount = String.valueOf(number).length();
		int zeroLen = length - yourNumberCount;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < zeroLen; i++) {
			sb.append("0");
		}
		sb.append(number);
		return sb.toString();
	}

	/**
	 * 截断字符，后面自动加上省略号
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String breakString(String str, int length) {
		return breakString(str, length, false, true);
	}

	/**
	 * 截断字符
	 * 
	 * @param str
	 * @param length
	 * @param removeTags
	 *            是否去掉空白字符和特殊格式
	 * @param ellipsis
	 *            是否自动加...
	 * @return
	 */
	public static String breakString(String str, int length,
			boolean removeTags, boolean ellipsis) {
		String s = str;
		if (removeTags) {
			s = s.replaceAll("\\s*|\t|\r|\n", "");
		}
		if (length <= 0 || s.length() <= length) {
			return s;
		} else {
			String tmp = s.substring(0, length);
			if (ellipsis) {
				tmp += "...";
			}
			return tmp;
		}

	}

	/**
	 * 返回文本的长度，中文占2个字符
	 * 
	 * @param input
	 * @return
	 */
	public static int getSize(String input) {
		String str = input;
		int size = 0;
		byte[] b = str.getBytes();
		size = b.length;
		return size;
	}

	public static String native2ascii(String str) {
		char[] ac = str.toCharArray();
		StringBuffer rs = new StringBuffer(ac.length);
		for (int k = 0; k < ac.length; k++)
			if (ac[k] > '\177') {
				rs.append((char) 92);
				rs.append((char) 117);
				String s1 = Integer.toHexString(ac[k]);
				StringBuffer stringbuffer = new StringBuffer(s1);
				stringbuffer.reverse();
				int l = 4 - stringbuffer.length();
				for (int i1 = 0; i1 < l; i1++)
					stringbuffer.append('0');

				for (int j1 = 0; j1 < 4; j1++)
					rs.append(stringbuffer.charAt(3 - j1));

			} else {
				rs.append(ac[k]);
			}
		return rs.toString();
	}

	public static String urlEncoder(String src) {
		try {
			return URLEncoder.encode(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return src;
		}
	}

	public static String urlDecoder(String src) {
		try {
			return java.net.URLDecoder.decode(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return src;
		}
	}

	/**
	 * 将日期的数字格式转化为中文０一二三四五六七八九十
	 * 
	 * @return
	 */
	public static String date2chinese(Date date, int format) {
		if (date == null) {
			date = new Date();
		}
		if (format <= 0) {
			format = 3;
		}

		String[] NUMBERS = { "０", "一", "二", "三", "四", "五", "六", "七", "八", "九",
				"十" };

		StringBuffer chinese = new StringBuffer();
		// 处理年
		String year = String.valueOf(DateTimeUtils.getYear(date));
		for (int i = 0; i < year.length(); i++) {
			chinese.append(NUMBERS[Integer.valueOf(String.valueOf(year
					.charAt(i)))]);
		}
		chinese.append("年");
		if (format <= 1) {
			return chinese.toString();
		}
		// 处理月
		int month = DateTimeUtils.getMonth(date);
		if (month < 10) {
			chinese.append(NUMBERS[month]);
		} else if (month == 10) {
			chinese.append(NUMBERS[10]);
		} else {
			chinese.append(NUMBERS[10]).append(NUMBERS[month % 10]);
		}
		chinese.append("月");
		if (format <= 2) {
			return chinese.toString();
		}
		// 处理日
		int day = DateTimeUtils.getDay(date);
		if (day < 10) {
			chinese.append(NUMBERS[day]);
		} else if (day == 10) {
			chinese.append(NUMBERS[10]);
		} else if (day < 20) {
			chinese.append(NUMBERS[10]).append(NUMBERS[day % 10]);
		} else if (day == 20) {
			chinese.append(NUMBERS[2]).append(NUMBERS[10]);
		} else if (day < 30) {
			chinese.append(NUMBERS[2]).append(NUMBERS[10])
					.append(NUMBERS[day % 20]);
		} else if (day == 30) {
			chinese.append(NUMBERS[3]).append(NUMBERS[10]);
		} else {
			chinese.append(NUMBERS[3]).append(NUMBERS[10])
					.append(NUMBERS[day % 30]);
		}
		chinese.append("日");
		return chinese.toString();
	}

	/**
	 * 将数字转化成中文表示
	 * 
	 * @param number
	 *            数字字符串
	 * @return
	 */
	public static String number2chinese(String number) {
		String[] NUMBERS = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] IUNIT = { "点", "十", "百", "千", "万", "十", "百", "千", "亿", "十",
				"百", "千", "万", "十", "百", "千" };

		number = number.replaceAll(",", "");// 去掉","
		String integerStr;// 整数部分数字
		String decimalStr;// 小数部分数字

		// 初始化：分离整数部分和小数部分
		if (number.indexOf(".") > 0) {
			integerStr = number.substring(0, number.indexOf("."));
			decimalStr = number.substring(number.indexOf(".") + 1);
		} else if (number.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = number.substring(1);
		} else {
			integerStr = number;
			decimalStr = "";
		}
		// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow超出处理能力，直接返回
		if (integerStr.length() > IUNIT.length) {
			// System.out.println(number + ":超出处理能力");
			return number;
		}

		StringBuffer chinese = new StringBuffer();
		int length = integerStr.length();
		String key = "";

		// 判断是否显示万
		boolean isMust5 = false;
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// 取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			isMust5 = Integer.parseInt(subInteger) > 0;
		}

		// 得到整数部分
		int[] array = new int[integerStr.length()];
		for (int i = 0; i < integerStr.length(); i++) {
			array[i] = Integer.parseInt(integerStr.substring(i, i + 1));
		}

		for (int i = 0; i < length; i++) {
			// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
			// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
			key = "";
			if (array[i] == 0) {
				if ((length - i) == 13)// 万(亿)(必填)
					key = IUNIT[4];
				else if ((length - i) == 9)// 亿(必填)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// 万(不必填)
					key = IUNIT[4];
				// else if ((length - i) == 1)// 元(必填)
				// key = IUNIT[0];
				// 0遇非0时补零，不包含最后一位
				if ((length - i) > 1 && array[i + 1] != 0)
					key += NUMBERS[0];
			}
			chinese.append(array[i] == 0 ? key
					: (NUMBERS[array[i]] + ((length - i) == 1 ? ""
							: IUNIT[length - i - 1])));
		}

		// 处理小数部分
		if (decimalStr != null && !decimalStr.equals("")) {
			chinese.append(IUNIT[0]);
			array = new int[decimalStr.length()];
			for (int i = 0; i < decimalStr.length(); i++) {
				array[i] = Integer.parseInt(decimalStr.substring(i, i + 1));
			}
			for (int i = 0; i < array.length; i++) {
				chinese.append(NUMBERS[array[i]]);
			}
		}

		return chinese.toString();

	}

	public static String number2money(double number) {
		return number2money(String.valueOf(number));
	}

	/**
	 * 将数值转化为中文大写金额
	 * 
	 * @param number
	 * @return
	 */
	public static String number2money(String number) {
		String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾",
				"佰", "仟", "万", "拾", "佰", "仟" };
		String[] DUNIT = { "角", "分", "厘" };

		// 处理传入的数据
		number = number.replaceAll(",", "");// 去掉","
		String integerStr;// 整数部分数字
		String decimalStr;// 小数部分数字

		// 初始化：分离整数部分和小数部分
		if (number.indexOf(".") > 0) {
			integerStr = number.substring(0, number.indexOf("."));
			decimalStr = number.substring(number.indexOf(".") + 1);
		} else if (number.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = number.substring(1);
		} else {
			integerStr = number;
			decimalStr = "";
		}
		// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow超出处理能力，直接返回
		if (integerStr.length() > IUNIT.length) {
			// System.out.println(number + ":超出处理能力");
			return number;
		}

		StringBuffer chinese = new StringBuffer();
		int length = integerStr.length();
		String key = "";

		// 判断是否显示万
		boolean isMust5 = false;
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// 取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			isMust5 = Integer.parseInt(subInteger) > 0;
		}

		// 得到整数部分
		int[] array = new int[integerStr.length()];
		for (int i = 0; i < integerStr.length(); i++) {
			array[i] = Integer.parseInt(integerStr.substring(i, i + 1));
		}

		for (int i = 0; i < length; i++) {
			// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
			// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
			key = "";
			if (array[i] == 0) {
				if ((length - i) == 13)// 万(亿)(必填)
					key = IUNIT[4];
				else if ((length - i) == 9)// 亿(必填)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// 万(不必填)
					key = IUNIT[4];
				else if ((length - i) == 1)// 元(必填)
					key = IUNIT[0];
				// 0遇非0时补零，不包含最后一位
				if ((length - i) > 1 && array[i + 1] != 0)
					key += NUMBERS[0];
			}
			chinese.append(array[i] == 0 ? key
					: (NUMBERS[array[i]] + IUNIT[length - i - 1]));
		}

		// 处理小数部分
		if (decimalStr != null && !decimalStr.equals("")) {
			array = new int[decimalStr.length()];
			for (int i = 0; i < decimalStr.length(); i++) {
				array[i] = Integer.parseInt(decimalStr.substring(i, i + 1));
			}
			for (int i = 0; i < array.length; i++) {
				// 舍去3位小数之后的
				if (i == 3)
					break;
				chinese.append(array[i] == 0 ? ""
						: (NUMBERS[array[i]] + DUNIT[i]));
			}
		}

		return chinese.toString();

	}

	/**
	 * 根据输入的分子分母两个参数获得百分数,取两位小数 memeber:分子 denominator:分母
	 */
	public static String getPercentStr(Integer memeber, Integer denominator) {
		if (denominator == 0) {
			return "0.00%";
		}
		double x = (double) memeber;
		double y = (double) denominator;
		double z = x / y;
		DecimalFormat myformat = null;
		myformat = (DecimalFormat) NumberFormat.getPercentInstance();
		myformat.applyPattern("00.00%");// 设置百分率的输出形式，形如00.*,根据需要设定。
		return myformat.format(z);
	}

	/**
	 * 将格式为“[key:value]”的字符串转换为Map<Long,String>
	 * 
	 * @param str
	 * @return
	 */
	public static Map<Long, String> getMap(String str) {
		if (str != null) {
			String[] strs = str.split("]");
			Long key = null;
			String value = null;
			Map<Long, String> map = new HashMap<Long, String>();
			if (strs != null && strs.length > 0) {
				for (String s : strs) {
					String[] ss = s.split(":");
					if (ss != null && ss.length == 2) {
						String keyStr = ss[0].substring(1);
						key = keyStr == null ? null : new Long(keyStr);
						value = ss[1];
						if (key != null && value != null) {
							map.put(key, value);
						}

					}
				}
			}
			return map;
		}
		return null;
	}

	/**
	 * 将Map<Long,String>转换为格式为“[key:value]”的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getString(Map<Long, String> map) {
		StringBuffer str = new StringBuffer();
		Long key = null;
		String value = null;
		if (map != null && map.keySet().size() > 0) {
			for (Long l : map.keySet()) {
				key = l;
				value = map.get(l);
				if (key != null && value != null) {
					str.append("[" + key + ":" + value + "]");
				}
			}
			return str.toString();
		}
		return null;
	}

	/**
	 * 删除字符串中的html格式，默认全部返回
	 * 
	 * @param input
	 *            被转换的带有html的文本
	 * @return
	 */
	public static String removeHTMLTags(String input) {
		return removeHTMLTags(input, 0, false);
	}

	/**
	 * 删除字符串中的html格式
	 * 
	 * @param input
	 *            被转换的带有html的文本
	 * @param length
	 *            截取字符的长度
	 * @param ellipsis
	 *            是否自动加入省略号
	 * @return
	 */
	public static String removeHTMLTags(String input, int length,
			boolean ellipsis) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(input);
		input = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(input);
		input = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(input);
		input = m_html.replaceAll(""); // 过滤html标签
		if(length>0){
			return  breakString(input.trim(), length, true, ellipsis);
		}
		return input;
	}
	
	
	/**
	 * 将字符串由下划线改为路径
	 * 
	 * @param s
	 * @return
	 */
	public static String toURIFromUnderCase(String s) {
		
		if(isBlank(s))
		{
			return "/";
		}
		s = s.toLowerCase();
		s = s.replaceAll("_","/");
		return "/"+s;
	}

	/**
	 * 将字符串由驼峰式改为下划线
	 * 
	 * @param s
	 * @return
	 */
	public static String toUnderScoreCase(String s) {
		return toUnderScoreCase(s, null);
	}

	/**
	 * 将字符串由驼峰式改为分割线
	 * 
	 * @param s
	 * @param SEPARATOR
	 * @return
	 */
	public static String toUnderScoreCase(String s, String SEPARATOR) {
		if (isBlank(SEPARATOR)) {
			SEPARATOR = "_";
		}
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 将字符串由下划线改为驼峰式，首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		return toCamelCase(s, false);
	}

	/**
	 * 将字符串由下划线改为驼峰式
	 * 
	 * @param s
	 * @param capitalizeUpper
	 *            首字母是否大写
	 * @return
	 */
	public static String toCamelCase(String s, boolean capitalizeUpper) {
		char SEPARATOR = '_';
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		if (capitalizeUpper) {
			upperCase = true;
		}
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();

	}

	/**
	 * 统计字符串中的单词数量
	 * 
	 * @param param
	 * @return
	 */
	public static long countWords(String param) {
		Pattern expression = Pattern.compile("\\w+");// 定义正则表达式匹配单词
		int n = 0;// 文章中单词总数
		if (param != null && !param.equals("")) {
			String input = param;
			if (input == null || input.trim().equals("")) {
				return 0l;
			}
			String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
			String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			Pattern p_script = Pattern.compile(regEx_script,
					Pattern.CASE_INSENSITIVE);
			Matcher m_script = p_script.matcher(input);
			input = m_script.replaceAll(""); // 过滤script标签

			Pattern p_style = Pattern.compile(regEx_style,
					Pattern.CASE_INSENSITIVE);
			Matcher m_style = p_style.matcher(input);
			input = m_style.replaceAll(""); // 过滤style标签
			//a、p、table、tr、th、td、br、ul、li、ol、div、hr替换成空格
			String regEx_space="<(a|p|table|tr|th|td|br|ul|li|ol|div|hr)[^>]*>";
			Pattern p_space = Pattern.compile(regEx_space,
					Pattern.CASE_INSENSITIVE);
			Matcher s_html = p_space.matcher(input);
			input = s_html.replaceAll(" "); 
			
			Pattern p_html = Pattern.compile(regEx_html,
					Pattern.CASE_INSENSITIVE);
			Matcher m_html = p_html.matcher(input);
			input = m_html.replaceAll(""); // 过滤html标签
			//转成HTML
			input=toHTMLTags(input);

			Matcher matcher = expression.matcher(input);// 定义string1的匹配器
			while (matcher.find()) {// 是否匹配单词
				n++;// 单词数加1
			}
		}
		return n;
	}

	public static void main(String[] args) {
		// System.out.println("abc".substring(0,"abc".lastIndexOf("c")));
		// 测试输出时间中文
		// System.out.println("2009 is " + StringUtils.date2chinese("2009"));
		// //测试数字转成中文
		// System.out.println("012300000 is " +
		// StringUtils.number2chinese("012300000"));
		// System.out.println("012300001 is " +
		// StringUtils.number2chinese("012300001"));
		// System.out.println("012.300000 is " +
		// StringUtils.number2chinese("012.300000"));
		// System.out.println("012300.001 is " +
		// StringUtils.number2chinese("012300.001"));
		// //测试生成钱
		// String number = "1.23";
		// System.out.println(number + " " + StringUtils.number2money(number));
		// number = "1234567890123456.123";
		// System.out.println(number + " " + StringUtils.number2money(number));
		// number = "0.0798";
		// System.out.println(number + " " + StringUtils.number2money(number));
		// number = "10,001,000.09";
		// System.out.println(number + " " + StringUtils.number2money(number));
		// number = "01.107700";
		// System.out.println(number + " " + StringUtils.number2money(number));
		// number = "1000000000.107700";
		// System.out.println(number + " " + StringUtils.number2money(number));
		//
		// String str = "<a href=\"#\">link</a>";
		// System.out.println(str + " " +
		// StringUtils.removeHTMLTags(str,2,true));
		//
		//
		// String html =
		// "<STYLE> \r\n /* Generator: eWebEditor */  \r\n div.Section1 {page:Section1;} \r\n </STYLE>  \r\n "
		// +
		// "这时真正的测试iwe脑子。";
		// System.out.println(html + " format is :\r\n" +
		// StringUtils.removeHTMLTags(html, 15, true));

		// String str="aaabbbcccdddeeefffggghhhiii";
		// long start = System.currentTimeMillis();
		// for(int i=0;i<100000;i++){
		// @SuppressWarnings("unused")
		// String rep = replace(str,"eee","111");
		// //System.out.println(rep);
		// }
		// long end = System.currentTimeMillis();
		// System.out.println(end - start);
		//
		// start = System.currentTimeMillis();
		// for(int i=0;i<100000;i++){
		//
		// @SuppressWarnings("unused")
		// String rep = str.replace("eee","111");
		// //System.out.println(rep);
		// }
		// end = System.currentTimeMillis();
		// System.out.println(end - start);

		System.out.println(StringUtils.date2chinese(
				DateTimeUtils.parse("2000-10-11", "yyyy-MM-dd"), 3));
		String input="你好\r\n"+
" nishinnihao whadsfjlajdsla;kfja;</p><p>djfladsjklfkas</p><p>dfjaslkdfjlakdsnfa</p><p>df</p><p>d</p><p>&nbsp;</p><p>d</p><p>d</p><p>&nbsp;</p><p>d</p><p>d</p><p>f</p><p>asd</p><p>f</p><p>a</p><p>sd</p><p>f</p><p>ds</p><p>f</p><p>asd</p><p>fsadfasdf\r\n";
		System.out.println(StringUtils.countWords(input));
	}

}
