package com.croydon.quick.exception;

public class EmployeeAlreadyExistsException extends BaseException {

	private static final long serialVersionUID = 2197201842564853759L;
	
	public EmployeeAlreadyExistsException(String message) {
        super(message);
    }

    public EmployeeAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public EmployeeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
