package ru.demo.libraryforyandex.service.authorservice;

import static ru.demo.libraryforyandex.exception.NotFoundException.NOT_FOUND_BY_ID;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.demo.libraryforyandex.controller.author.dto.request.AuthorRequestDto;
import ru.demo.libraryforyandex.controller.author.dto.response.AuthorResponseDto;
import ru.demo.libraryforyandex.data.dto.AuthorDto;
import ru.demo.libraryforyandex.data.mapper.AuthorMapper;
import ru.demo.libraryforyandex.exception.NotFoundException;
import ru.demo.libraryforyandex.service.BaseService;

@Service
@RequiredArgsConstructor
public class AuthorService extends BaseService<AuthorResponseDto, AuthorRequestDto> {

	private final AuthorMapper mapper;
	private final ModelMapper modelMapper;


	@Override
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
	public AuthorResponseDto findById(Long id) {
		return modelMapper.map(mapper.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id))),
				AuthorResponseDto.class);
	}

	@Override
	public void delete(Long id) {
		mapper.delete(id);
	}

}
