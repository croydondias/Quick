package com.croydon.quick.exception;

public class ProjectAlreadyExistsException extends BaseException {

	private static final long serialVersionUID = 8079320090306105797L;

	public ProjectAlreadyExistsException(String message) {
        super(message);
    }

    public ProjectAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ProjectAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
