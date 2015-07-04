package com.croydon.quick.exception;

/**
 * Base Exception class that other Exceptions should extend.
 * 
 * @author croydon
 *
 */
public class BaseException extends Exception {
	
	private static final long serialVersionUID = -2006032950066059871L;

	public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
