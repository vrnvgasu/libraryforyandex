package ru.demo.libraryforyandex.controller.author.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import ru.demo.libraryforyandex.data.ResponseDto;

@Getter
@Setter
public class AuthorResponseDto implements ResponseDto {

	private Long id;

	private String fullName;

	private LocalDate birthDate;

}
