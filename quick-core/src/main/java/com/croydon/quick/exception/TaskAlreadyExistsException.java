package com.croydon.quick.exception;

public class TaskAlreadyExistsException extends BaseException {

	private static final long serialVersionUID = -7906049896900238145L;

	public TaskAlreadyExistsException(String message) {
        super(message);
    }

    public TaskAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public TaskAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
