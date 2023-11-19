package ru.demo.libraryforyandex.data.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

	private Long id;

	private String fullName;

	private LocalDate birthDate;

}
