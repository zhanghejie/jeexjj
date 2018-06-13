package com.xjj.framework.exception;

import com.xjj.framework.exception.XjjHandlerExceptionResolver.ReturnType;

/**
 * 校验异常，数据校验出错时抛出
 * @author zhanghejie
 */
public class ValidationException extends RuntimeException{
	static final long serialVersionUID = -7034897190745766939L;
	
	private ReturnType returnType = ReturnType.JSON;
	
	/**
	 * 校验异常类
	 */
    public ValidationException() {
    	super();
    }

    /**
     * 校验异常类
     * @param returnType 返回类型
     */
    public ValidationException(ReturnType returnType) {
    	super();
    	setReturnType(returnType);
    }
    
    /**
     * 校验异常类
     * @param message 异常内容
     */
    public ValidationException(String message) {
    	super(message);
    }
    
    /**
     * 校验异常类
     * @param message 异常内容
     * @param returnType 返回类型
     */
    public ValidationException(String message, ReturnType returnType) {
    	super(message);
    	setReturnType(returnType);
    }

    /**
     * 校验异常类
     * @param message 异常内容
     * @param cause 异常堆栈 
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 校验异常类
     * @param message 异常内容
     * @param cause 异常堆栈
     * @param returnType 返回类型
     */
    public ValidationException(String message, Throwable cause, ReturnType returnType) {
        super(message, cause);
        setReturnType(returnType);
    }
    
    /**
     * 校验异常类
     * @param cause 异常堆栈
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
    
    /**
     * 校验异常类
     * @param cause 异常堆栈
     * @param returnType 返回类型
     */
    public ValidationException(Throwable cause, ReturnType returnType) {
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
