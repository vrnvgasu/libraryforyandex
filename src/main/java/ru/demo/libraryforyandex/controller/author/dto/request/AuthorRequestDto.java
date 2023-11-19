package ru.demo.libraryforyandex.controller.author.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import ru.demo.libraryforyandex.data.RequestDto;

@Getter
@Setter
public class AuthorRequestDto implements RequestDto {

	private String fullName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

}
