/****************************************************
 * Description: 权限管理的标注类
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      xjj
 * @version     1.0
**************************************************/
package com.xjj.framework.security.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 权限管理的annotation
 * @author xjj
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRole {

	/**
	 * 权限对应的角色
	 * @author xjj
	 */
	public static enum Role { MANAGER, SITE_MANAGER, TUTOR };
	
	/**
	 * 角色权限
	 * @return  角色权限
	 */
	public Role[] value();
}
