package ru.demo.libraryforyandex.service.genreservice;

import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_BY_ID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.libraryforyandex.controller.genre.dto.request.GenreRequestDto;
import ru.demo.libraryforyandex.controller.genre.dto.response.GenreResponseDto;
import ru.demo.libraryforyandex.data.dto.GenreDto;
import ru.demo.libraryforyandex.data.mapper.GenreMapper;
import ru.demo.libraryforyandex.exception.NotFoundException;
import ru.demo.libraryforyandex.exception.RelationException;
import ru.demo.libraryforyandex.service.BaseService;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreService extends BaseService<GenreResponseDto, GenreRequestDto> {

	private final GenreMapper mapper;
	private final ModelMapper modelMapper;

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public GenreResponseDto findById(Long id) {
		return modelMapper.map(mapper.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id))),
				GenreResponseDto.class);
	}

	@Override
	public void delete(Long id) {
		if (mapper.hasBookRelation(id)) {
			throw new RelationException("Genre has relations, id:" + id);
		}

		mapper.delete(id);
	}

	public void checkAreExistByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}

		HashSet<Long> copy = new HashSet<>(ids);
		copy.removeAll(mapper.getGenresIdsByIdSet(ids));

		if (copy.isEmpty()) {
			return;
		}

		throw new NotFoundException(String.format(
				"Can't found genres with id in %s",
				copy.stream().map(String::valueOf).collect(Collectors.toSet())
		));
	}

}
