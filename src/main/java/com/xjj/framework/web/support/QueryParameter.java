package com.xjj.framework.web.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询参数标注
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryParameter {

	public static final String DEFAULT_QUERY_NAME = "query";
	/**
	 * The name of the request parameter to bind to.
	 */
	String value() default DEFAULT_QUERY_NAME;

}