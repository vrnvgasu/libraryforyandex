package ru.demo.libraryforyandex.exception;

public class RelationException extends RuntimeException {

	public RelationException(String message) {
		super(message);
	}

	public RelationException(String message, Throwable cause) {
		super(message, cause);
	}

	public RelationException(Throwable cause) {
		super(cause);
	}

}
