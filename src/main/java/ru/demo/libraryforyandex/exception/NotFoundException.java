package ru.demo.libraryforyandex.exception;

public class NotFoundException extends RuntimeException {

	public static final String NOT_FOUND_BY_ID = "Can't found entity with id %d";

	public static final String NOT_FOUND_GENRE_BY_TITLE = "Can't found genre with title %s";

	public static final String NOT_FOUND_AUTHOR_LIKE_FULL_NAME = "Can't found author with full_name like %s";

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
