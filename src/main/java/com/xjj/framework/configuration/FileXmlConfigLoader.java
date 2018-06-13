package com.xjj.framework.configuration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class FileXmlConfigLoader implements ConfigLoader{
	
	protected static final Logger logger = Logger.getLogger(FileXmlConfigLoader.class);
	
	private String fileName = "/config/config.xml";
	
	public FileXmlConfigLoader(){
    }
	public FileXmlConfigLoader(String fileName){
    	this.fileName = fileName;
   	}

	public Properties load() throws Exception{
		Properties properties = new Properties();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputStream is = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			is = getClass().getResourceAsStream(fileName);
			Document doc = builder.parse(is, "UTF-8");
			Element root = doc.getDocumentElement();

			//开始获取所有的package节点
			NodeList configList = root.getElementsByTagName("package");
			for(int i = 0; i < configList.getLength(); i++){
				Element configElement = (Element)configList.item(i);
				String package_name = configElement.getAttribute("name");
				if(package_name != null && !package_name.equals("")){
					package_name += ".";
				}else{
					package_name = "";
				}
				
				NodeList propertyList = configElement.getElementsByTagName("property");
				for(int j = 0; j < propertyList.getLength(); j++){
					Element propertyElement = (Element)propertyList.item(j);
					properties.put(package_name + propertyElement.getAttribute("name"),propertyElement.getTextContent());
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//throw new Exception("Can't Read Properties from File:" + fileName);
			if(logger.isDebugEnabled()){
    			logger.debug("Can't Read Properties from File:" + fileName);
    		}
		}finally{
			try{
				is.close();
			}catch(IOException ioe){
				//ioe.printStackTrace();
			}
		}
		return properties;
	}
	
	public void save(Properties properties) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		OutputStream os = new FileOutputStream(this.fileName);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("config");
			doc.appendChild(root);
			
			Map<String,Element> keyPathMap = new HashMap<String,Element>();
			for(String key : properties.stringPropertyNames()){
				String value = properties.getProperty(key);
				String key_pack = key.substring(0, ((String)key).indexOf("."));
				String key_prop = key.substring(key.indexOf("."));
				Element pack = keyPathMap.get(key_pack);
				if(pack==null){
					pack = doc.createElement("package");
					pack.setAttribute("name", key_pack);
					root.appendChild(pack);
					keyPathMap.put(key_pack, pack);
				}
				Element prop = doc.createElement("property");
				prop.setAttribute(key_prop, value);
				pack.appendChild(prop);
			}
		
		
		
		    OutputStreamWriter outwriter = new OutputStreamWriter(os);
		    Source source = new DOMSource(doc);
	    	Result result = new StreamResult(outwriter);
	    	Transformer xformer = TransformerFactory.newInstance().newTransformer();
		    xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    xformer.transform(source, result);
		    outwriter.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    throw new Exception("Can't Save Properties to File:" + fileName + "! Disk IO Error");
		}catch (Exception e) {
		    e.printStackTrace();
		    throw new Exception("Can't Save Properties to File:" + fileName);
	    }
	}

}
