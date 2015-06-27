package com.croydon.quick.exception;

public class CategoryDoesntExistException extends BaseException {

	private static final long serialVersionUID = -8913279115952225073L;

	public CategoryDoesntExistException(String message) {
        super(message);
    }

    public CategoryDoesntExistException(Throwable cause) {
        super(cause);
    }

    public CategoryDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
