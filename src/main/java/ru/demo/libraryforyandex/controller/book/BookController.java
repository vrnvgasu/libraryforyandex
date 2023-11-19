package ru.demo.libraryforyandex.controller.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.libraryforyandex.controller.book.dto.response.BookResponseDto;
import ru.demo.libraryforyandex.controller.book.request.BookRequestDto;
import ru.demo.libraryforyandex.service.bookservice.BookService;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping()
	public List<BookResponseDto> getAll(@RequestParam(name = "author", required = false) String authorName,
			@RequestParam(name = "genre", required = false) String genre) {
		return bookService.getAll(authorName, genre);
	}

	@GetMapping("/{id}")
	public BookResponseDto findById(@PathVariable Long id) {
		return bookService.findById(id);
	}

	@PostMapping
	public BookResponseDto create(@RequestBody BookRequestDto dto) {
		return bookService.create(dto);
	}

	@PutMapping("/{id}")
	public BookResponseDto update(@PathVariable Long id, @RequestBody BookRequestDto dto) {
		return bookService.update(id, dto);
	}
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable Long id) {
		bookService.delete(id);
		return true;
	}
}
