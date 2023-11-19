package ru.demo.libraryforyandex.service.bookservice.handler;

import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_BY_ID;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.demo.libraryforyandex.controller.dto.response.AuthorResponseDto;
import ru.demo.libraryforyandex.controller.dto.response.BookResponseDto;
import ru.demo.libraryforyandex.controller.dto.response.GenreResponseDto;
import ru.demo.libraryforyandex.data.dto.BookData;
import ru.demo.libraryforyandex.data.mapper.BookMapper;
import ru.demo.libraryforyandex.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class GetBookByIdHandler {

	private final BookMapper bookMapper;

	public BookResponseDto handle(Long id) {
		BookData bookData = bookMapper.getBookBy(id)
				.orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
		return BookResponseDto.builder()
				.id(bookData.getId())
				.title(bookData.getTitle())
				.publishedYear(bookData.getPublishedYear())
				.genres(bookData.getGenres().stream()
						.map(el -> GenreResponseDto.builder()
								.id(el.id())
								.title(el.title())
								.build()).collect(Collectors.toSet()))
				.authors(bookData.getAuthors().stream()
						.map(el -> AuthorResponseDto.builder()
								.id(el.id())
								.fullName(el.fullName())
								.birthDate(el.birthDate())
								.build()).collect(Collectors.toSet()))
				.build();
	}

}
