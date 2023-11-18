package ru.demo.libraryforyandex.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.demo.libraryforyandex.mapper.BookMapper;
import ru.demo.libraryforyandex.model.Book;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookMapper bookMapper;

	public Optional<Book> findById(@PathVariable Long id) {
		return bookMapper.getBookBy(id);
	}

}
