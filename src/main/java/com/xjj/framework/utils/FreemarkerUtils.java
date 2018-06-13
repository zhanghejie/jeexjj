package com.xjj.framework.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtils {
	
	private static FreemarkerUtils instance = new FreemarkerUtils();
	/**
	 * 根据Freemarker模板和数据生成静态文件，默认如果同名则覆盖
	 * @param templateFileName 模板名称，相对于项目根路径
	 * @param propMap  数据
	 * @param fileName 生成文件名
	 * @return 生成文件名
	 */
	public static String writeFile(String templateFileName, Map<String,Object> propMap, String fileName) {
		return writeFile(templateFileName, propMap, fileName, true); 
	}
	/**
	 * 根据Freemarker模板和数据生成静态文件
	 * @param templateFileName 模板名称，相对于项目根路径
	 * @param propMap 数据
	 * @param fileName 生成文件名
	 * @param overwrite 如果文件已存在，是否覆盖
	 * @return 生成文件名
	 */
	public static String writeFile(String templateFileName, Map<String,Object> propMap, String fileName, boolean overwrite) {
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(FileUtils.getROOTPath()));
			cfg.setDefaultEncoding("UTF-8");
			Template t = cfg.getTemplate(templateFileName);
			new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdirs();
			if(overwrite){
				fileName = FileUtils.getValidFileName(fileName);
			}
			File afile = new File(fileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), "UTF-8"));
			t.process(propMap, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return fileName;
	}
	
	
	/**
	 * 获得对应的格式化过的模板内容，如果出错则返回null
	 * @param content 模板内容
	 * @param params 填充的参数
	 * @return 格式化过的内容，出错返回null
	 */
	public static String getMessage(String content ,Map<String,Object> params){
		if(content==null){
			return null;
		}
		//将文字转换成流
		Reader reader = new StringReader(content);
		try{
			Template t = new Template("MessageTemplate",reader,new Configuration(),"UTF-8");
			Writer w = new StringWriter();
			t.process(params, w);
			return w.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 创建传入的参数类
	 * @return
	 */
	public static FreemarkerModel getModel(){
		return instance.new FreemarkerModel();
	}
	
	/**
	 * Freemarker参数的封装类
	 * @author xjj
	 */
	public class FreemarkerModel{
		private Map<String,Object> model = new HashMap<String,Object>();
		private FreemarkerModel(){}
		
		/**
		 * 添加参数
		 * @param key
		 * @param val
		 * @return
		 */
		public FreemarkerModel add(String key, Object val){
			if( !StringUtils.isBlank(key) && val != null){
				model.put(key, val);
			}
			return this;
		}
		
		/**
		 * 转成对应的参数Map
		 * @return
		 */
		public Map<String,Object> toMap(){
			return model;
		}
	}
}
