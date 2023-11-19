package ru.demo.libraryforyandex.data.dto;

import java.time.LocalDate;

public record BookGenreAuthorData(
		Long bookId,
		String bookTitle,
		Integer bookPublishedYear,
		Long genreId,
		String genreTitle,
		Long authorId,
		String authorFullName,
		LocalDate authorBirthDate) {

}
