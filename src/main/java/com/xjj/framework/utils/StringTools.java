package com.xjj.framework.utils;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

public class StringTools {

	/**
	 * 获取指定长度的随机字符串
	 * 
	 * @param count
	 * @return
	 */
	public static String getRandomString(int count) {
		String rasc = "";
		int number = 0;
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			number = random.nextInt(54);
			rasc = rasc + getASC(number);
		}
		return rasc;
	}

	private static String getASC(int number) {// o O l i I 0 1容易混淆，去掉
		String str = "abcdefghjkmnpqrstuvwxyz23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
		String asc = "" + str.charAt(number);
		return asc;
	}

	public static String curString(String src, int length) {
		if (src.length() <= length) {
			return src;
		} else {
			String tmp = src.substring(0, length);
			tmp = tmp + "...";
			return tmp;
		}

	}

	/**
	 * 获得当前时间
	 * 
	 * @param parrten
	 *          输出的时间格式
	 * 
	 * 
	 * @return 返回时间
	 */
	public static String getTime(String parrten) {
		String timestr;
		if (parrten == null || parrten.equals("")) {
			parrten = "yyyyMMddHHmmss";
		}
		java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);
		java.util.Date cday = new Date();
		timestr = sdf.format(cday);
		return timestr;
	}

	/**
	 * 比较两个字符串时间的大小
	 * 
	 * @param t1
	 *          时间1
	 * @param t2
	 *          时间2
	 * @param parrten
	 *          时间格式 :yyyy-MM-dd
	 * @return 返回long =0相等，>0 t1>t2，<0 t1<t2
	 */
	public static long compareStringTime(String t1, String t2, String parrten) {
		SimpleDateFormat formatter = new SimpleDateFormat(parrten);
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		Date dt1 = formatter.parse(t1, pos);
		Date dt2 = formatter.parse(t2, pos1);
		long l = dt1.getTime() - dt2.getTime();
		return l;
	}

	public static String convertToChinese(String strInput) {
		String str = "";
		if (strInput == null || strInput.length() <= 0) {
			return strInput;
		}
		try {
			str = new String(strInput.getBytes("iso-8859-1"), "gb2312");
		} catch (Exception e) {
			str = strInput;
		}
		return str;
	}

	public static String convertToUTF8(String strInput) {
		String str = "";
		if (strInput == null || strInput.length() <= 0) {
			return strInput;
		}
		try {
			str = new String(strInput.getBytes( "UTF-8"));
		} catch (Exception e) {
			str = strInput;
		}
		return str;
	}

	public static List quoteStrList(List list) {
		List tmpList = list;
		list = new ArrayList();
		Iterator i = tmpList.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			str = "'" + str + "'";
			list.add(str);
		}
		return list;
	}

	public static String join(List list, String delim) {
		if (list == null || list.size() < 1) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			buf.append((String) i.next());
			if (i.hasNext()) {
				buf.append(delim);
			}
		}
		return buf.toString();
	}
	/**
	 * 数组转成字符串
	 * @param list
	 * @param delim
	 * @return
	 */
	public static String join(Object[] list, String delim) {
		if (list == null || list.length < 1) {
			return null;
		}
		if (delim == null) {
			delim = "";
		}
		StringBuffer buf = new StringBuffer();
		for (Object str : list) {
			buf.append(str);
			buf.append(delim);
		}
		return buf.toString().substring(0, buf.toString().lastIndexOf(delim));
	}

	public static List split(String str, String delim) {
		List splitList = null;
		StringTokenizer st = null;

		if (str == null) {
			return splitList;
		}

		if (delim != null) {
			st = new StringTokenizer(str, delim);
		} else {
			st = new StringTokenizer(str);

		}
		if (st != null && st.hasMoreTokens()) {
			splitList = new ArrayList();

			while (st.hasMoreTokens()) {
				splitList.add(st.nextToken());
			}
		}
		return splitList;
	}

	public static String createBreaks(String input, int maxLength) {
		char chars[] = input.toCharArray();
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
				buf.append(chars, cur, i - cur).append("\n");
				cur = i;
			}
			count++;
		}
		buf.append(chars, cur, len - cur);

		return buf.toString();
	}

	/**
	 * Escape SQL tags, ' to ''; \ to \\.
	 * 
	 * @param input
	 *          string to replace
	 * @return string
	 */
	public static String escapeSQLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
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
	 * Escape HTML tags.
	 * 
	 * @param input
	 *          string to replace
	 * @return string
	 */
	public static String escapeHTMLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '"') {
				buf.append("&quot;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * Convert new lines, \n or \r\n to <BR />
	 * .
	 * 
	 * @param input
	 *          string to convert
	 * @return string
	 */
	public static String convertNewlines(String input) {
		input = replace(input, "\r\n", "<P>");
		input = replace(input, "\n", "<BR>");
		return input;
	}

	public static String convertNewlinesP(String input) {
		input = replace(input, "\r\n", "<P>");
		return input;
	}

	public static String convertNewlinesBR(String input) {
		input = replace(input, "\r\n", "<BR>");
		input = replace(input, "\n", "<BR>");
		return input;
	}

	public static String convertBR(String input) {
		input = replace(input, "\n", "<BR>");
		return input;
	}

	public static String replace(String mainString, String oldString,
			String newString) {
		if (mainString == null) {
			return null;
		}
		int i = mainString.lastIndexOf(oldString);
		if (i < 0) {
			return mainString;
		}
		StringBuffer mainSb = new StringBuffer(mainString);
		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * Check a string null or blank.
	 * 
	 * @param param
	 *          string to check
	 * @return boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("")) ? true : false;
	}

	public static String notNull(String param) {
		return param == null ? "" : param.trim();
	}

	/**
	 * Parse a string to int.
	 * 
	 * @param param
	 *          string to parse
	 * @return int value, on exception return 0.
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

	public static long parseLong(String param) {
		long l = 0;
		try {
			l = Long.parseLong(param);
		} catch (Exception e) {
			l = (long) parseDouble(param);
		}
		return l;
	}

	public static float parseFloat(String param) {
		float f = 0;
		try {
			f = Float.parseFloat(param);
		} catch (Exception e) {
			//
		}
		return f;
	}

	public static double parseDouble(String param) {
		double d = 0;
		try {
			d = Double.parseDouble(param);
		} catch (Exception e) {
			//
		}
		return d;
	}

	/**
	 * Parse a string to boolean.
	 * 
	 * @param param
	 *          string to parse
	 * @return boolean value, if param begin with(1,y,Y,t,T) return true, on
	 *         exception return false.
	 */
	public static boolean parseBoolean(String param) {
		if (nullOrBlank(param)) {
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
	 *          string to convert
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
					&& chars[++i] == 't' && chars[++i] == 't' && chars[++i] == 'p'
					&& (chars[++i] == 's' || chars[--i] == 'p'))
					&& i < len - 4
					&& chars[++i] == ':'
					&& chars[++i] == '/'
					&& chars[++i] == '/') {
				j = ++i;
			}
			if (j > 0) {
				if (index == 0 || (cur = chars[index - 1]) != '\'' && cur != '"'
						&& cur != '<' && cur != '=') {
					cur = chars[j];
					while (j < len) {
						if (cur == ' ' || cur == '\t' || cur == '\'' || cur == '"'
								|| cur == '<' || cur == '[' || cur == '\n' || cur == '\r'
								&& j < len - 1 && chars[j + 1] == '\n') {
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
			} else if (cur == '[' && index < len - 6 && chars[i = index + 1] == 'u'
					&& chars[++i] == 'r' && chars[++i] == 'l'
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
					if (href.indexOf("javascript:") == -1 && href.indexOf("file:") == -1) {
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
	 * Display a string in html page, call methods: escapeHTMLTags, convertURL,
	 * convertNewlines.
	 * 
	 * @param input
	 *          string to display
	 * @return string
	 */
	public static String dspHtml(String input) {
		String str = input;
		str = createBreaks(str, 80);
		str = escapeHTMLTags(str);
		str = convertURL(str);
		str = convertNewlines(str);
		return str;
	}

	/**
	 * 超过80字符自动换行
	 * 
	 * @param input
	 * @return
	 */
	public static String dspHtmlBreak50(String input) {
		String str = input;
		str = createBreaks(str, 50);
		str = escapeHTMLTags(str);
		str = convertURL(str);
		str = convertNewlinesBR(str);
		return str;
	}

	/**
	 * 转换从数据库中取出的记录的中文并用iso8859_1输出
	 */
	public static String gb2iso(String s) {
		String str = "";
		if (s == null) {
			return "";
		}
		try {
			str = new String(s.getBytes("gb2312"), "8859_1");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return doBracket(str);
	}

	/**
	 * 转换从网页传递的中文,然后插入到数据库中
	 */
	public static String iso2gb(String s) {
		String str = "";

		if (s == null) {
			return "";
		}

		try {
			str = new String(s.getBytes("8859_1"), "gb2312");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return doQuote(str);

	}

	/**
	 * 将'转化为''，将<转化为&lt;，将>转化为&gt;
	 */
	public static String doQuote(String str) {
		StringBuffer sbCon = new StringBuffer("");
		int iLen = str.length();
		char[] cCon = str.toCharArray();
		String[] sCon = new String[iLen];
		for (int j = 0; j < iLen; j++) {
			if (cCon[j] == '\'') {
				sCon[j] = "''";
			} else {
				sCon[j] = (new Character(cCon[j])).toString();
			}
			sbCon.append(sCon[j]);
		}
		return (sbCon.toString());
	}

	/**
	 * 将<转化为&lt;，将>转化为&gt;
	 */
	public static String doBracket(String str) {
		StringBuffer sbCon = new StringBuffer("");
		int iLen = str.length();
		char[] cCon = str.toCharArray();
		String[] sCon = new String[iLen];
		for (int j = 0; j < iLen; j++) {
			if (cCon[j] == '<' || cCon[j] == '>') {
				if (cCon[j] == '<') {
					sCon[j] = "&lt;";
				} else {
					sCon[j] = "&gt;";
				}
			} else {
				sCon[j] = (new Character(cCon[j])).toString();
			}
			sbCon.append(sCon[j]);
		}
		return (sbCon.toString());
	}

	public static String genNumberString(int yourNumber, int yourStringLen) {
		int yourNumberCount = String.valueOf(yourNumber).length();
		int zeroLen = yourStringLen - yourNumberCount;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < zeroLen; i++) {
			sb.append("0");
		}
		sb.append(yourNumber);

		return sb.toString();
	}

	/**
	 *判断一个数组中是否有某一对象,如果有,就返回>=0,没有就返回-1 add by lgj
	 * 
	 * @param a
	 * @param key
	 * @return
	 */
	public static int binarySearch(Object[] a, Object key) {
		if (a == null || key == null) {
			return -1;
		}
		int len = a.length;

		for (int i = 0; i < len; i++) {
			if (a[i].equals(key)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 如果集合set2中有的元素在集合set1中出现，就从set1中移出
	 * 
	 * @param a
	 * @param key
	 * @return
	 */
	public static Set removeSet(Set set1, Set set2) {
		if (set2 == null || set2.size() <= 0) {
			return set1;
		}
		Set set3 = set1;
		Object[] obj1 = set1.toArray();
		Object[] obj2 = set2.toArray();
		for (int i = 0; i < obj1.length; i++) {
			for (int j = 0; j < obj2.length; j++) {
				if (obj1[i].equals(obj2[j])) {
					set3.remove(obj2[j]);
				}
			}
		}
		return set3;
	}

	public static int getSize(String input) {
		String str = input;
		int size = 0;
		byte[] b = str.getBytes();
		size = b.length;
		return size;
	}

	/**
	 * 由于实际的需要,现在把string[]中的值先转换成long,然后再
	 * 
	 * 放到list中
	 * 
	 * @param str
	 * @return
	 */
	public static ArrayList str2List(String[] str) {
		if (str == null) {
			return null;
		}
		int len = str.length;
		ArrayList list = new ArrayList();
		for (int i = 0; i < len; i++) {
			list.add(new Long(str[i]));
		}
		return list;
	}

	public static String array2String(String[] str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append(str[i]);
			sb.append(";");
		}
		return sb.toString();
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 左补0
	 * @param value
	 * @param numbers
	 * @return
	 */
	public static String fillLeft(Integer value,int numbers) {
    	StringBuffer str = new StringBuffer();
    	int strLen = value.toString().length(); 
    	if(strLen < numbers){
    		for (int i = 0; i < numbers - strLen; i++) {
				str.append("0");
			}
    	}
    	return str.append(value).toString();
	}
	/**
	 * String数组转成int数组
	 * @param arrs
	 * @return
	 */
	public static Integer[] stringToInt(String[] arrs){
		if(arrs == null || arrs.length == 0){
			return null;
		}
		Integer[] ints = new Integer[arrs.length];
	    for(int i=0;i<arrs.length;i++){
	        ints[i] = Integer.parseInt(arrs[i]);
	    }
	    return ints;
	}
}
