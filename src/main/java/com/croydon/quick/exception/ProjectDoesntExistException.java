package com.croydon.quick.exception;

public class ProjectDoesntExistException extends BaseException {

	private static final long serialVersionUID = 8079320090306105797L;

	public ProjectDoesntExistException(String message) {
        super(message);
    }

    public ProjectDoesntExistException(Throwable cause) {
        super(cause);
    }

    public ProjectDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
