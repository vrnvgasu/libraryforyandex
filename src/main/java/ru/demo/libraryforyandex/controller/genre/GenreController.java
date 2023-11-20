package ru.demo.libraryforyandex.controller.genre;

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
import ru.demo.libraryforyandex.controller.genre.dto.request.GenreRequestDto;
import ru.demo.libraryforyandex.controller.genre.dto.response.GenreResponseDto;
import ru.demo.libraryforyandex.service.genreservice.GenreService;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {

	private final GenreService service;

	@GetMapping
	public List<GenreResponseDto> getAll() {
		return service.getAll();
	}

	@PostMapping
	public GenreResponseDto create(@Valid @RequestBody GenreRequestDto dto) {
		return service.create(dto);
	}

	@PutMapping("/{id}")
	public GenreResponseDto update(@PathVariable Long id, @Valid @RequestBody GenreRequestDto dto) {
		return service.update(id, dto);
	}

	@GetMapping("/{id}")
	public GenreResponseDto findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable Long id) {
		service.delete(id);
		return true;
	}

}
