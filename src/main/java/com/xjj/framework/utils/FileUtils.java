package com.xjj.framework.utils;

/**
 * <p>Title: CompusIMS</p>
 * <p>Description: Compus Information Manage System</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: xjj</p>
 * @author xjj
 * @version 0.1
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xjj.framework.configuration.ConfigUtils;

public class FileUtils {
	
	protected final static Logger logger = Logger.getLogger(FileUtils.class);
	
	public static final String FILE_BASE_PATH = "/files";
	public static final String FILE_UPLOAD_PATH = FILE_BASE_PATH+"/upload";
	public static final String FILE_EXPORT_PATH = FILE_BASE_PATH+"/export";
	public static final String FILE_EDITOR_PATH = FILE_BASE_PATH+"/editor";
	public static final String FILE_TEMP_PATH = "/tmp";
	
    public FileUtils() {
    }

    /**
     * 创建文件夹
     * @param folder
     * @throws Exception
     */
    public static void createFolder(String folder) throws Exception {
      boolean success = true;
      if(StringUtils.isBlank(folder)){
    	  success = false;
      }else{
	     
	      File fileDirectory = new File(folder);
	      if (!fileDirectory.exists()) {
	        success = fileDirectory.mkdirs();
	      }
	      fileDirectory = null;
      }
      if (!success) {
    	  throw new Exception("无法建立文件夹：" + folder);
      }else{
    	  
      }

    }
    /**
     * 创建文件夹
     * @param folder
     * @throws Exception
     */
    public static void createFolder(File file) throws Exception {
      boolean success = true;
      if(file==null){
    	  success = false;
      }else{
    	  String folder=file.getAbsolutePath();
	      if(!folder.endsWith(File.separator) && folder.indexOf(File.separator) > -1){
	    	  folder = folder.substring(0,folder.lastIndexOf(File.separator)+1);
	      }
	      createFolder(folder);
      }
      if (!success) {
    	  throw new Exception("无法建立文件夹：" + file.getAbsolutePath());
      }else{
    	  
      }

    }
    /**
     * 删除目录及目录文件
     * @param path
     */
    public static void deleteFolder(File path) {  
        if (!path.exists())  
            return;  
        if (path.isFile()) {  
            path.delete();  
            return;  
        }  
        File[] files = path.listFiles();  
        for (int i = 0; i < files.length; i++) {  
        	deleteFolder(files[i]);  
        }  
        path.delete();  
    } 
    
    /**
     * 移动文件
     * @param srcFileFullName 源文件路径
     * @param desFolder 目标文件夹
     * @param desFileName 目标文件名
     * @param replace 如果存在是否覆盖
     * @throws Exception
     */
    public static void moveFile(String srcFileFullName, String desFolder, String desFileName, boolean replace) throws Exception{
    	moveFile(new File(srcFileFullName), desFolder, desFileName, replace);
    }
    
    /**
     * 移动文件
     * @param srcFile 源文件
     * @param desFolder 目标文件夹
     * @param desFileName 目标文件名
     * @param replace 如果存在是否覆盖
     * @return
     * @throws Exception
     */
    public static String moveFile(File srcFile, String desFolder, String desFileName, boolean replace) throws Exception {
		boolean success = true;
		createFolder(desFolder);
		String fileName = desFileName;
		if(fileName==null || fileName.equals("")){
			fileName = srcFile.getName();
		}

		String desFileFullName = (new StringBuffer(desFolder)).append("/").append(fileName).toString();
		File desFile = new File(desFileFullName);
		if (replace) {
			desFile.delete();
		} else {
			while (desFile.exists()) {
				fileName = getNextFile(fileName);
				desFileFullName = (new StringBuffer(desFolder)).append("/").append(fileName).toString();
				desFile = new File(desFileFullName);
			}
		}
		srcFile.renameTo(desFile);
		if (!success) {
			throw new Exception(" 无法移动文件:" + srcFile);
		} else {
			if(logger.isDebugEnabled()){
				logger.debug("移动文件成功,目标："+desFileFullName);
			}
			return fileName;
			
		}
	}

    /**
     *  复制文件
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param replace 如果存在是否覆盖
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile, boolean replace) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        if(!sourceFile.exists()||targetFile==null||targetFile.isDirectory()){
        	return;
        }
        if(!targetFile.exists()){
        	try {
				createFolder(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else{
        	if(replace){
        		targetFile.delete();
        	}else{
        		return;
        	}
        }
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     *  复制文件夹
     * @param sourceDir 源文件名
     * @param targetDir 目标文件名
     * @param replace 如果有重名，是否覆盖
     * @throws IOException
     */
    public static void copyDirectiory(String sourceDir, String targetDir, boolean replace) throws IOException {
    	
    	if(!new File(sourceDir).exists()){
        	return;
        }
        if(!new File(targetDir).exists()){
        	 // 新建目标目录
            (new File(targetDir)).mkdirs();
        }else{
        	if(replace){
        		new File(targetDir).delete();
        	}else{
        		return;
        	}
        }
       
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + "/"+ file[i].getName());
                copyFile(sourceFile, targetFile,replace);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2,replace);
            }
        }
    }
    
    /**
     * 删除文件
     * @param srcFileName 文件名
     * @throws Exception
     */
    public static void deleteFile(String srcFileName) throws Exception {
		boolean success = true;
		File file = new File(srcFileName);
		if (file.exists()) {
			file.delete();
		}
		if (!success) {
			throw new Exception( " 无法删除文件：" + srcFileName);
		} else {
			return;
		}
	}
    /**
     * 返回文件名的扩展名
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getFileExt(String fileName){

        if(fileName == null)
            return null;
        int pos = fileName.lastIndexOf('.');
        if(pos > 0){
            return fileName.substring(pos+1, fileName.length());
        }else{
            return "";
        }
    }
    /**
     * 返回带有路径文件名中的文件名部分
     * @param fileName 带有路径的文件全名
     * @return 文件名+扩展名
     */
    public static String getFileName(String fileName){
        if(fileName == null)
            return "";
        int pos1 = fileName.lastIndexOf("/");
        int pos2 = fileName.lastIndexOf("\\");
        int pos = pos1 > pos2 ? pos1 : pos2;
        
        if(pos > -1){
            return fileName.substring(pos+1, fileName.length());
        }else{
            return fileName;
        }
    }
    
    /**
     * 返回带有路径文件名中的文件名部分
     * @param fileName 带有路径的文件名
     * @return 文件名
     */
    public static String getFileShortName(String fileName){
    	String name = getFileName(fileName);
        if(name == null )
            return "";
        int pos = name.lastIndexOf('.');
        if(pos > 0){
            return name.substring(0, pos);
        }else{
            return name;
        }
    }
    
    /**
     * 返回下一个文件名，如果包含[index]，则index加一，否则返回文件名[1]
     * @param fileName 原始文件名
     * @return 下一个文件名
     */
    public static String getNextFile(String fileName){
    	StringBuffer sb = new StringBuffer();
    	//分别获得文件的名称和扩展名
    	String ext = getFileExt(fileName);
    	String shortName = getFileShortName(fileName);
    	//得到[的位置，如果包含[和]则认为文件后面有脚标
    	int pos = shortName.lastIndexOf('[');
    	if(shortName.endsWith("]") && pos>0){
    		String sureName = shortName.substring(0,pos);
    		int index = Integer.valueOf(shortName.substring(pos+1,shortName.length()-1));
    		sb.append(sureName).append("[").append(index+1).append("]");
    	}else{
    		sb.append(shortName).append("[1]");
    	}
    	//如果含有扩展名则加入
    	if(ext!=null && !ext.equals("")){
    		sb.append(".").append(ext);
    	}
    	return sb.toString();
    }
    /**
     * 获得有效的文件名，如果重名，则在文件名后面加入[index]
     * @param fileName 文件名
     * @return 不重名的文件名
     */
    public static String getValidFileName(String fileName){
    	File file = new File(fileName);
    	while(file.exists()){
    		fileName = getNextFile(fileName);
    		file = new File(fileName);
    	}
    	return fileName;
    }
    
    
    
    /* ***************************************************************************
     * 关于路径的方法
     ************************************************************************** */
   
    /**
     * 返回文件上传的相对路径，以“/”结束
     * @param paths 子路径
     * @return
     */
    public static String getUploadPath(String...paths){
    	return getPath(FILE_UPLOAD_PATH,paths);
    }
    /**
     * 返回文件上传的绝对路径，以“/”结束
     * @param paths 子路径
     * @return
     */
    public static String getUploadRealPath(String...paths){
    	return getRealPath(FILE_UPLOAD_PATH,paths);
    }
    /**
     * 返回文件导出的相对路径，以“/”结束
     * @param paths 子路径
     * @return
     */
    public static String getExportPath(String...paths){
    	return getPath(FILE_EXPORT_PATH,paths);
    }
    /**
     * 返回文件导出的绝对路径，以“/”结束
     * @param paths 子路径
     * @return
     */
    public static String getExportRealPath(String...paths){
    	return getRealPath(FILE_EXPORT_PATH,paths);
    }
    /**
     * 返回编辑器上传文件的相对路径，以“/”结束
     * @param paths 子路径
     * @return
     */
    public static String getEditorPath(String...paths){
    	return getPath(FILE_EDITOR_PATH,paths);
    }
    /**
     * 返回编辑器上传文件的绝对路径，以“/”结束
     * @param paths 子路径
     * @return
     */
    public static String getEditorRealPath(String...paths){
    	return getRealPath(FILE_EDITOR_PATH,paths);
    }
    /**
     * 返回临时文件的绝对路径，以“/”结束
     * @param paths 子路径
     * @return
     */
    public static String getTempRealPath(String...paths){
    	return getRealPath(FILE_TEMP_PATH,paths);
    }
    
    /**
     * 返回临时文件的绝对路径，以“/”结束
     * @param useUUID 子路径使用UUID
     * @return
     */
    public static String getTempRealPath(boolean useUUID){
    	if(useUUID){
    		UUID uuid = UUID.randomUUID();
    		return getRealPath(FILE_TEMP_PATH,uuid.toString());
    	}
    	return getRealPath(FILE_TEMP_PATH,"");
    }
    
    /**
     * 返回临时文件的绝对路径，以“/”结束
     * @param useUUID 子路径使用UUID
     * @return
     */
    public static String getTempRealPath(String path, boolean useUUID){
    	if(useUUID){
    		UUID uuid = UUID.randomUUID();
    		return getRealPath(FILE_TEMP_PATH,path,uuid.toString());
    	}
    	return getRealPath(FILE_TEMP_PATH,path);
    }
    
    /**
     * 返回工程的根路径，如果是java工程，返回class所在的根路径
     * @return
     */
    public static String getROOTPath(){
    	String path = ConfigUtils.get("path.ROOT", "");
    	if(StringUtils.isBlank(path)){
    		RequestAttributes ras = RequestContextHolder.getRequestAttributes();
    		if(ras != null){
	    		HttpServletRequest request = ((ServletRequestAttributes)ras).getRequest();
	    		if(request != null && request.getSession() != null){
	    			path = request.getSession().getServletContext().getRealPath("/");
	    		}
    		}
    	}
    	if(StringUtils.isBlank(path)){
    		path = FileUtils.class.getClassLoader().getResource(".").getPath();
    		if(path.indexOf("/WEB-INF/") != -1){
    			path = path.substring(0,path.indexOf("/WEB-INF/") + 1);
    		}
    		if(path.startsWith("/") && path.contains(":")){
    			path = path.substring(1);
    		}
    	}
    	
    	return path.replaceAll("\\\\", "/");
    }
    
    /**
     * 文件的真实全路径转化为 相对路径
     * @param realPath
     * @return
     */
    public static String realPath2Path(String realPath){
    	//项目的根目录
    	String rootPath = getROOTPath();
    	if(realPath.startsWith(rootPath)){
    		return realPath.substring(rootPath.length()-1);
    	}
    	return realPath;
    }
    
    /**
     * 返回文件的相对路径，以“/”结束
     * @param basePath 基础路径，区分上传、导出、编辑器等
     * @param paths 子路径
     * @return
     */
    private static String getPath(String basePath, String...paths){
    	StringBuilder builder = new StringBuilder(basePath);
    	boolean end_add = false;
    	if(paths != null && paths.length > 0){
    		for(String path : paths){
    			if(!path.startsWith("/")){
    				builder.append("/");
    			}
    			builder.append(path);
    			end_add = !path.endsWith("/");
    		}
    	}
    	if(end_add){
    		builder.append("/");
    	}
    	
    	return builder.toString();
    }
    /**
     * 返回文件的绝对路径，以“/”结束
     * @param basePath 基础路径，区分上传、导出、编辑器等
     * @param paths 子路径
     * @return
     */
    private static String getRealPath(String basePath, String...paths){
    	String rootPath = getROOTPath();
    	StringBuilder builder = new StringBuilder();
    	if(rootPath.endsWith("/") ){
    		builder.append(rootPath.substring(0, rootPath.length()-1));
    	}else{
    		builder.append(rootPath);
    	}
    	
    	String uploadPath = getPath(basePath, paths);
    	builder.append(uploadPath);
    	if(!uploadPath.endsWith("/") ){
    		builder.append("/");
    	}
    	try {
			createFolder(builder.toString());
		} catch (Exception e) { }
    	return builder.toString();
    }
    
    
    /**
     * 返回日期格式的文件路径，例如2013/07/30
     * @return
     */
    public static String getDatePath(){
    	return DateTimeUtils.getCurrentDateTimeString("yyyy/MM/dd");
    }
    /**
     * 返回日期时间格式的文件路径，例如2013/07/30/20/49
     * @return
     */
    public static String getDateTimePath(){
    	return DateTimeUtils.getCurrentDateTimeString("yyyy/MM/dd/HH/mm");
    }
    
    public static void main(String[] args){
    	System.out.println(getDatePath());
    	System.out.println(getDateTimePath());
    	System.out.println(getFileName("d:/abc/efg/cdf.exe"));
    	System.out.println(getFileShortName("d:/abc/efg/cdf.exe"));
    	System.out.println(getFileExt("d:/abc/efg/cdf.exe"));
    	String rp = getEditorRealPath("abc/efg");
    	System.out.println("realpath=" + rp);
    	System.out.println("path=" + realPath2Path(rp));
    }
}
