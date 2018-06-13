/****************************************************
 * Description: Entity for t_sys_xfile
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-05-04 zhanghejie Create File
**************************************************/

package com.xjj.sys.xfile.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.xjj.framework.entity.EntitySupport;

public class XfileEntity extends EntitySupport {

    private static final long serialVersionUID = 1L;
    public XfileEntity(){}
    private String fileRealname;//file_realname
    private String filePath;//file_path
    private String fileTitle;//file_title
    private Date createDate;//create_date
    
    private String isDeleted;
    private String url;
    private String extensionName;//扩展名
	private Long fileSize;
	private Long userId;
	
	private String createUserName;
    
    /**
     * 返回file_realname
     * @return file_realname
     */
    public String getFileRealname() {
        return fileRealname;
    }
    
    /**
     * 设置file_realname
     * @param fileRealname file_realname
     */
    public void setFileRealname(String fileRealname) {
        this.fileRealname = fileRealname;
    }
    
    /**
     * 返回file_path
     * @return file_path
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * 设置file_path
     * @param filePath file_path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * 返回file_title
     * @return file_title
     */
    public String getFileTitle() {
        return fileTitle;
    }
    
    /**
     * 设置file_title
     * @param fileTitle file_title
     */
    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
    
    /**
     * 返回create_date
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * 设置create_date
     * @param createDate create_date
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("com.xjj.sys.xfile.entity.XfileEntity").append("ID="+this.getId()).toString();
    }
}

