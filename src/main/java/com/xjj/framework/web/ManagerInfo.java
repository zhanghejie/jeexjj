package com.xjj.framework.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员信息
 * @author zhanghejie
 */
public class ManagerInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String loginName;
	private String userName;
	private Long[] roleIds;
	private String userType;
    private Map<String,Object> parameter = new HashMap<String,Object>();

    
    public ManagerInfo() { }
    
	/**
	 * @param userId
	 * @param loginName
	 * @param userName
	 */
	public ManagerInfo(Long userId, String loginName, String userName) {
		super();
		this.userId = userId;
		this.loginName = loginName;
		this.userName = userName;
	}
	
	
    /* ***********************************************************************
	 * 用户信息
	 *********************************************************************** */
	/**
	 * @return 用户主键
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * @param 用户主键
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
	 * @param 用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * 返回用户的姓名，如果为空，返回登录名，否则返回匿名
	 * @return 用户姓名
	 */
	public String getUserName(){
		return userName;
	}

	/**
	 * @param 登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 返回用户的登录名，如果未登录则显示：未登录
	 * @return 用户登录名
	 */
	public String getLoginName(){
		return loginName;
	}
	
	/**
	 * @param 用户类型
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 返回用户的类型，如果未登录则显示：未知
	 * @return 用户类型
	 */
	public String getUserType(){
		return userType;
	}
	
	/* ***********************************************************************
	 * 参数
	 *********************************************************************** */
	/**
	 * 添加其他参数
	 * @param key
	 * @param obj
	 */
	public void addParameter(String key,Object obj){
		parameter.put(key, obj);
	}
	/**
	 * 获得参数
	 * @param key
	 * @return
	 */
	public Object getParameter(String key){
		return parameter.get(key);
	}
	/**
	 * 移除参数
	 * @param key
	 * @return
	 */
	public Object removeParameter(String key){
		return parameter.remove(key);
	}
	/**
	 * 清空所有参数
	 */
	public void clearParameter(){
		parameter.clear();
	}
}