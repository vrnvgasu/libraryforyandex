package ru.demo.libraryforyandex.service.authorservice;

import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_BY_ID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.libraryforyandex.controller.author.dto.request.AuthorRequestDto;
import ru.demo.libraryforyandex.controller.author.dto.response.AuthorResponseDto;
import ru.demo.libraryforyandex.data.dto.AuthorDto;
import ru.demo.libraryforyandex.data.mapper.AuthorMapper;
import ru.demo.libraryforyandex.exception.NotFoundException;
import ru.demo.libraryforyandex.exception.RelationException;
import ru.demo.libraryforyandex.service.BaseService;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService extends BaseService<AuthorResponseDto, AuthorRequestDto> {

	private final AuthorMapper mapper;
	private final ModelMapper modelMapper;


	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponseDto> getAll() {
		return mapper.findAll().stream()
				.map(el -> modelMapper.map(el, AuthorResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public AuthorResponseDto create(AuthorRequestDto dto) {
		AuthorDto data = modelMapper.map(dto, AuthorDto.class);
		mapper.save(data);
		return findById(data.getId());
	}

	@Override
	public AuthorResponseDto update(Long id, AuthorRequestDto dto) {
		mapper.update(id, modelMapper.map(dto, AuthorDto.class));
		return findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public AuthorResponseDto findById(Long id) {
		return modelMapper.map(mapper.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id))),
				AuthorResponseDto.class);
	}

	@Override
	public void delete(Long id) {
		if (mapper.hasBookRelation(id)) {
			throw new RelationException("Author has relations, id:" + id);
		}

		mapper.delete(id);
	}

	public void checkAreExistByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}

		HashSet<Long> copy = new HashSet<>(ids);
		copy.removeAll(mapper.getAuthorsIdsByIdSet(ids));

		if (copy.isEmpty()) {
			return;
		}

		throw new NotFoundException(String.format(
				"Can't found authors with id in %s",
				copy.stream().map(String::valueOf).collect(Collectors.toSet())
		));
	}

}
