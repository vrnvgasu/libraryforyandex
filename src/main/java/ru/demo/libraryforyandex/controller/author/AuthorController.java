package ru.demo.libraryforyandex.controller.author;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.libraryforyandex.controller.author.dto.request.AuthorRequestDto;
import ru.demo.libraryforyandex.controller.author.dto.response.AuthorResponseDto;
import ru.demo.libraryforyandex.service.authorservice.AuthorService;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

	private final AuthorService service;

	@GetMapping
	public List<AuthorResponseDto> getAll() {
		return service.getAll();
	}

	@PostMapping
	public AuthorResponseDto create(@Valid @RequestBody AuthorRequestDto dto) {
		return service.create(dto);
	}

	@PutMapping("/{id}")
	public AuthorResponseDto update(@PathVariable Long id, @Valid @RequestBody AuthorRequestDto dto) {
		return service.update(id, dto);
	}

	@GetMapping("/{id}")
	public AuthorResponseDto findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable Long id) {
		service.delete(id);
		return true;
	}

}
