package ru.demo.libraryforyandex.service.bookservice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.demo.libraryforyandex.controller.book.dto.response.BookResponseDto;
import ru.demo.libraryforyandex.controller.book.request.BookRequestDto;
import ru.demo.libraryforyandex.data.dto.BookData;
import ru.demo.libraryforyandex.data.mapper.BookMapper;
import ru.demo.libraryforyandex.service.BaseService;
import ru.demo.libraryforyandex.service.bookservice.handler.GetAllFilterHandler;
import ru.demo.libraryforyandex.service.bookservice.handler.GetBookByIdHandler;

@Service
@RequiredArgsConstructor
public class BookService extends BaseService<BookResponseDto, BookRequestDto> {

	private final GetAllFilterHandler getAllFilterHandler;

	private final GetBookByIdHandler getBookByIdHandler;

	private final ModelMapper modelMapper;

	private final BookMapper mapper;

	@Override
	public BookResponseDto findById(Long id) {
		return getBookByIdHandler.handle(id);
	}

	public List<BookResponseDto> getAll(String authorNameParam, String genreParam) {
		return getAllFilterHandler.handle(authorNameParam, genreParam);
	}

	@Override
	public List<BookResponseDto> getAll() {
		return getAll(null, null);
	}

	@Override
	public BookResponseDto create(BookRequestDto dto) {
		BookData data = modelMapper.map(dto, BookData.class);
		mapper.save(data);
		return findById(data.getId());
	}

	@Override
	public BookResponseDto update(Long id, BookRequestDto dto) {
		mapper.update(id, modelMapper.map(dto, BookData.class));
		return findById(id);
	}

	@Override
	public void delete(Long id) {
		mapper.delete(id);
	}

}
