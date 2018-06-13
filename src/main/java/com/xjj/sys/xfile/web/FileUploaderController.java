package com.xjj.sys.xfile.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xjj.common.XJJConstants;
import com.xjj.framework.utils.DateTimeUtils;
import com.xjj.framework.utils.FileUtils;
import com.xjj.framework.utils.StringUtils;
import com.xjj.framework.web.SpringControllerSupport;
import com.xjj.sys.xfile.entity.XfileEntity;
import com.xjj.sys.xfile.service.XfileService;


@Controller
@RequestMapping("/sys/fileupload")
public class FileUploaderController extends SpringControllerSupport {

	@Autowired
	private XfileService xfileService;
	final Logger log = LoggerFactory.getLogger(FileUploaderController.class);
	
    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	@RequestMapping("/upload")
    public @ResponseBody void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean uploadSuccess = true;
		
		//获得传递的参数
		String filePath = request.getParameter("path");//文件的存放路径
		boolean format = StringUtils.parseBoolean(request.getParameter("format"));//格式化文件名
		boolean overwrite = StringUtils.parseBoolean(request.getParameter("overwrite"));//文件名重名，是否覆盖
		boolean datepath = StringUtils.parseBoolean(request.getParameter("datepath"));//路径名称加入日期
		
		String uploadPath = FileUtils.getUploadRealPath(filePath);//存放的真实路径
		if(datepath){
			uploadPath += FileUtils.getDatePath()+"/";
		}
		String fileFullPath = "";//文件存储的名
		String fileName = "";//文件原始的名字
		//创建文件路径
		try {
			FileUtils.createFolder(uploadPath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
        fileName = request.getHeader("X-File-Name");
        
        //如果文件格式不合法
        if(!isValidSuffix(fileName))
        {
        	PrintWriter writer = response.getWriter();
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	writer.print("{\"success\": false}");
            writer.flush();
            writer.close();
            return;
        }
        
        if(fileName != null){//使用ajax提交的
        	fileName = URLDecoder.decode(fileName,"utf-8"); 
        	fileFullPath = getFileValidName( uploadPath,fileName, format, overwrite);
	        
	        InputStream is = null;
	        FileOutputStream fos = null;
	        
	        try {
	            is = request.getInputStream();
	            fos = new FileOutputStream(new File(fileFullPath));
	            IOUtils.copy(is, fos);
	        } catch (FileNotFoundException ex) {
	        	uploadSuccess = false;
	        	if(logger.isDebugEnabled()){
	        		ex.printStackTrace();
	        	}
	        } catch (Exception ex) {
	        	uploadSuccess = false;
	        	if(logger.isDebugEnabled()){
	        		ex.printStackTrace();
	        	}
	        } finally {
	            try {
	                fos.close();
	                is.close();
	            } catch (IOException ignored) {
	            	
	            }
	        }
	        
        }else{
        	//使用iframe提交
        	DiskFileItemFactory fac = new DiskFileItemFactory();
    		ServletFileUpload upload = new ServletFileUpload(fac);
    		upload.setHeaderEncoding("utf-8");
    		List<FileItem> fileList = null;
    		try {
    			fileList = upload.parseRequest(request);
    			Iterator<FileItem> it = fileList.iterator();
    			while (it.hasNext()) {
        			FileItem item = it.next();
        			if (!item.isFormField()) {
        				fileName = FileUtils.getFileName(item.getName());
        				fileName = URLDecoder.decode(fileName,"utf-8"); 
//        				size = item.getSize();
//        				type = item.getContentType();
        				if (StringUtils.isBlank(fileName)) {
        					continue;
        				}
        	
        				fileFullPath = getFileValidName( uploadPath,fileName, format, overwrite);
        				item.write(new File(fileFullPath));
        			}
        		}
    		} catch (FileUploadException ex) {
	        	uploadSuccess = false;
	        	if(logger.isDebugEnabled()){
	        		ex.printStackTrace();
	        	}
    		} catch (Exception ex) {
	        	uploadSuccess = false;
	        	if(logger.isDebugEnabled()){
	        		ex.printStackTrace();
	        	}
			}
        }
        
      //保存成功以后，存储记录
        XfileEntity xfile = new XfileEntity();
		xfile.setFilePath(fileFullPath);
		xfile.setUrl(FileUtils.realPath2Path(fileFullPath));
		xfile.setFileRealname(FileUtils.getFileName(fileFullPath));
		xfile.setFileTitle(fileName);
		File fileLocal = new File(fileFullPath);
		xfile.setFileSize(Long.valueOf(fileLocal.length()));
		
		xfile.setCreateDate(new Date());
		if(null != fileName)
		{
			//扩展名
			xfile.setExtensionName(fileName.substring(fileName.lastIndexOf(".")));
		}
		if(null !=getManagerInfo())
		{
			xfile.setUserId(getManagerInfo().getUserId());
		}
		
			
		Long fileId = xfileService.save(xfile);
        
        PrintWriter writer = response.getWriter();
        if(uploadSuccess){
        	response.setStatus(HttpServletResponse.SC_OK);
            writer.print("{\"success\": true,\"fileId\":\""+fileId+"\"}");
        }else{
        	 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             writer.print("{\"success\": false}");
        }
        writer.flush();
        writer.close();
    }

	@RequestMapping("/delete/{fileId}")
    public @ResponseBody void delete(@PathVariable("fileId") Long fileId,@RequestParam(defaultValue="false") Boolean realDelete, HttpServletResponse response) throws Exception {
		
		boolean deleteSuccess = true;
		Long id = 0L;
        if(fileId == null){
        	deleteSuccess = false ;
        }else{
        	XfileEntity fileManager = xfileService.getById(fileId);
			if(fileManager != null){
				id = fileManager.getId();
				if(realDelete){
					FileUtils.deleteFile(fileManager.getFilePath());
					xfileService.delete(fileManager);
				}else{
					fileManager.setIsDeleted(XJJConstants.COMMON_YESNO_YES);
					xfileService.update(fileManager);
				}
			}
        }
        
        PrintWriter writer = response.getWriter();
        if(deleteSuccess){
        	response.setStatus(HttpServletResponse.SC_OK);
            writer.print("{\"success\": true,\"fileId\":\"" + id + "\"}");
        }else{
        	 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             writer.print("{\"success\": false}");
        }
        writer.flush();
        writer.close();
    }
	private String getFileValidName(String filePath, String fileName,boolean format, boolean overwrite ){
		String fileValidName;
		int fileRandom =  Double.valueOf(Math.random()*100).intValue();
		if(format){
			String fileExt = FileUtils.getFileExt(fileName);
			fileValidName =  filePath + DateTimeUtils.getCurrentDateTimeString("yyyyMMddHHmmsss") +fileRandom+ (fileExt==null?"":"."+fileExt);
    	}else{
    		fileValidName =  filePath + fileName;
    	}
		if( !overwrite ){
			fileValidName = FileUtils.getValidFileName(fileValidName);
		}
		return fileValidName;
	}
	
	
	//private static final List<String> BLACK_LIST_SUFFIX = Arrays.asList(new String[]{"jsp","html","htm","css","js","ftl","vm","java","class","exe","bat","sh"});
	private static final List<String>  WHITE_LIST_SUFFIX = Arrays.asList(new String[]{"bmp","png","jpeg","jpg","gif","rar","zip","mp3","mp4","avi","mpg","mpeg","rm","rmvb","mov","wmv","wav","ogg","ogg","asf","pdf","txt","doc","docx","ppt","ppx","xls","xlsx","apk","ipa"});
	
	/**
	 * 判断是否为有效的文件格式
	 * @param fileOrFixName  文件名或者文件后缀
	 * @return 
	 */
	private boolean isValidSuffix(String fileOrFixName)
	{
		if(null == fileOrFixName || "".equals(fileOrFixName))
		{
			return false;
		}
		String fixName = fileOrFixName;
		if(fileOrFixName.contains("."))
		{
			fixName = fileOrFixName.substring(fileOrFixName.lastIndexOf(".")+1);
		}
		
		fixName = fixName.toLowerCase();
		//在黑名单中
//		if(BLACK_LIST_SUFFIX.contains(fixName))
//		{
//			return false;
//		}
		
		// 不在白名单中
		if(!WHITE_LIST_SUFFIX.contains(fixName))
		{
			return false;
		}
		
		return true;
	}
}