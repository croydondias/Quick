package com.croydon.quick.exception;

public class EmployeeDoesntExistException extends BaseException {

	private static final long serialVersionUID = 2197201842564853759L;
	
	public EmployeeDoesntExistException(String message) {
        super(message);
    }

    public EmployeeDoesntExistException(Throwable cause) {
        super(cause);
    }

    public EmployeeDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
