package ru.demo.libraryforyandex.controller;

import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_BY_ID;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.libraryforyandex.exception.NotFoundException;
import ru.demo.libraryforyandex.model.Book;
import ru.demo.libraryforyandex.service.BookService;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping("/{id}")
	public Book findById(@PathVariable Long id) {
		return bookService.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
	}

}
