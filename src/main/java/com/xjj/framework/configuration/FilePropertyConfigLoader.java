package com.xjj.framework.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class FilePropertyConfigLoader implements ConfigLoader {
 
	protected static final Logger logger = Logger.getLogger(FilePropertyConfigLoader.class);
	
	private String fileName = "/config/config.properties";
	
	public FilePropertyConfigLoader(){
    }
	public FilePropertyConfigLoader(String fileName){
    	this.fileName = fileName;
   	}

	public Properties load() throws Exception{
		Properties properties = new Properties();
    	try {
    		InputStream is = getClass().getResourceAsStream(fileName);
    		properties.load(is);
    	} catch (Exception e) {
    		//e.printStackTrace();
    		//throw new Exception("Can't Read Properties from File:" + fileName);
    		if(logger.isDebugEnabled()){
    			logger.debug("Can't Read Properties from File:" + fileName);
    		}
    	}
    	return properties;
    }
	
	public void save(Properties properties) throws Exception {
		try {
			File propFile = new File(this.fileName);
			properties.store(new FileOutputStream(propFile), "");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Can't Save Properties to File:" + fileName);
		}
	}
}
