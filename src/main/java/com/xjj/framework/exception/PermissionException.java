package com.xjj.framework.exception;

import com.xjj.framework.exception.XjjHandlerExceptionResolver.ReturnType;


/**
 * 权限异常，没有相关权限时抛出
 * @author zhanghejie
 */
public class PermissionException extends RuntimeException{
	static final long serialVersionUID = -7034897190745766939L;
	
	private ReturnType returnType = ReturnType.JSON;
	
	/**
	 * 权限异常类
	 */
    public PermissionException() {
    	super();
    }

    /**
     * 权限异常类
     * @param returnType 返回类型
     */
    public PermissionException(ReturnType returnType) {
    	super();
    	setReturnType(returnType);
    }
    
    /**
     * 权限异常类
     * @param message 异常内容
     */
    public PermissionException(String message) {
    	super(message);
    }
    
    /**
     * 权限异常类
     * @param message 异常内容
     * @param returnType 返回类型
     */
    public PermissionException(String message, ReturnType returnType) {
    	super(message);
    	setReturnType(returnType);
    }

    /**
     * 权限异常类
     * @param message 异常内容
     * @param cause 异常堆栈 
     */
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 权限异常类
     * @param message 异常内容
     * @param cause 异常堆栈
     * @param returnType 返回类型
     */
    public PermissionException(String message, Throwable cause, ReturnType returnType) {
        super(message, cause);
        setReturnType(returnType);
    }
    
    /**
     * 权限异常类
     * @param cause 异常堆栈
     */
    public PermissionException(Throwable cause) {
        super(cause);
    }
    
    /**
     * 权限异常类
     * @param cause 异常堆栈
     * @param returnType 返回类型
     */
    public PermissionException(Throwable cause, ReturnType returnType) {
        super(cause);
        setReturnType(returnType);
    }

    /**
     * 返回类型
     * @return 返回类型
     */
	public ReturnType getReturnType() {
		return returnType;
	}

	/**
	 * 设置返回类型
	 * @param returnType 返回类型
	 */
	public void setReturnType(ReturnType returnType) {
		this.returnType = returnType;
	}
    
}
