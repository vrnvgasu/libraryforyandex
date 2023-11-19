package ru.demo.libraryforyandex.controller.genre.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.demo.libraryforyandex.data.RequestDto;

@Getter
@Setter
public class GenreRequestDto implements RequestDto {

	private String title;

}
