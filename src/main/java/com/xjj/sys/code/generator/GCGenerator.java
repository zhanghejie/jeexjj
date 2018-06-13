package com.xjj.sys.code.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.xjj.sys.code.GCConfig;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GCGenerator {
	
	
	/**
	 * 生成代码
	 * @param model
	 */
	public static void generateCode(GCModel model) {
		
		Map<String,Object> propMap = new HashMap<String,Object>();
		propMap.put("model", model);
		
		String templateFileName = "web/Controller.java.ftl";
		String fileName = model.getName() + "Controller.java";
		generate(templateFileName, propMap, GCConfig.getInstance().getSrcDir() + package2path(model.getPackageForAction()) + "/" + fileName);
		
		
		templateFileName = "entity/Entity.java.ftl";
		fileName = model.getName() + "Entity.java";
		generate(templateFileName, propMap, GCConfig.getInstance().getSrcDir() + package2path(model.getPackageForModel()) + "/" + fileName);
		
	
		templateFileName = "service/Service.java.ftl";
		fileName = model.getName() + "Service.java";
		generate(templateFileName, propMap, GCConfig.getInstance().getSrcDir() + package2path(model.getPackageForService()) + "/" + fileName);
		
		templateFileName = "service/ServiceImpl.java.ftl";
		fileName = model.getName() + "ServiceImpl.java";
		generate(templateFileName, propMap, GCConfig.getInstance().getSrcDir() + package2path(model.getPackageForServiceImpl()) + "/" + fileName);
	
		
		templateFileName = "dao/Dao.java.ftl";
		fileName = model.getName() + "Dao.java";
		generate(templateFileName, propMap, GCConfig.getInstance().getSrcDir() + package2path(model.getPackageForDAO()) + "/" + fileName);
		
		templateFileName = "mapper/Dao.xml.ftl";
		fileName = model.getName() + "Dao.xml";
		generate(templateFileName, propMap, GCConfig.getInstance().getMapperDir() + "/"+model.getModule()+"/" + fileName);
		
		String path = GCConfig.getInstance().getWebDir() + uncapFirst(model.getRequestMapping())+"/";
		templateFileName = "view/" + GCConfig.getInstance().getViewTemplateDir() + "modelInput.ftl";
		fileName = underScoreCase(uncapFirst(model.getName())) + "-input.ftl";
		generate(templateFileName, propMap,  path + fileName);
		
		templateFileName = "view/" + GCConfig.getInstance().getViewTemplateDir() + "modelList.ftl";
		fileName = underScoreCase(uncapFirst(model.getName())) + "-list.ftl";
		generate(templateFileName, propMap,  path + fileName);
		
		templateFileName = "view/" + GCConfig.getInstance().getViewTemplateDir() + "modelIndex.ftl";
		fileName = underScoreCase(uncapFirst(model.getName())) + "-index.ftl";
		generate(templateFileName, propMap,  path + fileName);
	
	}
	
	
	/**
	 * 按模版生成代码
	 * @param templateFileName
	 * @param propMap
	 * @param fileName
	 */
	public static void generate(String templateFileName, Map<String,Object> propMap,String fileName) {
		
		try {
			Template t = GCConfig.getInstance().getConfiguration().getTemplate(templateFileName);
			
			 
			new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdirs();
			File afile = new File(fileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), "UTF-8"));
			t.process(propMap, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static String package2path(String packageName) {
		String path = packageName.replace('.', '/');
		if(!path.endsWith("/")){
			path += "/";
		}
		return path;
	}

	protected static String capFirst(String string) {
		if(string==null || string.equals("")){
			return "";
		}
		if(string.length()==1){
			return string.toUpperCase();
		}
		String s = String.valueOf(string.charAt(0)).toUpperCase();
		s = s + string.substring(1);
		return s;
	}

	protected static String uncapFirst(String string) {
		if(string==null || string.equals("")){
			return "";
		}
		if(string.length()==1){
			return string.toLowerCase();
		}
		String s = String.valueOf(string.charAt(0)).toLowerCase();
		s = s + string.substring(1);
		return s;
	}
	
	protected static String underScoreCase(String string) {
		String SEPARATOR = "-";
		if (string == null || string.equals("")) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);

			boolean nextUpperCase = true;

			if (i < (string.length() - 1)) {
				nextUpperCase = Character.isUpperCase(string.charAt(i + 1));
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
}
