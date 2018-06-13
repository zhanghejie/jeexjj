package com.xjj.sys.xfile.web;

import java.util.ArrayList;
import java.util.List;


import com.xjj.SpringBeanLoader;
import com.xjj.framework.utils.StringUtils;
import com.xjj.sys.xfile.entity.XfileEntity;
import com.xjj.sys.xfile.service.XfileService;

/**
 * 文件信息管理类
 * @author Administrator
 *
 */
public class FileInfo {
	
	private XfileService xfileService = (XfileService)SpringBeanLoader.getBean("xfileServiceImpl");
	private List<FileInfoNode> nodes = new ArrayList<FileInfoNode>();//文件信息列表
	
	/**
	 * 得到文件信息列表
	 * @return  文件信息列表
	 */
	public List<FileInfoNode> getNodes() {
		return nodes;
	}

	/**
	 * 设置文件信息列表
	 * @param nodes 文件信息列表
	 */
	public void setNodes(List<FileInfoNode> nodes) {
		this.nodes = nodes;
	}

	private FileInfo(){}
	
	/**
	 * 新建一个文件信息对象
	 * @param fileId
	 * @param fileName
	 * @return 文件信息对象
	 */
	public static FileInfo newFileInfo(Long fileId,String fileName){
		FileInfo fileInfo = new FileInfo();
		return fileInfo.addFileInfo(fileId,fileName);
	}
	
	/**
	 * 新建一个文件信息对象
	 * @param fileId
	 * @return 文件信息对象
	 */
	public static FileInfo newFileInfo(Long fileId){
		FileInfo fileInfo = new FileInfo();
		return fileInfo.addFileInfo(fileId);
	}
	
	/**
	 * 新建一个文件信息对象，并添加到文件信息列表
	 * @param fileId
	 * @param fileName
	 * @return 文件信息对象
	 */
	public FileInfo addFileInfo(Long fileId,String fileName){
		if(fileId == null || fileId.longValue() <=0){
			return this;
		}
		if(StringUtils.isBlank(fileName)){
			XfileEntity file = xfileService.getById(fileId);
			if(file != null){
				fileName = file.getFileTitle();
			}
		}
		
		nodes.add(new FileInfoNode(fileId,fileName));
		
		return this;
	}
	/**
	 * 新建一个文件信息对象，并添加到文件信息列表
	 * @param fileId
	 * @return 文件信息对象
	 */
	public FileInfo addFileInfo(Long fileId){
		return addFileInfo(fileId,null);
	}
	
	/**
	 * 得到文件信息列表里所有的文件id
	 * @return 文件信息列表里所有的文件id
	 */
	public String getFileIds(){
		if(nodes == null || nodes.size() == 0){
			return "";
		}
		StringBuilder builder = null;
		for(FileInfoNode node : nodes){
			if(builder == null){
				builder = new StringBuilder();
				builder.append(node.getFileId());
			}else{
				builder.append(",").append(node.getFileId());
			}
		}
		return builder.toString();
	} 
	public boolean isHasFiles(){
		if(nodes == null || nodes.size() == 0){
			return false;
		}
		return true;
	}
	/**
	 * 得到文件信息列表里所有的文件信息
	 * @return 文件信息列表里所有的文件信息
	 */
	public String getFileInfos(){
		if(nodes == null || nodes.size() == 0){
			return "[]";
		}
		StringBuilder builder = null;
		for(FileInfoNode node : nodes){
			if(builder == null){
				builder = new StringBuilder("[");
				builder.append("{'fileId':'").append(node.getFileId()).append("','fileName':'").append(node.getFileName()).append("'}");
			}else{
				builder.append(",{'fileId':'").append(node.getFileId()).append("','fileName':'").append(node.getFileName()).append("'}");
			}
		}
		return builder.append("]").toString();
	} 
	
	/**
	 * 文件信息列表类
	 * @author Administrator
	 *
	 */
	public class FileInfoNode{
		private Long fileId;//文件主键
		private String fileName;//文件自定义名称
		
		/**
		 * 构造函数
		 * @param fileId 文件主键
		 * @param fileName 文件自定义京城
		 */
		public FileInfoNode(Long fileId, String fileName) {
			super();
			this.fileId = fileId;
			this.fileName = fileName;
		}
		/**
		 * 文件主键
		 * @return 文件主键
		 */
		public Long getFileId() {
			return fileId;
		}
		/**
		 * 文件自定义名称
		 * @return 文件自定义名称
		 */
		public String getFileName() {
			return fileName;
		}
	}
}
