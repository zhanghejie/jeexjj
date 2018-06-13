package com.xjj.framework.configuration;

import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigUtils {
	
	protected static final Logger logger = Logger.getLogger(ConfigUtils.class);
	
	private Properties properties = new Properties();
	private static ConfigUtils configUtils = new ConfigUtils();
	private ConfigLoader[] configLoaders = new ConfigLoader[]{new FileXmlConfigLoader(),new FilePropertyConfigLoader()};
	
	private ConfigUtils(){
		this.load();
	}
	
	public static void reload(){
		configUtils.properties.clear();
		configUtils.load();
	}
	
	public static Collection<String> getKeys() {
		return configUtils.properties.stringPropertyNames();
	}

	public static String get(String key) {
		String value = configUtils.properties.getProperty(key);
		if(value == null){
			return null;
		}else{
			return configUtils.transPath(value);
		}
	}
	
    public static String get(String key,String defaultValue){
        String value = get(key);
        if(value==null || value.equals("")){
        	return defaultValue;
        }
        return value;
    }

    public static int getInt(String key, int defaultValue) {
		String value = get(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		int num = defaultValue;
		try {
			num = Integer.parseInt(value);
		} catch (Exception ignored) {
			logger.error(ignored.getMessage());
		}
		return num;
	}
    public static long getLong(String key, long defaultValue) {
		String value = get(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		long num = defaultValue;
		try {
			num = Long.parseLong(value);
		} catch (Exception ignored) {
			logger.error(ignored.getMessage());
		}
		return num;
	}

    public static long getTime(String key, long defaultValue) {
		return configUtils.transTime(get(key), defaultValue);

	}

    public static boolean getBoolean(String key, boolean defaultValue) {
		String value = get(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		switch (value.charAt(0)) {
			case '1':
			case 'y':
			case 'Y':
			case 't':
			case 'T':
				return true;
		}
		return false;
	}
	
    private synchronized void load(){
		for(ConfigLoader loader : configLoaders){
			try{
				Properties props = loader.load();
				this.properties.putAll(props);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
    
	private String transPath(String path) {
		int pos, oldpos;
		String replace;
		StringBuffer out = new StringBuffer();
		oldpos = 0;

		if (path != null) {
			pos = path.indexOf("%");
			while (pos > -1) {
				out.append(path.substring(oldpos, pos));
				oldpos = pos;
				pos = path.indexOf("%", pos + 1);
				if (pos == -1)
					pos = path.length();
				String dir = path.substring(oldpos + 1, pos);
				
				replace = get("path." + dir);
				if(replace == null)
					replace = get(dir);
				if(replace == null)
					replace = dir;
				
				out.append(replace);
				oldpos = pos + 1;
				pos = path.indexOf("%", pos + 1);
			}
			out.append(path.substring(oldpos, path.length()));
		}
		return out.toString();
	}
	
	private long transTime(String value, long defaultValue) {
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		long num = defaultValue;
		long multiple = 1;
		try {
			if (value.toLowerCase().endsWith("h")) {
				multiple = 60 * 60 * 1000;
				value = value.substring(0, value.length() - 1);
			} else if (value.toLowerCase().endsWith("m")) {
				multiple = 60 * 1000;
				value = value.substring(0, value.length() - 1);
			} else if (value.toLowerCase().endsWith("s")) {
				multiple = 1000;
				value = value.substring(0, value.length() - 1);
			}
			num = Long.parseLong(value) * multiple;
		} catch (Exception ignored) {
			logger.error(ignored.getMessage());
		}
		return num;
	}
}
