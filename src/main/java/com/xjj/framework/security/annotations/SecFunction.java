package com.xjj.framework.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xjj.framework.security.SecurityConstants;

/**
 * 功能标注
 * @author xjj
 */
@Inherited
@Target( ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)
public @interface SecFunction {
	/**
	 * 权限功能的名称，没有名称则不会显示出来
	 * @return
	 */
	public String title() default "";
	
	/**
	 * 权限功能的编码
	 * @return
	 */
	public String code();

	/**
	 * 功能对应的权限，如果为ALL，则全部都可以访问；
	 * @return
	 */
	public String privilege() default "";
	
	/**
	 * 功能对应的权限列表；
	 * @return
	 */
	public String[] privileges() default "";
	
	/**
	 * 依赖的功能<br>
	 * 关联其他权限的功能，权限名和功能名用下划线分割“_”
	 * @return
	 */
	public String[] depend() default {SecurityConstants.SECURITY_DEFAULT_CODE};
}
