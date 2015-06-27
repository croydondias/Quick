package com.croydon.quick.exception;

public class CategoryAlreadyExistsException extends BaseException {

	private static final long serialVersionUID = -8913279115952225073L;

	public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public CategoryAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CategoryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
