package ru.demo.libraryforyandex.exception;

public class NotFoundException extends RuntimeException {

	public static final String NOT_FOUND_BY_ID = "Can't found entity with id %d";

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}
}
