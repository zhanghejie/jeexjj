/****************************************************
 * Description: Entity for t_sec_role_privilege
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/

package com.xjj.sec.entity;

import java.util.ArrayList;
import java.util.List;
import com.xjj.framework.entity.EntitySupport;
import com.xjj.framework.utils.StringUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RolePrivilegeEntity extends EntitySupport {

    private static final long serialVersionUID = 1L;
    public RolePrivilegeEntity(){}
    private Long roleId;//ROLE_ID
    private String privilegeTitle;//PRIVILEGE_TITLE
    private String privilegeCode;//PRIVILEGE_CODE
    private String functionList;//FUNCTION_LIST
    /**
     * 返回ROLE_ID
     * @return ROLE_ID
     */
    public Long getRoleId() {
        return roleId;
    }
    
    /**
     * 设置ROLE_ID
     * @param roleId ROLE_ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    /**
     * 返回PRIVILEGE_TITLE
     * @return PRIVILEGE_TITLE
     */
    public String getPrivilegeTitle() {
        return privilegeTitle;
    }
    
    /**
     * 设置PRIVILEGE_TITLE
     * @param privilegeTitle PRIVILEGE_TITLE
     */
    public void setPrivilegeTitle(String privilegeTitle) {
        this.privilegeTitle = privilegeTitle;
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
     * 返回FUNCTION_LIST
     * @return FUNCTION_LIST
     */
    public String getFunctionList() {
        return functionList;
    }
    
    /**
     * 设置FUNCTION_LIST
     * @param functionList FUNCTION_LIST
     */
    public void setFunctionList(String functionList) {
        this.functionList = functionList;
    }
    
    public void addFunction(String function) {
    	
    	if(StringUtils.isBlank(functionList))
    	{
    		 this.functionList = function;
    	}else
    	{
    		 this.functionList = this.functionList+"|"+function;
    	}
    }
    
    public void removeFunction(String function) {
    	
    	if(StringUtils.isBlank(functionList))
    	{
    		return;
    	}
    	if(functionList.equals(function))
    	{
    		functionList = null;
    		return;
    	}
    	if(functionList.endsWith("|"+function))
    	{
    		functionList = functionList.replace("|"+function,"");
    		return;
    	}
    	
    	if(functionList.startsWith(function+"|"))
    	{
    		functionList = functionList.replace(function+"|","");
    		return;
    	}
    	
    	if(functionList.contains("|"+function+"|"))
    	{
    		functionList = functionList.replace("|"+function+"|","");
    	}
    }
    
    /**
     * @return 功能编码列表集合
     */
    public List<String> getFunctions() {
    	if(!StringUtils.isBlank(this.functionList)){
    		return StringUtils.splitToList(this.functionList, "\\|");
    	}
        return new ArrayList<String>();
    }
    

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("com.xjj.sec.entity.RolePrivilegeEntity").append("ID="+this.getId()).toString();
    }
}

