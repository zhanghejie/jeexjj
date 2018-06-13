/****************************************************
 * Description: Entity for 菜单
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-12 zhanghejie Create File
**************************************************/

package com.xjj.sec.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.xjj.framework.entity.EntitySupport;

public class MenuEntity extends EntitySupport {

    private static final long serialVersionUID = 1L;
    public MenuEntity(){}
    private String title;//TITLE
    private String description;//DESCRIPTION
    private Long parentId;//PARENT_ID
    private String privilegeCode;//PRIVILEGE_CODE
    private String url;//URL
    private Integer orderSn;//order_sn
    private String icon;//ICON
    private String status;//status
    private String code;//code
    private List<MenuEntity> subMenus;//子菜单
    /**
     * 返回TITLE
     * @return TITLE
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 设置TITLE
     * @param title TITLE
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 返回DESCRIPTION
     * @return DESCRIPTION
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 设置DESCRIPTION
     * @param description DESCRIPTION
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 返回PARENT_ID
     * @return PARENT_ID
     */
    public Long getParentId() {
        return parentId;
    }
    
    /**
     * 设置PARENT_ID
     * @param parentId PARENT_ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    /**
     * 返回PRIVILEGE_CODE
     * @return PRIVILEGE_CODE
     */
    public String getPrivilegeCode() {
        return privilegeCode;
    }
    
    /**
     * 设置PRIVILEGE_CODE
     * @param privilegeCode PRIVILEGE_CODE
     */
    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode;
    }
    
    /**
     * 返回URL
     * @return URL
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * 设置URL
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * 返回order_sn
     * @return order_sn
     */
    public Integer getOrderSn() {
        return orderSn;
    }
    
    /**
     * 设置order_sn
     * @param orderSn order_sn
     */
    public void setOrderSn(Integer orderSn) {
        this.orderSn = orderSn;
    }
    
    /**
     * 返回ICON
     * @return ICON
     */
    public String getIcon() {
        return icon;
    }
    
    /**
     * 设置ICON
     * @param icon ICON
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    /**
     * 返回status
     * @return status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 设置status
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<MenuEntity> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<MenuEntity> subMenus) {
		this.subMenus = subMenus;
	}
	
	
	public MenuEntity copy() {
		MenuEntity newMenu = new MenuEntity();
		BeanUtils.copyProperties(this, newMenu);
		return newMenu;
	}
	
	public void addSubMenu(MenuEntity subMenu)
	{
		if(null==this.subMenus)
		{
			this.subMenus = new ArrayList<MenuEntity>();
			subMenus.add(subMenu);
		}else
		{
			subMenus.add(subMenu);
		}
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("com.xjj.sys.entity.MenuEntity").append("ID="+this.getId()).toString();
    }
}

