package com.xjj.framework.exception;


public class DataAccessException extends Exception{
	static final long serialVersionUID = -7034897190745766939L;

    public DataAccessException() {
    	super();
    }

    public DataAccessException(String message) {
    	super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
