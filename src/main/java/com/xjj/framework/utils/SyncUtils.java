package com.xjj.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xjj.framework.configuration.ConfigUtils;


public class SyncUtils {
	
	protected static final Logger logger = Logger.getLogger(SyncUtils.class);
	
	private static final SyncUtils instance = new SyncUtils();
	
	private Sender sender;
	
	private SyncUtils(){
		sender = new Sender(this);
		sender.setDaemon(true);
		sender.start();
	}
	/**
	 * 将配置信息的修改发布出去
	 */
	public static void refreshConfig(){
		instance.syncConfig();
	}
	/**
	 * 将权限信息的修改发布出去
	 */
	public static void refreshSecurity(){
		instance.syncSecurity();
	}
	
	protected void syncConfig(){
		String configUrl = ConfigUtils.get("cluster.BROKER_LIST","");
		String[] urlList = configUrl.split(",");
		if(urlList != null){
			for(String url : urlList){
				if(url !=null && !url.trim().equals("")){
					sender.addConfigURL(url);
				}
			}
			//sender.begin();
		}
	}
	
	protected void syncSecurity(){
		String configUrl = ConfigUtils.get("cluster.BROKER_LIST","");
		String[] urlList = configUrl.split(",");
		if(urlList != null){
			for(String url : urlList){
				if(url !=null && !url.trim().equals("")){
					sender.addSecurityURL(url);
				}
			}
			//sender.begin();
		}
	}

	/**
	 * 内部线程类，负责发送配置和权限的信息已经更改
	 * @author 曹新龙
	 *
	 */
	private class Sender extends Thread {
		private SyncUtils sync;
		
		private List<String> configList = new ArrayList<String>();
		private List<String> securityList = new ArrayList<String>();
		
		
		public void addConfigURL(String configURL) {
			synchronized(configList){
				configList.add(configURL);
			}
			if(logger.isDebugEnabled()){
				logger.debug("向同步线程添加配置信息修改的服务器地址 url="+configURL);
			}
			begin();
		}

		public void addSecurityURL(String securityURL) {
			synchronized(securityList){
				securityList.add(securityURL);
			}
			if(logger.isDebugEnabled()){
				logger.debug("向同步线程添加权限信息修改的服务器地址 url="+securityURL);
			}
			begin();
		}

		public Sender(SyncUtils sync){
			this.sync = sync;
			if(logger.isDebugEnabled()){
				logger.debug("同步线程初始化完成。 sync is " + sync);
			}
		}
		
		public synchronized void begin(){
			if(logger.isDebugEnabled()){
				logger.debug("唤醒线程");
			}
			notify();
		}

		public void run() {
			while(sync != null){
				try {
					if(!configList.isEmpty()){
						String url;
						synchronized(configList){
							url = configList.remove(0);
						}
						url = "http://" + url + "/cmd/refresh.jspa?target=config";
						String rtn = sendGET(url);
						if(logger.isDebugEnabled()){
							logger.debug("更新服务器"+url+"的配置信息，结果：" + rtn);
						}
					}else if(!securityList.isEmpty()){
						String url;
						synchronized(securityList){
							url = securityList.remove(0);
						}
						url = "http://" + url + "/cmd/refresh.jspa?target=security";
						String rtn = sendGET(url);
						if(logger.isDebugEnabled()){
							logger.debug("更新服务器"+url+"的权限信息，结果：" + rtn);
						}
					}else{
						try {
							if(logger.isDebugEnabled()){
								logger.debug("configList 和 securityList 都为空，线程等待");
							}
							synchronized(this){
								wait();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception pe) {
					pe.printStackTrace();
				}
			}
			
		}
		
		private String sendGET(String url){
			String allstr = "";

			try {
				URL httpurl = new URL(url);
				BufferedReader in = new BufferedReader(new InputStreamReader(httpurl.openConnection().getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {
					allstr += line;
				}
				in.close();
			} catch (IOException e) {}
			return allstr;
		}
		
		@SuppressWarnings("unused")
		private String sendPOST(String url,String param) {
			String allstr = "";

			try {
				URL httpurl = new URL(url);
				HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				PrintWriter out = new PrintWriter(httpConn.getOutputStream());
				out.print(param);
				out.flush();
				out.close();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {
					allstr += line;
				}
				in.close();
			} catch (IOException e) {}
			return allstr;
		}
		
	} 


}
