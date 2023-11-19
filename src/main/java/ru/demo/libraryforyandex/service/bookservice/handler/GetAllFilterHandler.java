package ru.demo.libraryforyandex.service.bookservice.handler;

import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_AUTHOR_LIKE_FULL_NAME;
import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_GENRE_BY_TITLE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.demo.libraryforyandex.controller.dto.response.AuthorResponseDto;
import ru.demo.libraryforyandex.controller.dto.response.BookResponseDto;
import ru.demo.libraryforyandex.controller.dto.response.GenreResponseDto;
import ru.demo.libraryforyandex.data.dto.BookGenreAuthorData;
import ru.demo.libraryforyandex.data.mapper.AuthorMapper;
import ru.demo.libraryforyandex.data.mapper.BookMapper;
import ru.demo.libraryforyandex.data.mapper.GenreMapper;
import ru.demo.libraryforyandex.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class GetAllFilterHandler {

	private final BookMapper bookMapper;

	private final GenreMapper genreMapper;

	private final AuthorMapper authorMapper;

	public List<BookResponseDto> handle(String authorNameParam, String genreParam) {
		validateGenre(genreParam);
		validateAuthor(authorNameParam);
		List<BookGenreAuthorData> allBookGenreAuthor = bookMapper.getAllBookGenreAuthor(authorNameParam, genreParam);
		Map<Long, BookResponseDto> bookMap = new HashMap<>();

		for (BookGenreAuthorData bookGenreAuthor : allBookGenreAuthor) {
			addBookToBookMap(bookGenreAuthor, bookMap);
		}

		return new ArrayList<>(bookMap.values());
	}

	private void validateGenre(String genreTitle) {
		if (genreTitle == null) {
			return;
		}

		if (!genreMapper.isExist(genreTitle)) {
			throw new NotFoundException(String.format(NOT_FOUND_GENRE_BY_TITLE, genreTitle));
		}
	}

	private void validateAuthor(String authorFullName) {
		if (authorFullName == null) {
			return;
		}

		if (!authorMapper.isExist(authorFullName)) {
			throw new NotFoundException(String.format(NOT_FOUND_AUTHOR_LIKE_FULL_NAME, authorFullName));
		}
	}

	private void addBookToBookMap(BookGenreAuthorData bookGenreAuthor, Map<Long, BookResponseDto> bookMap) {
		BookResponseDto book = bookMap.get(bookGenreAuthor.bookId());
		if (book == null) {
			book = BookResponseDto.builder()
					.id(bookGenreAuthor.bookId())
					.title(bookGenreAuthor.bookTitle())
					.publishedYear(bookGenreAuthor.bookPublishedYear())
					.build();
			bookMap.put(book.getId(), book);
		}

		book.getGenres().add(GenreResponseDto.builder()
				.id(bookGenreAuthor.genreId())
				.title(bookGenreAuthor.genreTitle())
				.build());
		book.getAuthors().add(AuthorResponseDto.builder()
				.id(bookGenreAuthor.authorId())
				.fullName(bookGenreAuthor.authorFullName())
				.birthDate(bookGenreAuthor.authorBirthDate())
				.build());
	}

}
