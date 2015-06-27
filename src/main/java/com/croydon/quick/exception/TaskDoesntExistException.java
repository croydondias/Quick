package com.croydon.quick.exception;

public class TaskDoesntExistException extends BaseException {

	private static final long serialVersionUID = -7906049896900238145L;

	public TaskDoesntExistException(String message) {
        super(message);
    }

    public TaskDoesntExistException(Throwable cause) {
        super(cause);
    }

    public TaskDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
