package ru.demo.libraryforyandex.service.genreservice;

import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_BY_ID;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.demo.libraryforyandex.controller.genre.dto.request.GenreRequestDto;
import ru.demo.libraryforyandex.controller.genre.dto.response.GenreResponseDto;
import ru.demo.libraryforyandex.data.dto.GenreDto;
import ru.demo.libraryforyandex.data.mapper.GenreMapper;
import ru.demo.libraryforyandex.exception.NotFoundException;
import ru.demo.libraryforyandex.service.BaseService;

@Service
@RequiredArgsConstructor
public class GenreService extends BaseService<GenreResponseDto, GenreRequestDto> {

	private final GenreMapper mapper;
	private final ModelMapper modelMapper;


	@Override
	public List<GenreResponseDto> getAll() {
		return mapper.findAll().stream()
				.map(el -> modelMapper.map(el, GenreResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public GenreResponseDto create(GenreRequestDto dto) {
		GenreDto data = modelMapper.map(dto, GenreDto.class);
		mapper.save(data);
		return findById(data.getId());
	}

	@Override
	public GenreResponseDto update(Long id, GenreRequestDto dto) {
		mapper.update(id, modelMapper.map(dto, GenreDto.class));
		return findById(id);
	}

	@Override
	public GenreResponseDto findById(Long id) {
		return modelMapper.map(mapper.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id))),
				GenreResponseDto.class);
	}

	@Override
	public void delete(Long id) {
		mapper.delete(id);
	}

}
