package ru.demo.libraryforyandex.service.bookservice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.demo.libraryforyandex.controller.dto.response.BookResponseDto;
import ru.demo.libraryforyandex.service.bookservice.handler.GetAllFilterHandler;
import ru.demo.libraryforyandex.service.bookservice.handler.GetBookByIdHandler;

@Service
@RequiredArgsConstructor
public class BookService {

	private final GetAllFilterHandler getAllFilterHandler;
	private final GetBookByIdHandler getBookByIdHandler;

	public BookResponseDto findById(Long id) {
		return getBookByIdHandler.handle(id);
	}

	public List<BookResponseDto> getAll(String authorNameParam, String genreParam) {
		return getAllFilterHandler.handle(authorNameParam, genreParam);
	}

}
