package ru.demo.libraryforyandex.controller.genre.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.demo.libraryforyandex.data.ResponseDto;

@Getter
@Setter
public class GenreResponseDto implements ResponseDto {

	private Long id;

	private String title;

}
