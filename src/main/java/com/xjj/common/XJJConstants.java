package com.xjj.common;

import com.xjj.framework.utils.EncryptUtils;


public class XJJConstants{
	
	
	/**
	 * 消息类型 ：成功
	 */
	public static final String MSG_TYPE_SUCCESS = "success";
	
	/**
	 * 消息类型：信息
	 */
	public static final String MSG_TYPE_INFO = "info";
	/**
	 * 消息类型：警告
	 */
	public static final String MSG_TYPE_WARNING = "warning";
	/**
	 * 消息类型：错误
	 */
	public static final String MSG_TYPE_ERROR = "error";
	
	
	/**
	 * 登录的url地址
	 */
	public static final String CONFIG_MANAGER_LOGIN_URL = "manager_login_url";
	
	//
	public static final String SESSION_MANAGER_INFO_KEY = "session_manager_info_key";
	public static final String SESSION_USER_INFO_KEY = "session_user_info_key";
	public static final String USER_INIT_PASSWORD = EncryptUtils.MD5Encode("123456");
	
	
	//=================================公共字典开始=====================================================
	/**
	 * 是否有效
	 */
	public static final String COMMON_STATUS_VALID = "valid";
	public static final String COMMON_STATUS_INVALID = "invalid";
	public static final String[] COMMON_STATUS_LIST = {COMMON_STATUS_VALID,COMMON_STATUS_INVALID};
	
	
	public static final String COMMON_YESNO_YES = "yes";
	public static final String COMMON_YESNO_NO = "no";
	public static final String[] COMMON_YESNO = {COMMON_YESNO_YES,COMMON_YESNO_NO};
	
	
	/**
	 * 用户类型（管理员和普通用户）
	 */
	public static final String USER_TYPE_ADMIN = "admin";
	public static final String USER_TYPE_USER = "user";
	public static final String[] USER_TYPE = {USER_TYPE_ADMIN,USER_TYPE_USER};
	//=================================公共字典结束=====================================================
	
}