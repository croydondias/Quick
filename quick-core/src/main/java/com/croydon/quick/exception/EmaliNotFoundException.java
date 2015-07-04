package com.croydon.quick.exception;

public class EmaliNotFoundException extends BaseException {

	private static final long serialVersionUID = 2197201842564853759L;
	
	public EmaliNotFoundException(String message) {
        super(message);
    }

    public EmaliNotFoundException(Throwable cause) {
        super(cause);
    }

    public EmaliNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
