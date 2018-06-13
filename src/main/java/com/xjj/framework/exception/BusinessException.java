package com.xjj.framework.exception;

import com.xjj.framework.exception.XjjHandlerExceptionResolver.ReturnType;


/**
 * 业务类异常，在Service，Controller，Helper等类中抛出
 * @author zhanghejie
 */
public class BusinessException extends RuntimeException{
	static final long serialVersionUID = -7034897190745766939L;
	
	private ReturnType returnType = ReturnType.JSON;
	
	/**
	 * 业务类异常类
	 */
    public BusinessException() {
    	super();
    }

    /**
     * 业务类异常类
     * @param returnType 返回类型
     */
    public BusinessException(ReturnType returnType) {
    	super();
    	setReturnType(returnType);
    }
    
    /**
     * 业务类异常类
     * @param message 异常内容
     */
    public BusinessException(String message) {
    	super(message);
    }
    
    /**
     * 业务类异常类
     * @param message 异常内容
     * @param returnType 返回类型
     */
    public BusinessException(String message, ReturnType returnType) {
    	super(message);
    	setReturnType(returnType);
    }

    /**
     * 业务类异常类
     * @param message 异常内容
     * @param cause 异常堆栈 
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 业务类异常类
     * @param message 异常内容
     * @param cause 异常堆栈
     * @param returnType 返回类型
     */
    public BusinessException(String message, Throwable cause, ReturnType returnType) {
        super(message, cause);
        setReturnType(returnType);
    }
    
    /**
     * 业务类异常类
     * @param cause 异常堆栈
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
    
    /**
     * 业务类异常类
     * @param cause 异常堆栈
     * @param returnType 返回类型
     */
    public BusinessException(Throwable cause, ReturnType returnType) {
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
