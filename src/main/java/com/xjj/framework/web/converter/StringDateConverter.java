package com.xjj.framework.web.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;


public class StringDateConverter implements Converter<String, Date> {
	
	private static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static String DATE_SHORT_FORMAT = "yyyy-M-d";
	private static String DATE_FORMAT = "yyyy-MM-dd";
	
	public Date convert(String text) throws IllegalArgumentException {
		
		SimpleDateFormat dateFormat = null;	
		
		if (StringUtils.hasText(text)) {
			try {
				if (text.indexOf(":") == -1) {
					if(text.length() == 10){
						dateFormat = new SimpleDateFormat(DATE_FORMAT);
						return dateFormat.parse(text);
					}else if (text.length() < 10 && text.length() > 7){
						dateFormat = new SimpleDateFormat(DATE_SHORT_FORMAT);
						return dateFormat.parse(text);
					}else{
						throw new IllegalArgumentException("Could not parse date, date format must is yyyy-MM-dd ");
					}
				} else {
					if(text.length() == 19){
						dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
						return dateFormat.parse(text);
					} else if (text.length() == 21) {
						text = text.replace(".0", "");
						dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
						return dateFormat.parse(text);
					} else {
						throw new IllegalArgumentException("Could not parse datetime, datedate format must is yyyy-MM-dd HH:mm:ss ");
					}
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		}
		
		return null;
	}
}
