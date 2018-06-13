package com.xjj.framework.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xjj.framework.security.SecurityConstants;

/**
 * 更新功能
 * @author xjj
 */
@Inherited
@Target( ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)
public @interface SecEdit {
	/**
	 * 名称
	 * @return
	 */
	public String title() default SecurityConstants.SECURITY_EDIT_TITLE;
	
	/**
	 * 编码
	 * @return
	 */
	public String code() default SecurityConstants.SECURITY_EDIT_CODE;
	
	/**
	 * 对应的权限；
	 * @return
	 */
	public String privilege() default "";
	
	/**
	 * 依赖的功能<br>
	 * 关联其他权限的功能，权限名和功能名用下划线分割“_”
	 * @return
	 */
	public String[] depend() default {SecurityConstants.SECURITY_DEFAULT_CODE,SecurityConstants.SECURITY_LIST_CODE};
}
