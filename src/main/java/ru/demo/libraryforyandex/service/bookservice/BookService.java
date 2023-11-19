package ru.demo.libraryforyandex.service.bookservice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.libraryforyandex.controller.book.dto.response.BookResponseDto;
import ru.demo.libraryforyandex.controller.book.request.BookRequestDto;
import ru.demo.libraryforyandex.data.dto.BookData;
import ru.demo.libraryforyandex.data.mapper.BookMapper;
import ru.demo.libraryforyandex.service.BaseService;
import ru.demo.libraryforyandex.service.authorservice.AuthorService;
import ru.demo.libraryforyandex.service.bookservice.handler.GetAllFilterHandler;
import ru.demo.libraryforyandex.service.bookservice.handler.GetBookByIdHandler;
import ru.demo.libraryforyandex.service.genreservice.GenreService;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService extends BaseService<BookResponseDto, BookRequestDto> {

	private final GetAllFilterHandler getAllFilterHandler;

	private final GetBookByIdHandler getBookByIdHandler;

	private final ModelMapper modelMapper;

	private final BookMapper mapper;

	private final AuthorService authorService;

	private final GenreService genreService;

	@Override
	@Transactional(readOnly = true)
	public BookResponseDto findById(Long id) {
		return getBookByIdHandler.handle(id);
	}

	@Transactional(readOnly = true)
	public List<BookResponseDto> getAll(String authorNameParam, String genreParam) {
		return getAllFilterHandler.handle(authorNameParam, genreParam);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookResponseDto> getAll() {
		return getAll(null, null);
	}

	@Override
	public BookResponseDto create(BookRequestDto dto) {
		authorService.checkAreExistByIds(dto.getAuthorIds());
		genreService.checkAreExistByIds(dto.getGenreIds());
		BookData data = modelMapper.map(dto, BookData.class);
		mapper.save(data);

		if (!dto.getGenreIds().isEmpty()) {
			mapper.attachGenres(dto.getGenreIds(), data.getId());
		}

		if (!dto.getAuthorIds().isEmpty()) {
			mapper.attachAuthors(dto.getAuthorIds(), data.getId());
		}

		return findById(data.getId());
	}

	@Override
	public BookResponseDto update(Long id, BookRequestDto dto) {
		authorService.checkAreExistByIds(dto.getAuthorIds());
		genreService.checkAreExistByIds(dto.getGenreIds());
		mapper.update(id, modelMapper.map(dto, BookData.class));
		mapper.detach(id);

		if (!dto.getGenreIds().isEmpty()) {
			mapper.attachGenres(dto.getGenreIds(), id);
		}

		if (!dto.getAuthorIds().isEmpty()) {
			mapper.attachAuthors(dto.getAuthorIds(), id);
		}

		return findById(id);
	}

	@Override
	public void delete(Long id) {
		mapper.delete(id);
	}

}
